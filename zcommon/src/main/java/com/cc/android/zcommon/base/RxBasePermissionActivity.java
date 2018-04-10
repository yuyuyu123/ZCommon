package com.cc.android.zcommon.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * A base activity for requesting dangerous permissions.
 *
 * @Author LiuLiWei
 */
public abstract class RxBasePermissionActivity<V extends RxBaseView, P extends RxBasePresenter<V>>
        extends RxBaseActivity<V,P> implements ActivityCompat.OnRequestPermissionsResultCallback {
    /**
     * An default permission request code for {@link ActivityCompat#requestPermissions(Activity, String[], int)}.
     */
    private static final int PERMISSIONS_REQUEST_CODE = 0x0025;

    /**
     * An array contained the operation results of some permissions' requests.
     */
    private boolean[] mGrantResults;

    /**
     * An instance of {@link OnSinglePermissionRequestCallBack}.
     */
    private OnSinglePermissionRequestCallBack mSinglePermissionRequestCallBack;

    /**
     * An instance of {@link OnMultiPermissionRequestCallBack}.
     */
    private OnMultiPermissionRequestCallBack mMultiPermissionRequestCallBack;

    /**
     * Set single permission request callback.
     * @param singlePermissionRequestCallBack an instance of {@link OnSinglePermissionRequestCallBack}.
     */
    public void setSinglePermissionRequestCallBack(OnSinglePermissionRequestCallBack singlePermissionRequestCallBack) {
        this.mSinglePermissionRequestCallBack = singlePermissionRequestCallBack;
    }

    /**
     * Set multi-permission requests callback.
     * @param multiPermissionRequestCallBack an instance of {@link OnMultiPermissionRequestCallBack}.
     */
    public void setMultiPermissionRequestCallBack(OnMultiPermissionRequestCallBack multiPermissionRequestCallBack) {
        this.mMultiPermissionRequestCallBack = multiPermissionRequestCallBack;
    }

    /**
     * Check the permission if granted.
     * @param permission  a permission of {@link android.Manifest.permission}.
     * @return true:granted
     *          false:denied
     */
    protected boolean checkPermission(@NonNull String permission) {
        return ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * If should show some information(always a dialog) to tell user that
     * accept this permission's request is necessary.
     * @param permission a permission of {@link android.Manifest.permission}
     * @return true:show
     *          false:ignore
     */
    protected boolean shouldShowPermissionRationale(@NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
    }

    /**
     * Send a permission request.
     * @param permission a permission of {@link android.Manifest.permission}
     */
    protected void sendPermissionRequest(@NonNull String permission) {
        sendMultiPermissionRequests(new String[] {permission});
    }

    /**
     * Send requests for multiple permissions.
     * @param permissionArr an array of {@link android.Manifest.permission}
     */
    protected void sendMultiPermissionRequests(@NonNull String permissionArr[]) {
        ActivityCompat.requestPermissions(this, permissionArr, PERMISSIONS_REQUEST_CODE);
    }

    /**
     * Show a dialog contained some information for the rejected permissions by user.
     * @param title {@link AlertDialog}'s title
     * @param content {@link AlertDialog}'s content
     * @param positiveTxt {@link AlertDialog}'s positive text
     * @param onPositiveClickListener {@link AlertDialog}'s positive button's {@link DialogInterface.OnClickListener}
     */
    protected void showPermissionDialog(String title, String content, String positiveTxt, DialogInterface.OnClickListener onPositiveClickListener) {
        showPermissionDialog(title, content, positiveTxt,onPositiveClickListener,null,null);
    }

    /**
     * Show a dialog contained some information for the rejected permissions by user.
     * @param title {@link AlertDialog}'s title
     * @param content {@link AlertDialog}'s content
     * @param positiveTxt {@link AlertDialog}'s positive text
     * @param onPositiveClickListener {@link AlertDialog}'s positive button's {@link DialogInterface.OnClickListener}
     * @param negativeTxt {@link AlertDialog}'s negative text
     * @param onNegativeClickListener {@link AlertDialog}'s negative button's {@link DialogInterface.OnClickListener}
     */
    protected void showPermissionDialog(String title, String content,
                                        String positiveTxt, DialogInterface.OnClickListener onPositiveClickListener,
                                        String negativeTxt, DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(TextUtils.isEmpty(title) ? "提示" : title);
        builder.setMessage(TextUtils.isEmpty(content) ? "请输入对话框内容" : content);
        if(TextUtils.isEmpty(positiveTxt)) {
            if(onPositiveClickListener != null) {
                builder.setPositiveButton("确定", onPositiveClickListener);
            }
        } else {
            builder.setPositiveButton(positiveTxt, onPositiveClickListener);
        }
        if(TextUtils.isEmpty(negativeTxt)) {
            if(onNegativeClickListener != null) {
                builder.setNegativeButton("取消", onNegativeClickListener);
            }
        } else {
            builder.setNegativeButton(negativeTxt, onNegativeClickListener);
        }
        builder.create().show();

    }
    /**
     * Operation set:
     * 1.Check permission;
     * 2.Show a dialog for the permission's request's reasonable;
     * 3.Send permission request.
     * @param permission a dangerous permission.
     * @param tipContent permission reasonable tip.
     * @return
     *      true:already granted.
     *      false:not granted yet.
     */
    protected boolean dealWithPermission(@NonNull String permission, String tipContent) {
        if(!checkPermission(permission)) {
            if(shouldShowPermissionRationale(permission)) {
                new MaterialDialog.Builder(this)
                        .title("提示")
                        .content(tipContent)
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                sendPermissionRequest(permission);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                finish();
                            }
                        }).build().show();
            } else {
                sendPermissionRequest(permission);
            }
            return false;
        }
        return true;
    }

    /**
     * The callback of the permission's requests.
     * @param requestCode   request code
     * @param permissions request permissions' array
     * @param grantResults  request permissions' operation result
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                Log.d(TAG, "onRequestPermissionsResult is invoked");
                if(grantResults != null && grantResults.length > 0) {
                    if(grantResults.length == 1) {
                        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            if(mSinglePermissionRequestCallBack != null) {
                                mSinglePermissionRequestCallBack.onPermissionAllowed(permissions[0]);
                            }
                        } else {
                            if(mSinglePermissionRequestCallBack != null) {
                                mSinglePermissionRequestCallBack.onPermissionDenied(permissions[0]);
                            }
                        }
                    }

                    if(grantResults.length > 1) {
                        if(mMultiPermissionRequestCallBack != null) {
                            mGrantResults = new boolean[grantResults.length];
                            for (int i = 0; i < grantResults.length; i++) {
                                mGrantResults[i] = grantResults[i] == PackageManager.PERMISSION_GRANTED ? true : false;
                            }
                            mMultiPermissionRequestCallBack.onMultiPermissionCallback(permissions, mGrantResults);
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * Interface used to allow the user to receive operation results
     * when user only send a permission request.
     */
    public interface OnSinglePermissionRequestCallBack {
        /**
         * This method will be invoked when a permission request is accepted by user.
         *
         * @param permission a permission of {@link android.Manifest.permission}
         */
        void onPermissionAllowed(String permission);

        /**
         * This method will be invoked when a permission request is rejected by user.
         *
         * @param permission a permission of {@link android.Manifest.permission}
         */
        void onPermissionDenied(String permission);

    }

    /**
     * Interface used to allow the user to receive operation results
     * when user send multiple requests for permission.
     */
    public interface OnMultiPermissionRequestCallBack {

        /**
         * This method will be invoked when multiple requests for permission received callback
         * @param permissions an array of {@link android.Manifest.permission}
         * @param grantResults  an array of the operation's results
         */
        void onMultiPermissionCallback(String permissions[], boolean[] grantResults);
    }

}
