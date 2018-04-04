package com.cc.android.zcommon.utils.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * AssetUtils.
 *
 * Created by LiuLiWei on 2016/3/24.
 */
public class AssetUtils {

    public AssetUtils() {
         /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    /**
     * Get a bitmap from assets with it's name.
     * @param ctx
     * @param fileName
     *
     * @return
     *      the Bitmap
     */
    public static Bitmap getImageFromAssetsFile(Context ctx, String fileName)
    {
        Bitmap image = null;
        AssetManager am = ctx.getResources().getAssets();
        try
        {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return image;

    }

    /**
     * Get a file from asset with it's file name..
     * @param ctx
     * @param fileName
     * @return
     */
    public static InputStream getFileFromAssetsFile(Context ctx, String fileName) {
        InputStream is = null;
        AssetManager am = ctx.getResources().getAssets();
        try
        {
             is = am.open(fileName);
             is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return is;
    }
}
