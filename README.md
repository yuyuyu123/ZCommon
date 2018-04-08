# ZCommon

[![](https://jitpack.io/v/yuyuyu123/ZCommon.svg)](https://jitpack.io/#yuyuyu123/ZCommon)

# Crash Handler
为了防止项目出现bug崩溃，可以在自定义Application中使用CrashHandler类，例：
```java
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initCrashHandler();
        
    }
    
    private void initCrashHandler() {
        CrashHandler.init((thread, throwable) -> {
            new Handler(Looper.getMainLooper()).post(() -> {
                try {
                    //处理错误，可以上报，也可以不处理
                } catch (Throwable e) {

                }
            });
        });
    }
} 
```
# Activity
建议所有的项目可以根据自己的需求写一到多个基类Activity，继承RxBaseActivity,例:
```java
public abstract class DkBaseActivity<V extends RxBaseView, P extends RxBasePresenter<V>>
    extends RxBaseActivity<V, P> {

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(TAG);
    MobclickAgent.onResume(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(TAG);
    MobclickAgent.onPause(this);
  }
}
```

# Activity's Swipe Back

# Fragment

# Event Bus
```java
Activity中默认注册EventBus，Fragment中想要用EventBus,需要复写该方法：
  @Override
    protected boolean isBindEventBusHere() {
        return true;
    }
```
发送EventBus消息：
```java
EventBusHelper.post(new AppUpdateEvent());
```
# Net
1.获取网络类型、判断网络是否可用等集合在NetworkUtils工具类中。
2.网络状态变更时获取网络连接状态
  step1.让Activity实现NetChangeObserver接口，复写其中的方法
  step2.在onResume和onPause方法中分别调用相应的注册方法和反注册方法：
```java
private void registerNetReceiver() {
  NetStateReceiver.registerObserver(this);
  NetStateReceiver.registerNetworkStateReceiver(this);
}

private void unRegisterNetReceiver() {
  NetStateReceiver.removeRegisterObserver(this);
  NetStateReceiver.unRegisterNetworkStateReceiver(this);
}
```

# Image
1.加载普通图片
无论是加载网络图片、File中的图片还是Asset资源中的图片，均可调用
```java 
displayImg(ImageView imageView, String path)
```
当然也可以调用
```java
displayImg(ImageView imageView, File file)
```
加载文件中的图片，调用
```java
displayAssetImg(ImageView imageView, String assetName)
```
加载assets中的图片，示例：
```java
ZImageLoader.get(imageView.getContext()).displayImg(imageView, path);
```
2.加载图片时需要设置相应的error和holder图
```java
ZImageLoader loader =  ZImageLoader.get(imageView.getContext());
loader.setResPlaceHolderId(R.mipmap.bg_img_load_failed);//设置加载中的占位图
loader.setResErrorId(R.mipmap.bg_img_load_failed);//设置加载错误时的占位图
loader.displayImg(imageView, path);
```   
3.加载圆形或圆角图片
  1）加载圆形图片：
```java
ZImageLoader loader =  ZImageLoader.get(imageView.getContext());
loader.setType(ZImageLoader.IMG_TYPE.CIRCLE);
loader.displayImg(imageView, path);
```   
  2）加载圆角图片
```java
ZImageLoader loader =  ZImageLoader.get(imageView.getContext());
loader.setType(ZImageLoader.IMG_TYPE.ROUND_CORNER);
loader.setRoundingRadius(20);
loader.displayImg(imageView, path);
```
     
4.加载图片支持设置回调
```java
ZImageLoader loader =  ZImageLoader.get(imageView.getContext());
loader.setType(ZImageLoader.IMG_TYPE.ROUND_CORNER);
loader.setRoundingRadius(20);
loader.displayImg(imageView, path, new OnImgLoadListener() {
   @Override
   public void onLoadComplete() {
     //图片加载完成
   }

   @Override
   public void onLoadFailure(Exception e) {
     //图片加载失败
   }
 });
 ```
# Fast Click
快速点击处理
1.用ViewClickUtils处理快速点击，间隔默认500ms，支持自定义时间，例：
```java
@Override
public void onClick(View v) {
  if (ViewClickUtils.isFastClick(v)) {//默认500ms，建议用这个
      return;
  }
}

@Override
public void onClick(View v) {
   if(ViewClickUtils.isSpecificTimeClick(v, 600)) {//指定600ms
      return;
   }
}
```  
2.也可以用FastClickAgent来处理快速点击，实际上他就是一个OnClickListener的扩展类，例：
```java
private FastClickAgent mFastClickListener = new FastClickAgent(view -> {
  final long id = view.getId();
  if(id == R.id.id_item_2) {
     //具体的点击事件
  }
});
view.setOnClickListener(mFastClickListener);
```  

# Download
1.下载apk文件，示例：
```java
private void downloadApk(String url) {
  File savingFile = new File(getExternalFilesDir(null) + File.separator + "rainbow.apk");
  RetrofitDownloadConfig retrofitDownloadConfig = new RetrofitDownloadConfig.Builder(this)
     .setSavingFile(savingFile)//设置保存的文件
     .setNotification(null)//通知栏显示下载进度，没有设置为null
     .setPackageName(getPackageName())//下载apk文件需要安装时建议手动设置包名，当然也可以不设置
     .setAutoInstallApk(true)//是否自动安装apk
     .setRetrofitDownloadAdapter(new RetrofitDownloadAdapter() {//下载进度监听
          @Override
          public void onDownloading(int code, String message, long fileTotalSize, long fileSizeDownloaded) {
             super.onDownloading(code, message, fileTotalSize, fileSizeDownloaded);
             if (fileTotalSize <= 0) return;
               final int percent = (int) ((fileSizeDownloaded * 1.f / fileTotalSize) * 100);
               showDownloadProgressDialog(percent);
             }

           @Override
           public void onDownloadSuccess(int code, String message) {
              //下载成功
              super.onDownloadSuccess(code, message);
              dismissDownloadProgressDialog();
           }

            @Override
            public void onDownloadFailure(int code, String message) {
                //下载失败
                super.onDownloadFailure(code, message);
                dismissDownloadProgressDialog();
                T.showShort(HomeActivity.this, getStringResource(R.string.download_failure) + ":" + message);
            }

            @Override
            public void onDownloadError(Throwable t) {
                //下载出错
                super.onDownloadError(t);
                dismissDownloadProgressDialog();
             }
            }).build();
        RetrofitDownloadManager retrofitDownloadManager = new RetrofitDownloadManager(retrofitDownloadConfig);
        retrofitDownloadManager.downloadFile(url);
    }
```
注意，在Android7.0以上的手机，需要提供相应的provider文件，并在AndroidManifest文件中注册，具体可以参考我们的项目
2.下载普通文件
   下载普通文件和apk类似
# Upload
支持上传单文件和多文件，例：
```java
private void uploadFiles(File[] files) {//上传单文件只需要写一个file即可
  Map<String, Object> map = new HashMap<>();
  map.put("app", URLHelper.IMG_APP_VALUE);
  map.put("key", URLHelper.IMG_KEY_VALUE);
  Map<String, Object> paramMap = new HashMap<>();
  paramMap.put("json", new Gson().toJson(map).toString());//上传的参数
  RetrofitUploadConfig retrofitUploadConfig = new RetrofitUploadConfig.Builder(context)
    .setUploadUrl(URLHelper.IMG_SERVER).setParamsMap(paramMap).setFileKey("file")
    .setDescriptionString("image for getting evidence")
    .setRetrofitUploadAdapter(new RetrofitUploadAdapter<PhotoModel>() {
         @Override
         public void onUploadSuccess(int code, PhotoModel bean, boolean allFinished) {
                //上传成功
         }

         @Override
         public void onUploadFailure(int code, String message, boolean allFinished) {
               //上传失败
         }

         @Override
         public void onUploadError(Throwable t, boolean allFinished) {
               //上传发生错误
         }
                }).build();
        List<File> list = new ArrayList<>();
        for (File file : files) {
            if (null == file) {
                continue;
            }
            list.add(file);
        }
        RetrofitUploadManager retrofitUploadManager = new RetrofitUploadManager
                (retrofitUploadConfig);
        retrofitUploadManager.uploadFiles(list);
    }
```
# Cache
暂时只提供ACache这个类作轻量级的缓存，后续会引入接口缓存的方案
# Animation
暂时忽略
# Utils
ZCommon下面提供了许多有用的Utils，比如跟Log相关的L类，跟toast相关的T类等等，待进一步完善。

# Thanks
