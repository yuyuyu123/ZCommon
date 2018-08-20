package com.zcommon.simple;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cc.android.zcommon.aspect.FastClick;
import com.cc.android.zcommon.aspect.permission.CPermission;
import com.cc.android.zcommon.aspect.permission.DPermission;
import com.cc.android.zcommon.aspect.permission.GPermission;
import com.cc.android.zcommon.aspect.MethodTrace;
import com.cc.android.zcommon.aspect.TestAnnoTrace;
import com.cc.android.zcommon.aspect.permission.PermissionCanceled;
import com.cc.android.zcommon.aspect.permission.PermissionDenied;
import com.cc.android.zcommon.base.RxBasePermissionActivity;
import com.cc.android.zcommon.img.CameraAndPictureActivity;
import com.cc.android.zcommon.utils.android.T;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends RxBasePermissionActivity
        implements  RxBasePermissionActivity.OnSinglePermissionRequestCallBack{

    private static final String TAG = "MainActivity";
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

    @TestAnnoTrace(value = "test", type = 1)
    public void testAop(View view) {
        Log.e(TAG, "Hello, I am MainActivity");
    }

    @FastClick
    public void fastClick(View view) {
        Log.e(TAG, "fastClick-----------");
    }

    @MethodTrace
    public void methodTrace(View view) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void singlePermission(View view) {
       location();
    }

    @GPermission(value = {Manifest.permission.ACCESS_FINE_LOCATION})
    private void location() {
        T.showShort(this, "定位权限通过");
    }

    public void multiPermissions(View view) {
        takePhoto();
    }

    @GPermission(value = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode = 10)
    private void takePhoto() {
        T.showShort(this, "拍照和文件读写权限通过");
    }
    /**
     * 权限被取消
     *
     * @param bean CancelBean
     */
    @CPermission(requestCode = 10)
    public void dealCancelPermission(PermissionCanceled bean) {
        Toast.makeText(this, "requestCode:" + bean.getRequestCode(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 权限被拒绝
     *
     * @param bean DenyBean
     */
    @DPermission
    public void dealPermission(PermissionDenied bean) {
        if (bean == null) return;
        Toast.makeText(this, "requestCode:" + bean.getRequestCode()
                + ",Permissions: " + Arrays.toString(bean.getDenyList().toArray()), Toast.LENGTH_SHORT).show();
        List<String> denyList = bean.getDenyList();
        switch (bean.getRequestCode()) {
            case 10:
                //多个权限申请返回结果
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < denyList.size(); i++) {
                    if (Manifest.permission.CAMERA.equals(denyList.get(i))) {
                        builder.append("相机");
                    } else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(denyList.get(i))) {
                        builder.append("文件权限");
                    }
                }
                builder.append("权限被禁止，需要手动打开");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setMessage(builder)
                        .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
//                                SettingUtil.go2Setting(MainActivity.this);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();

                break;
            case 0:
                //单个权限申请返回结果
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setMessage("定位权限被禁止，需要手动打开")
                        .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
//                                SettingUtil.go2Setting(MainActivity.this);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
                break;
            default:
                break;
        }
    }
}
