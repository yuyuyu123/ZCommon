package com.cc.android.zcommon.upload;

/**
 * The upload progress listener.
 * @Author:LiuLiWei
 */
public interface UploadProgressListener {
    void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish);
}
