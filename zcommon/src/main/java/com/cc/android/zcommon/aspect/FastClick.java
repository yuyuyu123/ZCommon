package com.cc.android.zcommon.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fast click.
 *
 * @Author:LiuLiWei
 */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface FastClick {
    boolean isFastClick() default false;
    long interval() default 500;

}
