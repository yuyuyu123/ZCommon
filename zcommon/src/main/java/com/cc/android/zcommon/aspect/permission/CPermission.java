package com.cc.android.zcommon.aspect.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permission canceled.
 *
 * @Author:LiuLiWei
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CPermission {

    int requestCode() default 0;
}
