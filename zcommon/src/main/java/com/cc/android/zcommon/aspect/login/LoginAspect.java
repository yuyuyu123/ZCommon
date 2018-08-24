package com.cc.android.zcommon.aspect.login;

import android.content.Context;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Login aspect.
 *
 * @Author:LiuLiWei
 */
@Aspect
public class LoginAspect {
    private static final String TAG = "LoginAspect";

    @Pointcut("execution(@com.cc.android.zcommon.aspect.login.Login * *(..))")
    public void loginPoint() {

    }

    @Around("loginPoint()")
    public void login(ProceedingJoinPoint joinPoint) throws Throwable{
        Log.e(TAG, "loginAspect----------------");
        Context context = LoginManager.get().getContext();
        if(null == context) {
            throw new NullPointerException("The context cannot be null.");
        }
        OnLoginListener listener = LoginManager.get().getListener();
        if(null == listener) {
            throw new NullPointerException("The OnLoginListener cannot be null.");
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Login login = signature.getMethod().getAnnotation(Login.class);
        if(null == login) {
            return;
        }
        if(listener.isLogin(context)) {
            Log.e(TAG, "loginAspect----------------11111111");
            joinPoint.proceed();
        } else {
            Log.e(TAG, "loginAspect----------------222");
            listener.login(context, login.val());
        }
    }
}
