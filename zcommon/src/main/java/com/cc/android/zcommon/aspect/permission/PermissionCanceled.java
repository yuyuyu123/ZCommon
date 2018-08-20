package com.cc.android.zcommon.aspect.permission;


import android.os.Parcel;
import android.os.Parcelable;

public class PermissionCanceled implements Parcelable {
    private int requestCode;


    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.requestCode);
    }

    public PermissionCanceled() {
    }

    protected PermissionCanceled(Parcel in) {
        this.requestCode = in.readInt();
    }

    public static final Creator<PermissionCanceled> CREATOR = new Creator<PermissionCanceled>() {
        @Override
        public PermissionCanceled createFromParcel(Parcel source) {
            return new PermissionCanceled(source);
        }

        @Override
        public PermissionCanceled[] newArray(int size) {
            return new PermissionCanceled[size];
        }
    };
}
