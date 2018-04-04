package com.cc.android.zcommon.img;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * ZImageLoader for loading any images based on Glide.
 *
 * @Author:LiuLiWei
 */
public class ZImageLoader implements IImgLoader{

    public enum IMG_TYPE {
        NORMAL, CIRCLE, ROUND_CORNER
    }

    private WeakReference<Context> mContext;
    private RequestOptions mRequestOptions;
    private Handler mMainThreadHandler;
    private IMG_TYPE mType = IMG_TYPE.NORMAL;
    private int mRoundingRadius;

    private int mResPlaceHolderId;
    private int mResErrorId;
    private int mResFallbackId;

    /**
     * Get an instance.
     * @param context
     * @return
     */
    public static ZImageLoader get(Context context) {
        return new ZImageLoader(context);
    }

    /**
     * Initialization.
     *
     * @param context
     */
    private ZImageLoader(Context context) {
        mContext = new WeakReference<>(context);
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Check validity.
     * @param imageView
     * @param path
     * @return
     */
    private boolean isNotValid(ImageView imageView, String path) {
        if (getContext() == null || imageView == null || TextUtils.isEmpty(path)) {
            return true;
        }
        initRequestOptions();
        return false;
    }

    /**
     * Initialize request options.
     */
    private void initRequestOptions() {
        if (getRequestOptions() == null) {
            setRequestOptions(getDefaultRequestOptions());
        }
        if(mType == IMG_TYPE.CIRCLE) {
            getRequestOptions().transform(new CircleCrop());
        }
        if(mType == IMG_TYPE.ROUND_CORNER) {
            getRequestOptions().transform(new RoundedCorners(getRoundingRadius()));
        }
    }

    @Override
    public void displayImg(ImageView imageView, File file) {
        if(!file.exists()) {
            return;
        }
        if(file.isDirectory()) {
            return;
        }
        if(isNotValid(imageView, file.getPath())) {
            return;
        }
        requestBuilder(file).into(imageView);
    }


    @Override
    public void displayImg(ImageView imageView, @DrawableRes int resId) {
        if(isNotValid(imageView, resId + "")) {
            return;
        }
        requestBuilder(resId).into(imageView);
    }

    @Override
    public void displayImg(ImageView imageView, String path) {
        displayImg(imageView, path, null);
    }

    @Override
    public void displayImg(ImageView imageView, String path, OnImgLoadListener listener) {
        displayImg(imageView, path, listener, -1);
    }

    @Override
    public void displayImg(ImageView imageView, String path, OnImgLoadListener listener, int animId) {
        if (isNotValid(imageView, path)) {
            return;
        }
        requestBuilder(path, listener, animId).into(imageView);
    }

    @Override
    public void displayAssetImg(ImageView imageView, String assetName) {
        displayImg(imageView, "file:///android_asset/" + assetName);
    }

    /**
     * Get content.
     * @return
     */
    public Context getContext() {
        return mContext.get();
    }

    /**
     * Get a request builder.
     * @param obj
     * @return
     */
    private RequestBuilder<Drawable> requestBuilder(Object obj) {
        return requestBuilder(obj, null).transition(DrawableTransitionOptions.withCrossFade());
    }

    /**
     * Get a  request builder with listener..
     * @param obj
     * @param listener
     * @return
     */
    private RequestBuilder<Drawable> requestBuilder(Object obj, OnImgLoadListener listener) {
        RequestBuilder<Drawable> builder = Glide.with(getContext())
                .load(obj)
                .apply(getRequestOptions());
        if (listener != null) {
            builder.listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException ge, Object model, Target<Drawable> target, boolean isFirstResource) {
                    mainThreadCallback(listener, ge);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    mainThreadCallback(listener, null);
                    return false;
                }
            });
        }
        return builder;
    }

    /**
     * @param obj
     * @param animId
     * @return
     */
    private RequestBuilder<Drawable> requestBuilder(Object obj, OnImgLoadListener listener, int animId) {
        if (animId != -1 && animId != 0) {
            return requestBuilder(obj, listener).transition(GenericTransitionOptions.with(animId));
        }
        return requestBuilder(obj, listener);
    }


    /**
     * Listen the image's loading state in UI thread.
     * @param listener
     * @param e
     */
    private void mainThreadCallback(OnImgLoadListener listener, Exception e) {
        mMainThreadHandler.post(() -> {
            if (null != e) {
                listener.onLoadFailure(e);
            } else {
                listener.onLoadComplete();
            }
        });
    }

    @Override
    public void clearAllMemoryCache() {
        clearMemory();
        clearDiskCache();
    }

    @Override
    public void clearMemory() {
        Glide.get(getContext()).clearMemory();
    }

    @Override
    public void clearDiskCache() {
        new Thread(() -> Glide.get(getContext()).clearDiskCache()).start();
    }

    @Override
    public void onTrimMemory(int level) {
        Glide.get(getContext()).onTrimMemory(level);
    }

    /**
     * Get default request options when the user did not provider it's own request options.
     *
     * @return
     */
    private RequestOptions getDefaultRequestOptions() {
        RequestOptions options = new RequestOptions();
        options.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(mResPlaceHolderId)
                .error(mResErrorId)
                .fallback(mResFallbackId);
        return options;
    }

    /**
     * Get request options.
     *
     * @return mRequestOptions
     */
    public RequestOptions getRequestOptions() {
        return mRequestOptions;
    }

    /**
     * Set request options.
     *
     * @param options
     */
    public void setRequestOptions(RequestOptions options) {
        mRequestOptions = options;
    }

    /**
     * Set image type {@link IMG_TYPE}
     * @return
     */
    public IMG_TYPE getType() {
        return mType;
    }

    /**
     * Get the image type
     * @param type
     */
    public void setType(IMG_TYPE type) {
        this.mType = type;
    }

    /**
     * Get rounding radius.
     * @return
     */
    public int getRoundingRadius() {
        return mRoundingRadius;
    }

    /**
     * Set rounding radius.
     * @param roundingRadius
     */
    public void setRoundingRadius(int roundingRadius) {
        this.mRoundingRadius = roundingRadius;
    }

    /**
     * Get resource's id for error.
     * @return
     */
    public int getResErrorId() {
        return mResErrorId;
    }

    /**
     * Set drawable resource's id for error.
     * @param resErrorId
     */
    public void setResErrorId(@DrawableRes int resErrorId) {
        this.mResErrorId = resErrorId;
    }

    /**
     * Get resource's id for place holder.
     * @return
     */
    public int getResPlaceHolderId() {
        return mResPlaceHolderId;
    }

    /**
     * Set drawable resource's id for place holder.
     * @param resPlaceHolderId
     */
    public void setResPlaceHolderId(@DrawableRes int resPlaceHolderId) {
        this.mResPlaceHolderId = resPlaceHolderId;
    }

    /**
     * Get resource's id for fall back.
     * @return
     */
    public int getResFallbackId() {
        return mResFallbackId;
    }

    /**
     * Set drawable resource's id for fall back.
     * @param resFallbackId
     */
    public void setResFallbackId(@DrawableRes int resFallbackId) {
        this.mResFallbackId = resFallbackId;
    }
}
