package com.cc.android.zcommon.aspect.permission;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PermissionDenied implements Parcelable {

    private int requestCode;
    private List<String> denyList;

    @Override
    public String toString() {
        return "PermissionDenied{" +
                "requestCode=" + requestCode +
                ", denyList=" + denyList +
                '}';
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public List<String> getDenyList() {
        return denyList;
    }

    public void setDenyList(List<String> denyList) {
        this.denyList = denyList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.requestCode);
        dest.writeStringList(this.denyList);
    }

    public PermissionDenied() {
    }

    protected PermissionDenied(Parcel in) {
        this.requestCode = in.readInt();
        this.denyList = in.createStringArrayList();
    }

    public static final Creator<PermissionDenied> CREATOR = new Creator<PermissionDenied>() {
        @Override
        public PermissionDenied createFromParcel(Parcel source) {
            return new PermissionDenied(source);
        }

        @Override
        public PermissionDenied[] newArray(int size) {
            return new PermissionDenied[size];
        }
    };
}
