package com.cc.android.zcommon.upload;

import android.util.Log;

import java.io.File;
import java.lang.reflect.ParameterizedType;

/**
 * The retrofit upload adapter defined for a callback to be invoked when
 * {@link com.ccclubs.common.upload.RetrofitUploadManager#uploadFile(File)} passes
 * uploading results:
 * 1)success
 *      {@link #onUploadSuccess(int, String)} or {@link #onUploadSuccess(int, Object)}
 * 2)failure
 *      {@link #onUploadFailure(int, String)}
 * 3)error
 *      {@link #onUploadError(Throwable)}
 *
 * @Author LiuLiWei
 */
public abstract class RetrofitUploadAdapter<T> {
    /**
     * An identifier that identifies this class.
     */
    private static final String TAG = RetrofitUploadAdapter.class.getSimpleName();

    /**
     * The in-memory representation of a Java class.
     */
    private Class<T> mClazz;

    /**
     * Get T's real type.
     * @return
     */
    public Class<T> getClazz() {
        return mClazz;
    }

    /**
     * Default constructor of this class.
     */
    public RetrofitUploadAdapter() {
        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            mClazz =(Class<T>) pt.getActualTypeArguments()[0];
        }catch (Exception e) {
            Log.e(TAG, e + "");
            mClazz = null;
        }
    }

    /**
     * Invoked when a uploading-task finished successfully..
     *
     * @param code  the result code from {@link okhttp3.ResponseBody}
     * @param message the result message from {@link okhttp3.ResponseBody}
     */
    public void onUploadSuccess(int code, String message) {}

    /**
     * Invoked when a uploading-task finished successfully..
     *
     * @param code the result code from {@link okhttp3.ResponseBody}
     * @param bean the bean parsed by {@link com.google.gson.Gson}
     */
    public void onUploadSuccess(int code, T bean){}


    /**
     * Invoked when a uploading-task finished successfully..
     *
     * @param code the result code from {@link okhttp3.ResponseBody}
     * @param bean the bean parsed by {@link com.google.gson.Gson}
     */
    public void onUploadSuccess(int code, T bean, boolean allFinished){}

    /**
     * Invoked when a uploading-task failed.
     *
     * @param code  the result code from {@link okhttp3.ResponseBody}
     * @param message the result message from {@link okhttp3.ResponseBody}
     */
    public void onUploadFailure(int code, String message){}

    public void onUploadFailure(int code, String message, boolean allFinished){}

    /**
     * Invoked when some errors happened during a uploading process.
     *
     * @param t error information
     */
    public void onUploadError(Throwable t){}

    public void onUploadError(Throwable t, boolean allFinished){}
}
