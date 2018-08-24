package com.cc.android.zcommon.aspect.permission;

import android.app.Fragment;
import android.content.Context;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Get permission aspect.
 *
 * @Author:LiuLiWei
 */
@Aspect
public class GPermissionAspect {

    @Pointcut("execution(@com.cc.android.zcommon.aspect.permission.GPermission * *(..))")
    public void gPermissionPoint() {}

    @Around("gPermissionPoint()")
    public void gPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        GPermission gPermission = method.getAnnotation(GPermission.class);
        Context context = null;
        final Object object = joinPoint.getThis();
        if (object instanceof Context) {
            context = (Context) object;
        } else if (object instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        } else if (object instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) object).getActivity();
        }
        if (context == null || null==gPermission) return;

        PermissionRequestsActivity.PermissionRequest(context, gPermission.value(), gPermission.requestCode(), new OnPermissionCallback() {
            @Override
            public void onPermissionGranted() {
                try {
                    joinPoint.proceed();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            @Override
            public void onPermissionDenied(int requestCode, List<String> denyList) {
                Class<?> cls = object.getClass();
                Method[] methods = cls.getDeclaredMethods();
                if (methods == null || methods.length == 0) return;
                for (Method method : methods) {
                    boolean isHasAnnotation = method.isAnnotationPresent(DPermission.class);
                    if (isHasAnnotation) {
                        method.setAccessible(true);
                        Class<?>[] types = method.getParameterTypes();
                        if (types == null || types.length != 1) return;
                        DPermission aInfo = method.getAnnotation(DPermission.class);
                        if (aInfo == null) return;
                        PermissionDenied bean = new PermissionDenied();
                        bean.setRequestCode(requestCode);
                        bean.setDenyList(denyList);
                        try {
                            method.invoke(object, bean);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onPermissionCanceled(int requestCode) {
                Class<?> cls = object.getClass();
                Method[] methods = cls.getDeclaredMethods();
                if (methods == null || methods.length == 0) return;
                for (Method method : methods) {
                    boolean isHasAnnotation = method.isAnnotationPresent(CPermission.class);
                    if (isHasAnnotation) {
                        method.setAccessible(true);
                        Class<?>[] types = method.getParameterTypes();
                        if (types == null || types.length != 1) return;
                        CPermission aInfo = method.getAnnotation(CPermission.class);
                        if (aInfo == null) return;
                        PermissionCanceled bean = new PermissionCanceled();
                        bean.setRequestCode(requestCode);
                        try {
                            method.invoke(object, bean);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
