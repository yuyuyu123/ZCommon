package com.cc.android.zcommon.aspect.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Get permission.
 *
 * @Author:LiuLiWei
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GPermission {

    String[] value();

    int requestCode() default 0;
}
