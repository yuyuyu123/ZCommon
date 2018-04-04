package com.cc.android.zcommon.crash;

import android.os.Handler;
import android.os.Looper;

/**
 * Crash handler.
 *
 * @Author:LiuLiWei
 */
public final class CrashHandler {

    private static ExceptionHandler sExceptionHandler;
    private static boolean sInstalled = false;

    private CrashHandler() {}

    /**
     *
     * @param exceptionHandler
     */
    public static synchronized void init(ExceptionHandler exceptionHandler) {
        if (sInstalled) {
            return;
        }
        sInstalled = true;
        sExceptionHandler = exceptionHandler;

        new Handler(Looper.getMainLooper()).post(() -> {

            while (true) {
                try {
                    Looper.loop();
                } catch (Throwable e) {
                    if (sExceptionHandler != null) {
                        sExceptionHandler.handlerException(Looper.getMainLooper().getThread(), e);
                    }
                }
            }
        });

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            if (sExceptionHandler != null) {
                sExceptionHandler.handlerException(t, e);
            }
        });

    }

    /**
     *
     */
    public interface ExceptionHandler {

        void handlerException(Thread thread, Throwable throwable);
    }
}
