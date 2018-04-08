# ZCommon

[![](https://jitpack.io/v/yuyuyu123/ZCommon.svg)](https://jitpack.io/#yuyuyu123/ZCommon)

# Activity

# Fragment

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
无论是加载网络图片、File中的图片还是Asset资源中的图片，统一调用displayImg(ImageView imageView, String path)方法，
当然也可以调用displayImg(ImageView imageView, File file)方法加载文件中的图片，调用displayAssetImg(ImageView imageView, String assetName)加载assets中的图片，示例：ZImageLoader.get(imageView.getContext()).displayImg(imageView, path);

2.加载图片时需要设置相应的error和holder图
     ZImageLoader loader =  ZImageLoader.get(imageView.getContext());
     loader.setResPlaceHolderId(R.mipmap.bg_img_load_failed);//设置加载中的占位图
     loader.setResErrorId(R.mipmap.bg_img_load_failed);//设置加载错误时的占位图
     loader.displayImg(imageView, path);
     
3.加载圆形或圆角图片
  1）加载圆形图片：
     ZImageLoader loader =  ZImageLoader.get(imageView.getContext());
     loader.setType(ZImageLoader.IMG_TYPE.CIRCLE);
     loader.displayImg(imageView, path);
     
  2）加载圆角图片
     ZImageLoader loader =  ZImageLoader.get(imageView.getContext());
     loader.setType(ZImageLoader.IMG_TYPE.ROUND_CORNER);
     loader.setRoundingRadius(20);
     loader.displayImg(imageView, path);
     
4.加载图片需要监听
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
# Utils

# Fast Click
快速点击处理
1.用ViewClickUtils处理快速点击，间隔默认500ms，支持自定义时间，例：
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
    
2.也可以用FastClickAgent来处理快速点击，实际上他就是一个OnClickListener的扩展类，例：
  private FastClickAgent mFastClickListener = new FastClickAgent(view -> {
        final long id = view.getId();
        if(id == R.id.id_item_2) {
        
        } else if(id == R.id.id_item_3) {

        } else if(id == R.id.id_btn_log_out) {
           
        }
    });
  view.setOnClickListener(mFastClickListener);
  
# Activity's Swipe Back

# Crash Handler

# Cache

# Animation

# Download


# Upload

# Event Bus

