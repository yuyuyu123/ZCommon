package com.cc.android.zcommon.img;

/**
 *On image load listener.
 *
 * @Author:LiuLiWei
 */
public interface OnImgLoadListener {

     /**
      * Invoked when finished the image's loading.
      */
     void onLoadComplete();

     /**
      * Invoked when an exception occurs during loading an image.
      */
     void onLoadFailure(Exception e);
}
