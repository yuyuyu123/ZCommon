# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\adt-bundle-windows-x86\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more orderProductItemBeen, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# 指定代码的压缩级别
-optimizationpasses 5
# 包明不混合大小写
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclassmembers
# 不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
# 预校验
-dontpreverify
# 不压缩输入的类文件
-dontshrink
# 混淆时是否记录日志
-verbose
# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 保护注解
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
# 优化 不优化输入的类文件
-dontoptimize
# 未混淆的类和成员
-printseeds seeds.txt
# 列出从 apk 中删除的代码
-printusage unused.txt
# 混淆前后的映射
-printmapping mapping.txt

# 保持哪些类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
# 崩溃的时候就有源文件和行号的信息了
#-keepattributes SourceFile,LineNumberTable
# 如果有引用v4包可以添加下面这行
-keep class android.support.**{*;}
-dontwarn android.support.**

-keepattributes EnclosingMethod
-keepattributes Signature

#
-keep class com.ccclubs.base.model.**{*;}
-keep public class com.ccclubs.rainbow.R$*{
    public static final int *;
}
# js brdge
#-keepclassmembers class com.ccclubs.dk.app.MyJsBridge {
#  public *;
#}

-keepclasseswithmembers class com.ccclubs.rainbow.database** {
     <fields>;
     <methods>;
}
-keepclasseswithmembers class com.ccclubs.base.model** {
     <fields>;
     <methods>;
}
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keepclassmembers class ** {
    public void onEvent*(**);
}

#========================gson================================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}


-dontwarn com.igexin.**
-keep class com.igexin.sdk.** {*;}

#========================protobuf================================
-keep class com.google.protobuf.** {*;}


# 以下包不进行过滤
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# okhttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase


# rxjava & rxandroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# alipay
#-keep class com.alipay.**{*;}
#-dontwarn com.alipay.**

# android.databinding
#-keep class android.databinding.**{*;}
#-dontwarn android.databinding.**

# lambdas.
-dontwarn java.lang.invoke.*

# Notification.setLatestEventInfo() 还是得友盟改代码才行
#-dontwarn com.umeng.update.c$a
#-dontwarn com.umeng.update.net.DownloadingService$1
#-dontwarn com.umeng.update.net.DownloadingService$b
#-dontwarn com.umeng.update.net.c$c

# eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#-keep class com.ccclubs.common.** { *; }
#-dontwarn com.ccclubs.common.**
#-keep public class com.ccclubs.common.R$*{
#    public static final int *;
#}


#-keep class com.ccclubs.base.** { *; }
#-dontwarn com.ccclubs.base.**
#-keep public class com.ccclubs.base.R$*{
#    public static final int *;
#}
#
#
#-keep class com.ccclubs.corelib.** { *; }
#-dontwarn com.ccclubs.corelib.**
#-keep public class com.ccclubs.corelib.R$*{
#    public static final int *;
#}
#
#-keep class com.ccclubs.maplib.** { *; }
#-dontwarn com.ccclubs.maplib.**
#-keep public class com.ccclubs.maplib.R$*{
#    public static final int *;
#}
#
#-keep class com.ccclubs.piclib.** { *; }
#-dontwarn com.ccclubs.piclib.**
#-keep public class com.ccclubs.piclib.R$*{
#    public static final int *;
#}
#
#-keep class com.ccclubs.weblib.** { *; }
#-dontwarn com.ccclubs.weblib.**
#-keep public class com.ccclubs.weblib.R$*{
#    public static final int *;
#}

# sharesdk
#-dontwarn 	cn.sharesdk.**
#-dontwarn 	com.mob.**
#-keep class cn.sharesdk.**{*;}
#-keep class cn.smssdk.**{*;}
#-keep class com.mob.**{*;}

# igexin
#-dontwarn com.igexin.**
#-keep class com.igexin.**{*;}

#X5
#-dontwarn com.tencent.**
#-keep class com.tencent.**{*;}

# RxCache
#-dontwarn io.rx_cache2.internal.**
#-keepclassmembers enum io.rx_cache2.Source { *; }
#-keepclassmembernames class * { @io.rx_cache2.* <methods>; }

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
# -keepresourcexmlelements manifest/application/meta-data@value=GlideModule
