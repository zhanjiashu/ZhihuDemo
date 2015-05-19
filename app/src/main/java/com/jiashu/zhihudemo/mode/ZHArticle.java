package com.jiashu.zhihudemo.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jiashu on 2015/5/19.
 */
public class ZHArticle extends ZHContent implements Parcelable {

    private String mUrl;
    private String mTitle;
    private String mSummary;
    private int mVoteupCount;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getVoteupCount() {
        return mVoteupCount;
    }

    public void setVoteupCount(int voteupCount) {
        mVoteupCount = voteupCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeString(mTitle);
        dest.writeString(mSummary);
        dest.writeInt(mVoteupCount);
    }

    public static final Parcelable.Creator<ZHArticle> CREATOR = new Creator<ZHArticle>() {
        @Override
        public ZHArticle createFromParcel(Parcel source) {
            ZHArticle zhArticle = new ZHArticle();
            zhArticle.mUrl = source.readString();
            zhArticle.mTitle = source.readString();
            zhArticle.mSummary = source.readString();
            zhArticle.mVoteupCount = source.readInt();
            return zhArticle;
        }

        @Override
        public ZHArticle[] newArray(int size) {
            return new ZHArticle[size];
        }
    };
}
