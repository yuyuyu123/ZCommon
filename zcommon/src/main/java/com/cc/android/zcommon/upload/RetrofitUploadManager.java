package com.cc.android.zcommon.upload;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.cc.android.zcommon.api.ManagerFactory;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The retrofit upload manager is a custom service that handles long-running HTTP or HTTPS uploads by retrofit.
 * Clients may want to upload a particular destination file to its' server, clients firstly should get an instance
 * of {@link RetrofitUploadConfig} to configure some params.
 * For example:
 *
 * 1).The destination url linking to file server;
 * 2).The params attached to the url;
 * 3).The file to upload;
 * 4).The key used to match your server programming.
 *
 * Apps that request upload through this API should register a retrofit upload adapter
 * ({@link RetrofitUploadAdapter}), if necessary, for listening
 * uploading results through {@link RetrofitUploadConfig}.
 *
 * Note that the application must have the {@link android.Manifest.permission#INTERNET}
 * permission to use this class.
 *
 * @Author LiuLiWei
 */
public class RetrofitUploadManager {
    /**
     * An identifier to identify RetrofitUploadManager.
     */
    private static final String TAG = RetrofitUploadManager.class.getSimpleName();

    /**
     * Context wrapped by {@link WeakReference}.
     */
    private WeakReference<Context> mContext;

    /**
     * The retrofit upload config manages an uploading-task's all configurations.
     */
    private RetrofitUploadConfig mConfig;

    /**
     * The listener for listening an uploading-task's uploading results(success,failure or error).
     */
    private RetrofitUploadAdapter mAdapter;

    /**
     * The service that manages all primary time-consuming(upload) tasks.
     */
    private RetrofitUploadService mService;

    /**
     *
     */
    private UploadProgressListener mUploadProgressListener;

    /**
     *
     * @param listener
     */
    public void setUploadProgressListener(UploadProgressListener listener) {
        this.mUploadProgressListener = listener;
    }

    private int mTotalFiles;
    private int mCurrentFinishedFiles;

    /**
     * The class's default constructor.
     *
     * @param retrofitUploadConfig
     *      the instance of {@link RetrofitUploadConfig}
     */
    public RetrofitUploadManager(RetrofitUploadConfig retrofitUploadConfig) {
        this.mConfig = retrofitUploadConfig;
        this.mContext = new WeakReference<>(retrofitUploadConfig.getContext());
        this.mAdapter = retrofitUploadConfig.getRetrofitUploadAdapter();
        this.mService = ManagerFactory.getFactory().getManager(RetrofitUploadService.class, false);
        this.mTotalFiles = 0;
        this.mCurrentFinishedFiles = 0;

    }

    /**
     * Upload a file with it's complete path.
     *
     * @param filePath  the file's complete path to upload
     */
    public void  uploadFile(String filePath){
        if(TextUtils.isEmpty(filePath)) {
            throw new NullPointerException("the file path can not be null");
        }
        Log.d(TAG, "filePath:" + filePath);

        File file = new File(filePath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        uploadFile(file);
    }

    /**
     * Upload a file with it's uri.
     *
     * @param fileUri the file's url to upload
     * Note:
     *  If clients wants't to upload a file with it's uri,
     *  here only supports upload an image file.
     */
    public void uploadFile(Uri fileUri) {
        if(fileUri == null) {
            throw new NullPointerException("the Uri can not be null");
        }

        String filePath = RetrofitUploadFileUtils.getRealFilePath(mContext.get(), fileUri);
        uploadFile(filePath);
    }

    /**
     * Upload a file with it's complete path(formed by it's directory and file name).
     *
     * @param dirPath the file's directory to upload
     * @param fileName  the file's name to upload
     */
    public void uploadFile(String dirPath, String fileName){
        if(TextUtils.isEmpty(dirPath)) {
            throw new NullPointerException("the file directory's path can not be null");
        }

        if(TextUtils.isEmpty(fileName)) {
            throw new NullPointerException("the file name can not be null");
        }

        String filePath = dirPath + fileName;
        uploadFile(filePath);
    }

    /**
     * Upload a file with it's complete path(formed by it's directory instance and file name).
     *
     * @param dir the file's directory to upload
     * @param fileName  the file's name to upload
     */
    public void uploadFile(File dir, String fileName) {
        if(dir == null) {
            throw new NullPointerException("the file directory can not be null");
        }

        if(!dir.isDirectory()) {
            throw new IllegalArgumentException("the dir to upload is not a directory");
        }

        if(!dir.exists()) {
            dir.mkdirs();
        }

        if(TextUtils.isEmpty(fileName)) {
            throw new NullPointerException("the file name can not be null");
        }

        File file = new File(dir, fileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        uploadFile(file);
    }

    public boolean checkAllFilesFinished() {
        return mTotalFiles == mCurrentFinishedFiles;
    }

    /**
     * Upload a file with it's instance.
     *
     * @param file the file to upload
     */
    public void uploadFile(File file) {
        if(file == null) {
            throw new NullPointerException("the file to upload can not be null");
        }

        if(!file.isFile()) {
            throw new IllegalArgumentException("the file to upload is not a file");
        }

        if(TextUtils.isEmpty(mConfig.getUploadUrl())) {
            throw new NullPointerException("the url to upload can not be null");
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(requestFile, new UploadProgressListener() {
            @Override
            public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {
                Log.d(TAG, "onProgress:" + hasWrittenLen + ",totalLen:" + totalLen);
                if(null != mUploadProgressListener) {
                    mUploadProgressListener.onProgress(hasWrittenLen, totalLen, hasFinish);
                } else {
                    Log.d(TAG, "mUploadProgressListener is not set.");
                }
            }
        });
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                mConfig.getFileKey(),
                mConfig.getFileName() == null ? file.getName() : mConfig.getFileName(),
                uploadFileRequestBody);
        RequestBody description = RequestBody.create(
                MediaType.parse("multipart/form-data"), mConfig.getDescriptionString());
        Call<ResponseBody> call;
        if(mConfig.getParamsMap() == null || mConfig.getParamsMap().size() == 0) {
             call = mService.uploadFileWithoutParams(mConfig.getUploadUrl(), description, body);
        } else {
            call = mService.uploadFileWithParams(mConfig.getUploadUrl(), mConfig.getParamsMap(), description, body);
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mCurrentFinishedFiles++;
                if (response != null) {
                    if(response.isSuccessful()) {
                        try {
                            String str = response.body().string();
                            if(mAdapter != null) {
                                mAdapter.onUploadSuccess(response.code(), str);
                                if(mAdapter.getClazz() != null) {
                                    mAdapter.onUploadSuccess(response.code(), new Gson().fromJson(str, mAdapter.getClazz()));
                                    mAdapter.onUploadSuccess(response.code(), new Gson().fromJson(str, mAdapter.getClazz()), checkAllFilesFinished());
                                }
                            }
                        } catch (IOException e) {
                            if(mAdapter != null) {
                                mAdapter.onUploadFailure(response.code(), "Response encounters an IOException:" + e);
                                mAdapter.onUploadFailure(response.code(), "Response encounters an IOException:" + e, checkAllFilesFinished());
                            }
                        } catch (IllegalStateException e) {
                            if(mAdapter != null) {
                                mAdapter.onUploadFailure(response.code(), "Response encounters an IllegalStateException:" + e);
                                mAdapter.onUploadFailure(response.code(), "Response encounters an IllegalStateException:" + checkAllFilesFinished());
                            }
                        } catch (Exception e)  {
                            if(mAdapter != null) {
                                mAdapter.onUploadFailure(response.code(), "Response encounters an Exception:" + e);
                                mAdapter.onUploadFailure(response.code(), "Response encounters an Exception:" + e, checkAllFilesFinished());
                            }
                        }
                    } else {
                        if(mAdapter != null) {
                            mAdapter.onUploadFailure(response.code(), response.message());
                            mAdapter.onUploadFailure(response.code(), response.message(), checkAllFilesFinished());
                        }
                    }
                } else {
                    if(mAdapter != null) {
                        mAdapter.onUploadFailure(-1, "response is null");
                        mAdapter.onUploadFailure(-1, "response is null", checkAllFilesFinished());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mCurrentFinishedFiles++;
                checkAllFilesFinished();
                if(mAdapter != null) {
                    mAdapter.onUploadError(t);
                    mAdapter.onUploadError(t, checkAllFilesFinished());
                }
            }
        });
    }

    public void uploadFiles(List<File> files) {
        if(null == files) {
            throw new NullPointerException("the files to upload can not be null");
        }
        if(files.size() == 0) {
            throw new IllegalArgumentException("the list of file's size must be bigger than zero");
        }
        for (File file : files) {
            if(null == file) {
                throw new NullPointerException("any file to upload can not be null");
            }
        }
        mTotalFiles = files.size();
        for (File file : files) {
            uploadFile(file);
        }
    }

    /**
     * Release all the instances when necessary(for example: invoked when an activity finished.).
     */
    public void release() {
        mConfig = null;
        mContext = null;
        mAdapter = null;
        mService = null;
    }

}
