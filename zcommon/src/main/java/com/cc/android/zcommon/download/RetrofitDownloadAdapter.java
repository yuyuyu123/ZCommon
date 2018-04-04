package com.cc.android.zcommon.download;

/**
 * The retrofit download adapter defined for a callback to be invoked when
 * {@link RetrofitDownloadManager#downloadFile(String)} passes
 * downloading progress and download results:
 * 1)downloading
 *      {@link #onDownloading(int, String, long, long)}
 * 2)success
 *      {@link #onDownloadSuccess(int, String)}
 * 3)failure
 *      {@link #onDownloadFailure(int, String)}
 * 4)error
 *      {@link #onDownloadError(Throwable)}
 *
 * @Author LiuLiWei
 */
public abstract class RetrofitDownloadAdapter {

    /**
     * Invoked when a downloading-task is executing.
     *
     * @param code  the result code from {@link okhttp3.ResponseBody}
     * @param message the result message from {@link okhttp3.ResponseBody}
     * @param fileTotalSize  total size of the downloading-file
     * @param fileSizeDownloaded downloaded size of the downloading-file
     */
     public void onDownloading(int code, String message, long fileTotalSize, long fileSizeDownloaded) {}

    /**
     * Invoked when a downloading-task finished successfully.
     *
     * @param code  the result code from {@link okhttp3.ResponseBody}
     * @param message the result message from {@link okhttp3.ResponseBody}
     */
     public void onDownloadSuccess(int code, String message) {}

    /**
     * Invoked when a downloading-task failed.
     *
     * @param code  the result code from {@link okhttp3.ResponseBody}
     * @param message the result message from {@link okhttp3.ResponseBody}
     */
     public void onDownloadFailure(int code, String message) {}

    /**
     * Invoked when some errors happened during a downloading process.
     *
     * @param t error information
     */
     public void onDownloadError(Throwable t) {}
}
