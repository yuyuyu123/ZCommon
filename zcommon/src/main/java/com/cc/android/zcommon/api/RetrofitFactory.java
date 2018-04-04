package com.cc.android.zcommon.api;

import android.support.annotation.NonNull;


import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 参数与相应结果.
 * <br/>
 * TODO:<ul><li>1、如果有多个baseUrl</li><li>2、需要定制化OkHttpclent，如加入session id 等</li></ul>
 */
public class RetrofitFactory {
    private static Retrofit mRetrofit;
    private static OkHttpClient mClient;
    private static String baseUrl;

    public static void setBaseUrl(@NonNull String url) {
        baseUrl = url;
    }

    public static void setOkhttpClient(@NonNull OkHttpClient client) {
        mClient = client;
    }

    /**
     * 获取配置好的retrofit对象来生产Manager对象
     */
    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            if (baseUrl == null || baseUrl.length() <= 0) {
                throw new IllegalStateException("请在调用getFactory之前先调用setBaseUrl");
            }

            Retrofit.Builder builder = new Retrofit.Builder();

            builder.baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

            if (mClient != null) builder.client(mClient);

            mRetrofit = builder.build();
        }
        return mRetrofit;
    }

    /**
     * 获取配置好的retrofit对象来生产Manager对象
     */
    public static Retrofit getRetrofit(Converter.Factory factory) {
        if (baseUrl == null || baseUrl.length() <= 0) {
            throw new IllegalStateException("请在调用getFactory之前先调用setBaseUrl");
        }

        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(factory);

        if (mClient != null) builder.client(mClient);

        return builder.build();
    }
}
