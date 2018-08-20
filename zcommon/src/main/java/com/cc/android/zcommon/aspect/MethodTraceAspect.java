package com.cc.android.zcommon.aspect;

import android.util.Log;
import android.view.View;

import com.cc.android.zcommon.BuildConfig;
import com.cc.android.zcommon.utils.android.ViewClickUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Method trace aspect.
 *
 * @Author:LiuLiWei
 */
@Aspect
public class MethodTraceAspect {

    private static final String TAG = "MethodTrace";

    @Pointcut("execution(@com.cc.android.zcommon.aspect.MethodTrace * *(..))")
    public void methodTracePoint() {}

    @Around("methodTracePoint()")
    public void methodTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        final long start = System.currentTimeMillis();
        joinPoint.proceed();
        final long diff = System.currentTimeMillis() - start;
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "method:" + method + " resumed:" + diff + "ms");
        }
    }
}
