package com.zcommon.simple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cc.android.zcommon.base.RxBasePermissionActivity;
import com.cc.android.zcommon.img.CameraAndPictureActivity;


public class MainActivity extends RxBasePermissionActivity implements  RxBasePermissionActivity.OnSinglePermissionRequestCallBack{

    private ImageView img;

    @Override
    public void onPermissionAllowed(String s) {

    }

    @Override
    public void onPermissionDenied(String s) {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        img = findViewById(R.id.img);
    }

    public void takePhoto(View v) {
        startActivityForResult(CameraAndPictureActivity.newIntent(this, CameraAndPictureActivity.TYPE_TAKING_PHOTO_AND_CROP, "hello"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) {
            return;
        }
        if(requestCode == 1) {
            final String path = data.getStringExtra("path");
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            img.setImageBitmap(bitmap);
        }
    }
}
