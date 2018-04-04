package com.cc.android.zcommon.upload;

import android.content.Context;

import java.io.File;
import java.util.Map;

/**
 * This class describes an uploading-task's all configurations' information that can
 * impact the {@link com.ccclubs.common.upload.RetrofitUploadManager#uploadFile(File)}.
 *
 * Instances of this class should be obtained through
 * {@link Builder#build()}
 *
 * @Auther LiuLiWei
 */
public class RetrofitUploadConfig {

    /**
     * Context
     */
    private Context mContext;

    /**
     * The url linking to your file server.
     */
    private String mUploadUrl;

    /**
     * The file key used to match your server programming.
     */
    private String mFileKey;

    /**
     * The file's description to Upload.
     */
    private String mDescriptionString;

    /**
     * The params attached to the mUploadUrl.
     */
    private Map<String, Object> mParamsMap;

    /**
     * The file's name that you want to designate.
     */
    private String mFileName;

    /**
     * The retrofit upload adapter that handles
     * {@link RetrofitUploadManager}'s uploading results.
     */
    private RetrofitUploadAdapter mRetrofitUploadAdapter;

    /**
     * Private constructor.
     */
    private RetrofitUploadConfig() {

    }

    /**
     * Get a context instance.
     *
     * @return mContext
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Get the file server's url.
     *
     * @return  mUploadUrl
     */
    public String getUploadUrl() {
        return mUploadUrl;
    }

    /**
     * Get the file key.
     *
     * @return mFileKey
     */
    public String getFileKey() {
        return mFileKey;
    }

    /**
     * Get the file's description information to upload.
     *
     * @return mDescriptionString
     */
    public String getDescriptionString() {
        return mDescriptionString;
    }

    /**
     * Get params(Map<String, Object>) attached to mUploadUrl.
     *
     * @return mParamsMap
     */
    public Map<String, Object> getParamsMap() {
        return mParamsMap;
    }

    /**
     * Get file's name (if you designated) to upload.
     *
     * @return mFileName
     */
    public String getFileName() {
        return mFileName;
    }

    /**
     * Get an instance of {@link RetrofitUploadAdapter}.
     *
     * @return mRetrofitUploadAdapter
     */
    public RetrofitUploadAdapter getRetrofitUploadAdapter() {
        return mRetrofitUploadAdapter;
    }


    public static class Builder{
        /**
         * Default file key for mFileKey.
         */
        private static final String DEFAULT_FILE_KEY = "file";

        /**
         * Default file description for mDescriptionString.
         */
        private static final String DEFAULT_DESCRIPTION = "this is uploaded file description";

        private Context mContext;

        private String mUploadUrl;

        private String mFileKey = DEFAULT_FILE_KEY;

        private String mDescriptionString = DEFAULT_DESCRIPTION;

        private Map<String, Object> mParamsMap;

        private String mFileName;

        private RetrofitUploadAdapter mRetrofitUploadAdapter;

        /**
         * Builder's constructor.
         *
         * @param context an instance of {@link Context}
         */
        public Builder(Context context) {
            this.mContext = context;
        }

        /**
         * Assign mParamsMap a value.
         *
         * @param paramsMap
         * @return this object
         */
        public Builder setParamsMap(Map<String, Object> paramsMap) {
            this.mParamsMap = paramsMap;
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
         * Assign mUploadUrl a value.
         *
         * @param uploadUrl
         * @return this object
         */
        public Builder setUploadUrl(String uploadUrl) {
            this.mUploadUrl = uploadUrl;
            return this;
        }

        /**
         * Assign mFileKey a value.
         *
         * @param fileKey
         * @return this object
         */
        public Builder setFileKey(String fileKey) {
            this.mFileKey = fileKey;
            return this;
        }

        /**
         * Assign mDescriptionString a value.
         *
         * @param descriptionString
         * @return this object
         */
        public Builder setDescriptionString(String descriptionString) {
            this.mDescriptionString = descriptionString;
            return this;
        }

        /**
         * Assign mRetrofitUploadAdapter a value.
         *
         * @param retrofitUploadListener
         * @return this object
         */
        public Builder setRetrofitUploadAdapter(RetrofitUploadAdapter retrofitUploadListener) {
            this.mRetrofitUploadAdapter = retrofitUploadListener;
            return this;
        }

        /**
         * Apply the  builder's all configurations to {@link RetrofitUploadConfig}.
         *
         * @param retrofitUploadConfig
         */
        private void applyConfig(RetrofitUploadConfig retrofitUploadConfig) {
            retrofitUploadConfig.mContext = this.mContext;
            retrofitUploadConfig.mUploadUrl = this.mUploadUrl;
            retrofitUploadConfig.mFileKey = this.mFileKey;
            retrofitUploadConfig.mDescriptionString = this.mDescriptionString;
            retrofitUploadConfig.mParamsMap = this.mParamsMap;
            retrofitUploadConfig.mFileName = this.mFileName;
            retrofitUploadConfig.mRetrofitUploadAdapter = this.mRetrofitUploadAdapter;
        }

        /**
         * Get an instance of {@link RetrofitUploadConfig} and
         * apply the builder's all configurations to itself.
         *
         * @return an instance of {@link RetrofitUploadConfig}
         */
        public RetrofitUploadConfig build() {
            RetrofitUploadConfig retrofitUploadConfig = new RetrofitUploadConfig();
            applyConfig(retrofitUploadConfig);
            return retrofitUploadConfig;
        }

    }
}
