package com.cc.android.zcommon.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TestAnnoAspect {

    private static final String TAG = "TestAnnoAspect";

    @Pointcut("execution(@com.cc.android.zcommon.aspect.TestAnnoTrace * *(..))")
    public void pointcut() {

    }
//
//    @Before("pointcut()")
//    public void before(JoinPoint point) {
//        Log.e(TAG, "@Before------------");
//    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "@Around before------------");
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        String name = signature.getName(); // 方法名：test
//        Method method = signature.getMethod(); // 方法：public void com.lqr.androidaopdemo.MainActivity.test(android.view.View)
//        Class returnType = signature.getReturnType(); // 返回值类型：void
//        Class declaringType = signature.getDeclaringType(); // 方法所在类名：MainActivity
//        String[] parameterNames = signature.getParameterNames(); // 参数名：view
//        Class[] parameterTypes = signature.getParameterTypes(); // 参数类型：View

//        TestAnnoTrace annotation = method.getAnnotation(TestAnnoTrace.class);
//        String value = annotation.value();
//        int type = annotation.type();

//        long beginTime = SystemClock.currentThreadTimeMillis();
        joinPoint.proceed();
//        long endTime = SystemClock.currentThreadTimeMillis();
//        long dx = endTime - beginTime;
//        System.out.println("耗时：" + dx + "ms");
        Log.e(TAG, "@Around after------------");
    }

//    @After("pointcut()")
//    public void after(JoinPoint point) {
//        Log.e(TAG, "@After------------");
//    }
//
//    @AfterReturning("pointcut()")
//    public void afterReturning(JoinPoint point, Object returnValue) {
//        Log.e(TAG, "@AfterReturning------------");
//    }
//
//    @AfterThrowing(value = "pointcut()", throwing = "ex")
//    public void afterThrowing(Throwable ex) {
//        Log.e(TAG, "@AfterThrowing------------");
//        Log.e(TAG, "ex = " + ex.getMessage());
//    }

}
