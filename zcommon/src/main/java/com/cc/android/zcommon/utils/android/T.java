package com.cc.android.zcommon.utils.android;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

/**
 * Toast统一管理类
 */
public class T {
    private T() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    public static boolean isShow = true;

    private static Toast toast=null;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if(null == context || TextUtils.isEmpty(message)) {
            return;
        }
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /***
     * 自定义 内容 位置的Toast
     * @param ctx
     * @param v
     * @param gravityType
     */
    public static void showLong(Context ctx, View v, int gravityType) {
        if (isShow) {
            Toast toast = new Toast(ctx);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(gravityType, 0, 0);
            toast.setView(v);
            toast.show();
        }

    }

    /***
     * 自定义 内容 位置的Toast
     * @param ctx
     * @param v
     * @param gravityType
     */

    public static void showShortTop(Context ctx, View v, int gravityType) {
        if (isShow) {
            Toast toast = new Toast(ctx);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(gravityType, 0, 0);
            toast.setView(v);
            toast.show();
        }
    }

    public static void  ToastCancel() {
        if (isShow) {
            if(toast!=null)
          toast.cancel();
        }
    }


}  