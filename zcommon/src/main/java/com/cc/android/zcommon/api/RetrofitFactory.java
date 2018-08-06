package com.cc.android.zcommon.api;

import android.support.annotation.NonNull;
import android.text.TextUtils;


import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 用于获取配置好的retrofit对象, 通过设置{@link com.cc.android.zcommon.support.ConfigurationHelper#enableLoggingNetworkParams()}来启用网络请求
 * 参数与相应结果.
 * <br/>
 * TODO:<ul><li>1、如果有多个baseUrl</li><li>2、需要定制化OkHttpclent，如加入session id 等</li></ul>
 */
public class RetrofitFactory {
    private static Retrofit mRetrofit;
    private static Retrofit mRetrofitWithoutInterceptor;
    private static Map<String, Retrofit> mRetrofitMap = new HashMap<>();

    private static OkHttpClient mClient;
    private static Map<String, OkHttpClient> mClientMap = new HashMap<>();

    private static String baseUrl;
    private static Map<String, String> baseUrlMap = new HashMap<>();

    public static void setBaseUrl(@NonNull String url) {
        baseUrl = url;
    }

    public static void setBaseUrlMap(@NonNull Map<String, String> map) {
        baseUrlMap = map;
    }

    public static void setOkhttpClient(@NonNull OkHttpClient client) {
        mClient = client;
    }

    public static void setOkhttpClientMap(@NonNull Map<String, OkHttpClient> map) {
        mClientMap = map;
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

    public static Retrofit getRetrofit(boolean hasInterceptor) {
        if (hasInterceptor) return getRetrofit();

        if (mRetrofitWithoutInterceptor == null) {
            if (baseUrl == null || baseUrl.length() <= 0) {
                throw new IllegalStateException("请在调用getFactory之前先调用setBaseUrl");
            }

            Retrofit.Builder builder = new Retrofit.Builder();

            builder.baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
            mRetrofitWithoutInterceptor = builder.build();
        }
        return mRetrofitWithoutInterceptor;
    }


    public static Retrofit getRetrofit(String key) {
        if(!mClientMap.containsKey(key)) {
            return null;
        }
        OkHttpClient client = mClientMap.get(key);
        Retrofit retrofit = null;
        if(!mRetrofitMap.containsKey(key) || mRetrofitMap.get(key) == null) {
            if(!baseUrlMap.containsKey(key) || TextUtils.isEmpty(baseUrlMap.get(key))) {
                throw new IllegalStateException("请在调用getFactory之前先调用setBaseUrlMap");
            }
            final String url = baseUrlMap.get(key);
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(url)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
            if(client != null) {
                builder.client(client);
            }
            retrofit = builder.build();
        }
        return retrofit;
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
