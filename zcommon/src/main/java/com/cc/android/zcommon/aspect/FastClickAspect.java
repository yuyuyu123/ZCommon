package com.cc.android.zcommon.aspect;

import android.util.Log;
import android.view.View;

import com.cc.android.zcommon.utils.android.ViewClickUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Fast click aspect.
 *
 * @Author:LiuLiWei
 */
@Aspect
public class FastClickAspect {

    @Pointcut("execution(@com.cc.android.zcommon.aspect.FastClick * *(..))")
    public void fastClickPoint() {}

    @Around("fastClickPoint()")
    public void fastClick(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] args = joinPoint.getArgs();
        if(null == args || args.length == 0 || !(args[0] instanceof View)) {
            joinPoint.proceed();
            return;
        }
        View view = (View) args[0];
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        FastClick fastClick = method.getAnnotation(FastClick.class);
        final boolean isFastClick = fastClick.isFastClick();
        if(isFastClick) {
            joinPoint.proceed();
            return;
        }
        final long interval = fastClick.interval();
        if(ViewClickUtils.isSpecificTimeClick(view, interval)) {
            return;
        }
        joinPoint.proceed();
    }
}
