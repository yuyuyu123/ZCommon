package com.cc.android.zcommon.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.cc.android.zcommon.api.ManagerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The retrofit download manager is a custom service that handles long-running HTTP or HTTPS downloads.
 * Clients may request that a URI be downloaded to a particular destination file, clients firstly should
 * get an instance of {@link RetrofitDownloadManager} to configure some params.
 * For example:
 *
 * 1).A file used to save the downloaded-file(Necessary);
 * 2).A notification(if clients want to show some information through a notification);
 * 3).Notification's Style(RemoteViews,
 *              downloading tips, download-task finished tips, download-task failed tips,etc.);
 * 4).If automatically install the apk file(the downloaded file's type must be an apk file.).
 *
 * Apps that request downloads through this API should register a retrofit download adapter
 * ({@link RetrofitDownloadAdapter}), if necessary, for listening
 * downloading progress and results through {@link RetrofitDownloadConfig}.
 *
 * Note that the application must have the {@link android.Manifest.permission#INTERNET}
 * permission to use this class.
 *
 * @Author LiuLiWei
 */
public class RetrofitDownloadManager {
    /**
     * An identifier to identify RetrofitDownloadManager.
     */
    private static final String TAG = RetrofitDownloadManager.class.getSimpleName();

    /**
     * The flag for handler to recognize task's downloading.
     */
    private static final int DOWNLOADING_FLAG = 0x01;

    /**
     * The flag for handler to recognize task's download-success.
     */
    private static final int DOWNLOAD_SUCCESS_FLAG = 0x02;

    /**
     * The flag for handler to recognize task's download-failure.
     */
    private static final int DOWNLOAD_FAILURE_FLAG = 0x03;

    /**
     * The flag for handler to recognize task's download-error.
     */
    private static final int DOWNLOAD_ERROR_FLAG = 0x04;

    /**
     * The identifier to identify {@link Response#code()}
     */
    private static final String BUNDLE_CODE = "code";

    /**
     * The identifier to identify {@link Response#raw().message()}.
     */
    private static final String BUNDLE_MESSAGE = "message";

    /**
     * The identifier to identify the file's total size that clients want to download.
     */
    private static final String BUNDLE_FILE_TOTAL_SIZE = "file_size";

    /**
     * The identifier to identify the file's downloaded size.
     */
    private static final String BUNDLE_FILE_DOWNLOADED_SIZE = "file_download_size";

    /**
     * This handler to handle some download-events in UI thread, which can avoid that kinds of
     * errors that happened when UI is updated in a non-UI thread.
     */
    private static Handler mHandler;

    /**
     * Context wrapped by {@link WeakReference}.
     */
    private WeakReference<Context> mContext;

    /**
     * The retrofit download config manages a downloading-task's all configurations.
     */
    private RetrofitDownloadConfig mConfig;

    /**
     * The listener for listening a downloading-task's downloading progress and download
     * results(downloading,success,failure or error).
     */
    private RetrofitDownloadAdapter mAdapter;

    /**
     * The service that manages all primary time-consuming(download) tasks.
     */
    private RetrofitDownloadService mService;

    /**
     * NotificationManager manages all kinds of notifications.
     */
    private NotificationManager mNotificationManager = null;

    /**
     * RemoteView serves as a role of notification's content view.
     */
    private RemoteViews mRemoteView = null;

    /**
     * Notification
     */
    private Notification mNotification = null;

    /**
     * The class's default constructor.
     *
     * @param retrofitDownloadConfig
     *      the instance of {@link RetrofitDownloadConfig}
     */
    public RetrofitDownloadManager(RetrofitDownloadConfig retrofitDownloadConfig) {
        this.mService = ManagerFactory.getFactory().getManager(RetrofitDownloadService.class, false);
        this.mConfig = retrofitDownloadConfig;
        this.mContext = new WeakReference<>(mConfig.getContext());
        this.mAdapter = mConfig.getRetrofitDownloadAdapter();
        this.mNotificationManager = (NotificationManager) mContext.get()
                .getApplicationContext().getSystemService(mContext.get().getApplicationContext().NOTIFICATION_SERVICE);
        this.mRemoteView = mConfig.getRemoteView();
        this.mNotification = mConfig.getNotification();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                    Bundle bundle = null;
                    switch (msg.what) {
                        case DOWNLOADING_FLAG:
                            bundle = (Bundle) msg.obj;
                            if(bundle != null) {
                                mAdapter.onDownloading(
                                        bundle.getInt(BUNDLE_CODE),
                                        bundle.getString(BUNDLE_MESSAGE),
                                        bundle.getLong(BUNDLE_FILE_TOTAL_SIZE),
                                        bundle.getLong(BUNDLE_FILE_DOWNLOADED_SIZE)
                                );
                            }
                            break;
                        case DOWNLOAD_SUCCESS_FLAG:
                            Log.e(TAG, "DOWNLOAD_SUCCESS_FLAG");
                            bundle = (Bundle) msg.obj;
                            if(bundle != null) {
                                Log.e(TAG, "DOWNLOAD_SUCCESS_FLAG:" + bundle.toString());

                                mAdapter.onDownloadSuccess(
                                        bundle.getInt(BUNDLE_CODE),
                                        bundle.getString(BUNDLE_MESSAGE)
                                );
                            } else {
                                Log.e(TAG, "DOWNLOAD_SUCCESS_FLAG null");
                            }
                            break;
                        case DOWNLOAD_FAILURE_FLAG:
                            Log.e(TAG, "DOWNLOAD_FAILURE_FLAG");

                            bundle = (Bundle) msg.obj;
                            if(bundle != null) {
                                mAdapter.onDownloadFailure(
                                        bundle.getInt(BUNDLE_CODE),
                                        bundle.getString(BUNDLE_MESSAGE)
                                );
                            }
                            break;
                        case DOWNLOAD_ERROR_FLAG:
                            Throwable t = (Throwable) msg.obj;
                            mAdapter.onDownloadError(t);
                            break;
                        default:break;
                    }
                }
        };
    }

    /**
     * Download a file with a valid url.
     *
     * @param url   the particular destination url
     */
    public void downloadFile(String url) {
        Log.d(TAG, "download url:" + url);

        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("the url cannot be null");
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new IllegalArgumentException("the url is invalid:" + url + ",only supports http or https protocol");
        }

        if(mNotification != null) {
            mNotificationManager.notify(mConfig.getNotificationTag(),mConfig.getNotificationId(), mNotification);
        }

        Call<ResponseBody> call = mService.downloadFile(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response != null && response.isSuccessful()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            Log.d(TAG, "server contacted and has file");
                            boolean writtenToDisk = writeResponseBodyToDisk(response.body(), mConfig.getSavingFile(), response.code(), response.message());
                            Log.e(TAG, "writtenToDisk:" + writtenToDisk);
                            if (writtenToDisk) {
                                if (mAdapter != null) {
                                    Log.e("TAG", "mAdater not null");
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(BUNDLE_CODE, response.code());
                                    bundle.putString(BUNDLE_MESSAGE, response.raw().message());
                                    Message msg = mHandler.obtainMessage();
                                    msg.obj = bundle;
                                    msg.what = DOWNLOAD_SUCCESS_FLAG;
                                    mHandler.sendMessage(msg);
                                } else {
                                    Log.e("TAG", "mAdater null");
                                }

                                if (mConfig.isAutoInstallApk()) {
                                    installApk(mContext.get(), mConfig.getSavingFile());
                                }

                                Log.d(TAG, "file download was a success? " + writtenToDisk);

                                if (mNotification != null) {
                                    notifyNotification(100, 100);
                                }
                            }
                            Looper.loop();
                        }
                    }).start();
                } else {
                    Log.d(TAG, "server contact failed");
                    if (mNotification != null) {
                        notifyNotification(101, 100);
                    }

                    if(mAdapter != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(BUNDLE_CODE, response.code());
                        bundle.putString(BUNDLE_MESSAGE, response.raw().message());
                        Message msg = mHandler.obtainMessage();
                        msg.what = DOWNLOAD_FAILURE_FLAG;
                        msg.obj = bundle;
                        mHandler.sendMessage(msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
                if(mAdapter != null) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = DOWNLOAD_ERROR_FLAG;
                    msg.obj = t;
                    mHandler.sendMessage(msg);
                }

                if (mNotification != null) {
                    notifyNotification(101, 100);
                }
            }
        });
    }

    /**
     * Write response's body to a device's disk
     *
     * @param body the instance of {@link ResponseBody}
     * @param file used to saving the downloaded file
     * @param code {@link Response#code()}
     * @param message {@link Response#raw().message()}
     * @return
     */
    private boolean writeResponseBodyToDisk(ResponseBody body, File file, int code, String message) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);
                int downloadCount = 0;
                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    int rate = (int) (fileSizeDownloaded * 100 / fileSize);
                    Log.e(TAG, "rate:" + rate + ",fileSizeDownloaded:" + fileSizeDownloaded + ",fileSize:" + fileSize);
                    Log.e(TAG, "downloadCount:" + downloadCount);
                    if ((downloadCount == 0) || rate - 0 >= downloadCount) {
                        downloadCount += 5;

                        if(mAdapter != null) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(BUNDLE_CODE, code);
                            bundle.putString(BUNDLE_MESSAGE, message);
                            bundle.putLong(BUNDLE_FILE_TOTAL_SIZE, fileSize);
                            bundle.putLong(BUNDLE_FILE_DOWNLOADED_SIZE, fileSizeDownloaded);
                            Message msg = mHandler.obtainMessage();
                            msg.what = DOWNLOADING_FLAG;
                            msg.obj = bundle;
                            mHandler.sendMessage(msg);
                        }

                        if(mNotification != null) {
                            notifyNotification(rate, 100);
                        }
                    }
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                if (mAdapter != null) {
                    mAdapter.onDownloadError(e);
                }
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            mAdapter.onDownloadError(e);
            return false;
        }
    }

    /**
     * Notify that downloading rates have updated.
     *
     * @param percent current percent
     * @param length total percent
     */
    private void notifyNotification(long percent, long length) {
        if(mConfig.getDownloadCompleteTxtId() == 0) {
            Log.e(TAG, "you haven't designated an id to the view for showing tips when your a downloading task completes");
        }

        if(mConfig.getDownloadingProgressTxtId() == 0) {
            Log.e(TAG, "you haven't designated an id to the view for showing text information of downloading rates");
        }

        if(mConfig.getDownloadingProgressBarId() == 0) {
            Log.e(TAG, "you haven't designated an id to the view(progress bar) for showing downloading rates");
        }

        if(mRemoteView == null) {
            throw new NullPointerException("the remote view for showing notification's content(RemoteViews) can not be null");
        }

        if (percent == length) {
            mRemoteView.setTextViewText(mConfig.getDownloadCompleteTxtId(), mConfig.getDownloadSuccessTip());
        } else if (percent > length) {
            mRemoteView.setTextViewText(mConfig.getDownloadCompleteTxtId(), mConfig.getDownloadFailureTip());
        } else {
            mRemoteView.setTextViewText(mConfig.getDownloadCompleteTxtId(), mConfig.getDownloadingTip());
        }

        mRemoteView.setTextViewText(mConfig.getDownloadingProgressTxtId(), (percent * 100 / length) + "%");
        mRemoteView.setProgressBar(mConfig.getDownloadingProgressBarId(), (int) length, (int) percent, false);
        mNotification.contentView = mRemoteView;
        mNotificationManager.notify(mConfig.getNotificationTag(), mConfig.getNotificationId(), mNotification);
    }

    /**
     * Install apk file.
     *
     * @param context the instance of {@link Context}
     * @param file the file that the apk file located in
     */
    private void installApk(Context context, File file) {
        Intent intent = new Intent();
        //Android N install apk.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String packName = mConfig.getPackageName();
            if(TextUtils.isEmpty(packName)) {
                packName = context.getPackageName();
            }
            Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(), packName + ".provider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(intent);
    }

    /**
     * Release all the instances when necessary(for example: invoked when an activity finished.).
     */
    private void release() {
        mContext = null;
        mConfig = null;
        mAdapter = null;
        mService = null;
        mNotificationManager = null;
        mNotification = null;
        mRemoteView = null;
    }
}
