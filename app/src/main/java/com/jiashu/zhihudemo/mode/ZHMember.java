package com.jiashu.zhihudemo.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jiashu on 2015/5/19.
 */
public class ZHMember implements Parcelable{
    private String mUrl;
    private String mName;
    private String mAvatarUrl;
    private String mHeadline;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public void setHeadline(String headline) {
        mHeadline = headline;
    }

    @Override
    public String toString() {
        return "ZHMember{" +
                "mUrl='" + mUrl + '\'' +
                ", mName='" + mName + '\'' +
                ", mAvatarUrl='" + mAvatarUrl + '\'' +
                ", mHeadline='" + mHeadline + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeString(mName);
        dest.writeString(mAvatarUrl);
        dest.writeString(mHeadline);
    }

    public static final Parcelable.Creator<ZHMember> CREATOR = new Creator<ZHMember>() {
        @Override
        public ZHMember createFromParcel(Parcel source) {
            ZHMember zhMember = new ZHMember();
            zhMember.mUrl = source.readString();
            zhMember.mName = source.readString();
            zhMember.mAvatarUrl = source.readString();
            zhMember.mHeadline = source.readString();
            return zhMember;
        }

        @Override
        public ZHMember[] newArray(int size) {
            return new ZHMember[size];
        }
    };
}
