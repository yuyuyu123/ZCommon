package com.cc.android.zcommon.img;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import java.io.File;

/**
 * An interface for {@link ZImageLoader}
 *
 * @Author:LiuLiWei
 */
public interface IImgLoader {

    /**
     * Display an image from a file into an ImageView.
     *
     * @param imageView
     * @param file
     */
    void displayImg(ImageView imageView, File file);


    /**
     * Display an image from drawable or mipmap into an ImageView.
     *
     * @param imageView
     * @param resId
     */
    void displayImg(ImageView imageView, @DrawableRes int resId);


    /**
     * Display an image from a specific url into an ImageView.
     *
     * @param imageView
     * @param path
     */
    void displayImg(ImageView imageView, String path);

    /**
     * Display an image from net with listener.
     *
     * @param imageView
     * @param path
     * @param listener
     */
    public void displayImg(ImageView imageView, String path, OnImgLoadListener listener);

    /**
     * Display an image from net with listener and animation.
     *
     * @param imageView
     * @param path
     * @param animId
     */
    void displayImg(ImageView imageView, String path, OnImgLoadListener listener, int animId);

    /**
     * Display an image by it's asset name into an ImageView.
     * You also can invoke displayImg(ImageView imageView, String path) by it's complete path.
     *
     * @param imageView
     * @param assetName
     */
    void displayAssetImg(ImageView imageView, String assetName);

    /**
     * Clear all of images' cache.
     */
    void clearAllMemoryCache();

    /**
     * Clear image's memory cache.
     */
    void clearMemory();

    /**
     * Clear image's disk cache in a background thread, otherwise the UI thread will be blocked.
     */
    void clearDiskCache();

    /**
     * Trim memory when the app need to release memory.
     *
     * @param level
     */
    void onTrimMemory(int level);
}
