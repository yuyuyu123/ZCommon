package com.cc.android.zcommon.aspect.permission;

import java.util.List;


public interface OnPermissionCallback {

    //同意权限
    void onPermissionGranted();

    //拒绝权限并且选中不再提示
    void onPermissionDenied(int requestCode, List<String> denyList);

    //取消权限
    void onPermissionCanceled(int requestCode);
}
