package com.cc.android.zcommon.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * The retrofit download service that manages all primary time-consuming tasks(download).
 *
 * Instances of this class should be obtained through
 * {@link com.cc.android.zcommon.api.ManagerFactory#getManager(Class)}.
 *
 * @Auther LiuLiWei
 */
public interface RetrofitDownloadService {

    /**
     * Download file from an url
     *
     * @param fileUrl the particular destination url
     * @return download information
     */
    @Streaming @GET Call<ResponseBody> downloadFile(@Url String fileUrl);
}
