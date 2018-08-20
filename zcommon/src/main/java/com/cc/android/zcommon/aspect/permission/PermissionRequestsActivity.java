package com.cc.android.zcommon.aspect.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;


import com.cc.android.zcommon.R;

import java.util.ArrayList;
import java.util.List;

public class PermissionRequestsActivity extends Activity {

    private static OnPermissionCallback sOnPermissionCallback;
    private String[] mPermissions;
    private static final String PERMISSION_KEY = "permission_key";
    private static final String REQUEST_CODE = "request_code";
    private int requestCode;

    /**
     * Apply for permissions.
     *
     * @param context     Context
     * @param permissions Permission List
     * @param callback Interface
     */
    public static void PermissionRequest(Context context, String[] permissions, int requestCode, OnPermissionCallback callback) {
        sOnPermissionCallback = callback;
        Intent intent = new Intent(context, PermissionRequestsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PERMISSION_KEY, permissions);
        bundle.putInt(REQUEST_CODE, requestCode);
        intent.putExtras(bundle);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(0, 0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permission);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPermissions = bundle.getStringArray(PERMISSION_KEY);
            requestCode = bundle.getInt(REQUEST_CODE, 0);
        }
        if (mPermissions == null || mPermissions.length <= 0) {
            finish();
            return;
        }
        requestPermission(mPermissions);
    }


    /**
     * Apply for permissions.
     *
     * @param permissions permission list
     */
    private void requestPermission(String[] permissions) {

        if (PermissionUtils.hasSelfPermissions(this, permissions)) {
            //all mPermissions granted
            if (sOnPermissionCallback != null) {
                sOnPermissionCallback.onPermissionGranted();
                sOnPermissionCallback =null;
            }
            finish();
            overridePendingTransition(0, 0);
        } else {
            //request mPermissions
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (PermissionUtils.verifyPermissions(grantResults)) {
            //All the permissions are granted.
            if (sOnPermissionCallback != null) {
                sOnPermissionCallback.onPermissionGranted();
            }
        } else {
            if (!PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
                if (permissions.length != grantResults.length) return;
                List<String> denyList = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == -1) {
                        denyList.add(permissions[i]);
                    }
                }
                if (sOnPermissionCallback != null) {
                    sOnPermissionCallback.onPermissionDenied(requestCode, denyList);
                }

            } else {
                if (sOnPermissionCallback != null) {
                    sOnPermissionCallback.onPermissionCanceled(requestCode);
                }
            }

        }
        sOnPermissionCallback =null;
        finish();
        overridePendingTransition(0, 0);
    }

}
