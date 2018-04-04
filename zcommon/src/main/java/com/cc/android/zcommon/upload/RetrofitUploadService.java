package com.cc.android.zcommon.upload;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * The retrofit upload service that manages all primary time-consuming tasks(upload).
 *
 * Instances of this class should be obtained through
 * {@link com.ccclubs.common.api.ManagerFactory#getManager(Class)}.
 *
 * @Auther LiuLiWei
 */
public interface RetrofitUploadService {


    /**
     * Upload a file without any url params.
     *
     * @param url the url linking to your file server
     * @param description the file's description to upload
     * @param file the file to upload
     * @return the uploading file's results
     */
    @Multipart
    @POST
    Call<ResponseBody> uploadFileWithoutParams(@Url String url,
                                               @Part("description") RequestBody description, @Part MultipartBody.Part file);

    /**
     * Upload a file with some params attached to the url.
     *
     * @param url the url linking to your file server
     * @param map the params attached to the url
     * @param description the file's description to upload
     * @param file the file to upload
     * @return the uploading file's result
     */
    @Multipart
    @POST
    Call<ResponseBody> uploadFileWithParams(@Url String url, @QueryMap Map<String, Object> map,
                                            @Part("description") RequestBody description, @Part MultipartBody.Part file);

    @Multipart
    @POST
    Call<ResponseBody> uploadFileWithParams(@Url String url, @QueryMap Map<String, Object> map,
                                            @Part("description") RequestBody description, @Part List<MultipartBody.Part> parts);

    @POST
    Call<ResponseBody> uploadFileWithParams(@Url String url, @QueryMap Map<String, Object> map,
                                            @Body MultipartBody multipartBody);
}
