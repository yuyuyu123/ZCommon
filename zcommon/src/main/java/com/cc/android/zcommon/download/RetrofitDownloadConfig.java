package com.cc.android.zcommon.download;

import android.app.Notification;
import android.content.Context;
import android.widget.RemoteViews;

import java.io.File;

/**
 * This class describes a downloading-task's all configurations' information that can
 * impact the {@link com.ccclubs.common.download.RetrofitDownloadManager#downloadFile(String)}.
 *
 * Instances of this class should be obtained through
 * {@link Builder#build()}
 *
 * @Auther LiuLiWei
 */
public class RetrofitDownloadConfig {

    /**
     * Context
     */
    private Context mContext;

    /**
     * The retrofit download adapter that handles
     * {@link RetrofitDownloadManager}'s downloading events.
     */
    private RetrofitDownloadAdapter mRetrofitDownloadAdapter;

    /**
     * The file to save the downloaded file.
     */
    private File mSavingFile;

    /**
     * Saving-file's name.
     */
    private String mFileName;

    /**
     * An instance of {@link Notification}
     */
    private Notification mNotification = null;

    /**
     * The notification's tag.
     */
    private String mNotificationTag = null;

    /**
     * The notification's id.
     */
    private int mNotificationId;

    /**
     * The notification's content view.
     */
    private RemoteViews mRemoteView = null;

    /**
     *  Tip shows in notification's content view
     *  when a downloading-task finished successfully.
     */
    private String mDownloadSuccessTip;

    /**
     *  Tip shows in notification's content view
     *  when a downloading-task is executing.
     */
    private String mDownloadingTip;

    /**
     *  Tip shows in notification's content view
     *  when a downloading-task failed.
     */
    private String mDownloadFailureTip;

    /**
     * The view's id to show mDownloadSuccessTip or mDownloadFailureTip or mDownloadingTip.
     */
    private int mDownloadCompleteTxtId;

    /**
     * The view's id to show download text progress(such as: 10%).
     */
    private int mDownloadingProgressTxtId;

    /**
     * The progress bar's id to show download-rates.
     */
    private int mDownloadingProgressBarId;

    /**
     * The app's package name.
     */
    private String mPackageName;

    /**
     * Judge if automatically install apk file.
     */
    private boolean mAutoInstallApk = true;

    /**
     * Get a context instance.
     *
     * @return mContext
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Get an instance of {@link RetrofitDownloadAdapter}
     *
     * @return mRetrofitDownloadAdapter
     */
    public RetrofitDownloadAdapter getRetrofitDownloadAdapter() {
        return mRetrofitDownloadAdapter;
    }

    /**
     * Get the saving file.
     *
     * @return mSavingFile
     */
    public File getSavingFile() {
        return mSavingFile;
    }

    /**
     * Get the saving file's name.
     *
     * @return mFileName
     */
    public String getFileName() {
        return mFileName;
    }

    /**
     * Get an instance of {@link Notification}
     *
     * @return mNotification
     */
    public Notification getNotification() {
        return mNotification;
    }

    /**
     * Get notification's tag.
     *
     * @return mNotificationTag
     */
    public String getNotificationTag() {
        return mNotificationTag;
    }

    /**
     * Get notification's id.
     *
     * @return mNotificationId
     */
    public int getNotificationId() {
        return mNotificationId;
    }

    /**
     * Get the remote view for the notification's content.
     *
     * @return mRemoteView
     */
    public RemoteViews getRemoteView() {
        return mRemoteView;
    }

    /**
     * Get download failure tip.
     *
     * @return mDownloadFailureTip
     */
    public String getDownloadFailureTip() {
        return mDownloadFailureTip;
    }

    /**
     * Get downloading tip.
     *
     * @return mDownloadingTip
     */
    public String getDownloadingTip() {
        return mDownloadingTip;
    }

    /**
     * Get download success tip.
     *
     * @return mDownloadSuccessTip
     */
    public String getDownloadSuccessTip() {
        return mDownloadSuccessTip;
    }

    /**
     * Get downloading progress bar's Id
     *
     * @return mDownloadingProgressBarId
     */
    public int getDownloadingProgressBarId() {
        return mDownloadingProgressBarId;
    }

    /**
     * Get downloading progress text view's Id
     *
     * @return mDownloadingProgressTxtId
     */
    public int getDownloadingProgressTxtId() {
        return mDownloadingProgressTxtId;
    }

    /**
     * Get download complete(success, failure or error) text view's Id
     *
     * @return mDownloadCompleteTxtId
     */
    public int getDownloadCompleteTxtId() {
        return mDownloadCompleteTxtId;
    }

    /**
     * Get the app's package name.
     * @return mPackageName
     */
    public String getPackageName() {
        return mPackageName;
    }

    /**
     * Get if need automatically install apk file.
     *
     * @return mAutoInstallApk
     */
    public boolean isAutoInstallApk() {
        return mAutoInstallApk;
    }

    /**
     * Private constructor.
     */
    private RetrofitDownloadConfig() {}

    public static class Builder {
        /**
         * Default downloading success tip.
         */
        private static final String DEFAULT_DOWNLOAD_SUCCESS_TIP = "下载完成";

        /**
         * Default downloading failure tip.
         */
        private static final String DEFAULT_DOWNLOAD_FAILURE_TIP = "下载出错,请检查网络连接";

        /**
         * Default downloading tip.
         */
        private static final String DEFAULT_DOWNLOADING_TIP = "正在下载...";

        /**
         * Default notification tag.
         */
        private static final String DEFAULT_NOTIFICATION_TAG = RetrofitDownloadManager.class.getSimpleName();

        /**
         * Default notification id.
         */
        private static final int DEFAULT_NOTIFICATION_ID = 100000;

        private Context mContext;

        private RetrofitDownloadAdapter mRetrofitDownloadAdapter;

        private File mSavingFile;

        private String mFileName;

        private Notification mNotification = null;

        private String mNotificationTag = DEFAULT_NOTIFICATION_TAG;

        private int mNotificationId = DEFAULT_NOTIFICATION_ID;

        private RemoteViews mRemoteView = null;

        private String mDownloadSuccessTip = DEFAULT_DOWNLOAD_SUCCESS_TIP;

        private String mDownloadingTip = DEFAULT_DOWNLOADING_TIP;

        private String mDownloadFailureTip = DEFAULT_DOWNLOAD_FAILURE_TIP;

        private int mDownloadCompleteTxtId;

        private int mDownloadingProgressTxtId;

        private int mDownloadingProgressBarId;

        private String mPackageName;

        private boolean mAutoInstallApk = true;

        /**
         * Builder's constructor.
         *
         * @param context an instance of {@link Context}
         */
        public Builder(Context context) {
            this.mContext = context;
        }

        /**
         * Assign mRetrofitUploadAdapter a value.
         *
         * @param retrofitDownloadAdapter
         * @return this object
         */
        public Builder setRetrofitDownloadAdapter(RetrofitDownloadAdapter retrofitDownloadAdapter) {
            this.mRetrofitDownloadAdapter = retrofitDownloadAdapter;
            return this;
        }

        /**
         * Assign mSavingFile a value.
         *
         * @param savingFile
         * @return this object
         */
        public Builder setSavingFile(File savingFile) {
            this.mSavingFile = savingFile;
            return this;
        }

        /**
         * Assign mFileName a value.
         *
         * @param fileName
         * @return this object
         */
        public Builder setFileName(String fileName) {
            this.mFileName = fileName;
            return this;
        }

        /**
         * Assign mNotification a value.
         *
         * @param notification
         * @return this object
         */
        public Builder setNotification(Notification notification) {
            this.mNotification = notification;
            return this;
        }

        /**
         * Assign mNotificationTag a value.
         *
         * @param notificationTag
         * @return this object
         */
        public Builder setNotificationTag(String notificationTag) {
            if(notificationTag != null && !notificationTag.equals("")) {
                this.mNotificationTag = notificationTag;
            }
            return this;
        }

        /**
         * Assign mNotificationId a value.
         *
         * @param notificationId
         * @return this object
         */
        public Builder setNotificationId(int notificationId) {
            if(notificationId != 0) {
                this.mNotificationId = notificationId;
            }
            return this;
        }

        /**
         * Assign mRemoteView a value.
         *
         * @param remoteView
         * @return this object
         */
        public Builder setRemoteView(RemoteViews remoteView) {
            this.mRemoteView = remoteView;
            return this;
        }

        /**
         * Assign mDownloadSuccessTip a value.
         *
         * @param downloadSuccessTip
         * @return this object
         */
        public Builder setDownloadSuccessTip(String downloadSuccessTip) {
            if(downloadSuccessTip != null && !downloadSuccessTip.equals("")) {
                this.mDownloadSuccessTip = downloadSuccessTip;
            }
            return this;
        }

        /**
         * Assign mDownloadingTip a value.
         *
         * @param downloadingTip
         * @return this object
         */
        public Builder setDownloadingTip(String downloadingTip) {
            if(downloadingTip != null && !downloadingTip.equals("")) {
                this.mDownloadingTip = downloadingTip;
            }
            return this;
        }

        /**
         * Assign mDownloadFailureTip a value.
         *
         * @param downloadFailureTip
         * @return this object
         */
        public Builder setDownloadFailureTip(String downloadFailureTip) {
            if(downloadFailureTip != null && !downloadFailureTip.equals("")) {
                this.mDownloadFailureTip = downloadFailureTip;
            }
            return this;
        }

        /**
         * Assign mDownloadCompleteTxtId a value.
         *
         * @param downloadCompleteTxtId
         * @return this object
         */
        public Builder setDownloadCompleteTxtId(int downloadCompleteTxtId) {
            this.mDownloadCompleteTxtId = downloadCompleteTxtId;
            return this;
        }

        /**
         * Assign mDownloadingProgressTxtId a value.
         *
         * @param downloadingProgressTxtId
         * @return this object
         */
        public Builder setDownloadingProgressTxtId(int downloadingProgressTxtId) {
            this.mDownloadingProgressTxtId = downloadingProgressTxtId;
            return this;
        }

        /**
         * Assign mDownloadingProgressBarId a value.
         *
         * @param downloadingProgressBarId
         * @return this object
         */
        public Builder setDownloadingProgressBarId(int downloadingProgressBarId) {
            this.mDownloadingProgressBarId = downloadingProgressBarId;
            return this;
        }

        /**
         * Set the app's package name.
         * @param packageName
         * @return
         */
        public Builder setPackageName(String packageName) {
            this.mPackageName = packageName;
            return this;
        }

        /**
         * Assign mAutoInstallApk a value(false or true).
         *
         * @param autoInstallApk
         * @return this object
         */
        public Builder setAutoInstallApk(boolean autoInstallApk) {
            this.mAutoInstallApk = autoInstallApk;
            return this;
        }

        /**
         * Apply the  builder's all configurations to {@link RetrofitUploadConfig}.
         *
         * @param retrofitDownloadConfig
         */
        private void applyConfig(RetrofitDownloadConfig retrofitDownloadConfig) {
            retrofitDownloadConfig.mContext = this.mContext;
            retrofitDownloadConfig.mRetrofitDownloadAdapter = this.mRetrofitDownloadAdapter;
            retrofitDownloadConfig.mSavingFile = this.mSavingFile;
            retrofitDownloadConfig.mFileName = this.mFileName;
            retrofitDownloadConfig.mNotification = this.mNotification;
            retrofitDownloadConfig.mNotificationTag = this.mNotificationTag;
            retrofitDownloadConfig.mNotificationId = this.mNotificationId;
            retrofitDownloadConfig.mRemoteView = this.mRemoteView;
            retrofitDownloadConfig.mDownloadSuccessTip = this.mDownloadSuccessTip;
            retrofitDownloadConfig.mDownloadFailureTip = this.mDownloadFailureTip;
            retrofitDownloadConfig.mDownloadingTip = this.mDownloadingTip;
            retrofitDownloadConfig.mDownloadCompleteTxtId = this.mDownloadCompleteTxtId;
            retrofitDownloadConfig.mDownloadingProgressTxtId = this.mDownloadingProgressTxtId;
            retrofitDownloadConfig.mDownloadingProgressBarId = this.mDownloadingProgressBarId;
            retrofitDownloadConfig.mPackageName = this.mPackageName;
            retrofitDownloadConfig.mAutoInstallApk = this.mAutoInstallApk;
        }

        /**
         * Get an instance of {@link RetrofitDownloadConfig} and
         * apply the builder's all configurations to itself.
         *
         * @return an instance of {@link RetrofitDownloadConfig}
         */
        public RetrofitDownloadConfig build() {
            RetrofitDownloadConfig retrofitDownloadConfig = new RetrofitDownloadConfig();
            applyConfig(retrofitDownloadConfig);
            return retrofitDownloadConfig;
        }
    }
}
