package com.jiashu.zhihudemo.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jiashu on 2015/5/19.
 */
public class ZHAnswer implements Parcelable {
    private String mQuestionTitle;
    private String mQUestionUrl;
    private String mContent;
    private String mUrl;
    private String mSummary;
    private int mVoteupCount;
    private int mCommentCount;
    private boolean isVoteUp;
    private boolean isVoteDown;
    private boolean isNoHelped;
    private boolean isThanked;
    private ZHMember mAuthor;

    public String getQuestionTitle() {
        return mQuestionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        mQuestionTitle = questionTitle;
    }

    public String getQUestionUrl() {
        return mQUestionUrl;
    }

    public void setQUestionUrl(String QUestionUrl) {
        mQUestionUrl = QUestionUrl;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
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

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(int commentCount) {
        mCommentCount = commentCount;
    }

    public boolean isVoteUp() {
        return isVoteUp;
    }

    public void setIsVoteUp(boolean isVoteUp) {
        this.isVoteUp = isVoteUp;
    }

    public boolean isVoteDown() {
        return isVoteDown;
    }

    public void setIsVoteDown(boolean isVoteDown) {
        this.isVoteDown = isVoteDown;
    }

    public boolean isNoHelped() {
        return isNoHelped;
    }

    public void setIsNoHelped(boolean isNoHelped) {
        this.isNoHelped = isNoHelped;
    }

    public boolean isThanked() {
        return isThanked;
    }

    public void setIsThanked(boolean isThanked) {
        this.isThanked = isThanked;
    }

    public ZHMember getAuthor() {
        return mAuthor;
    }

    public void setAuthor(ZHMember author) {
        mAuthor = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQuestionTitle);
        dest.writeString(mQUestionUrl);
        dest.writeString(mContent);
        dest.writeString(mUrl);
        dest.writeString(mSummary);
        dest.writeInt(mVoteupCount);
        dest.writeInt(mCommentCount);
        dest.writeByte((byte) (isVoteUp ? 1 : 0));
        dest.writeByte((byte) (isVoteDown ? 1: 0));
        dest.writeByte((byte) (isNoHelped ? 1 : 0));
        dest.writeByte((byte) (isThanked ? 1 : 0));
        dest.writeParcelable(mAuthor, 0);
    }

    public static final Parcelable.Creator<ZHAnswer> CREATOR = new Parcelable.Creator<ZHAnswer>() {

        @Override
        public ZHAnswer createFromParcel(Parcel source) {
            ZHAnswer answer = new ZHAnswer();
            answer.mQuestionTitle = source.readString();
            answer.mQUestionUrl = source.readString();
            answer.mContent = source.readString();
            answer.mUrl = source.readString();
            answer.mSummary = source.readString();
            answer.mVoteupCount = source.readInt();
            answer.mCommentCount = source.readInt();
            answer.isVoteUp = source.readByte() != 0;
            answer.isVoteDown = source.readByte() != 0;
            answer.isNoHelped = source.readByte() != 0;
            answer.isThanked = source.readByte() != 0;
            answer.mAuthor = source.readParcelable(ZHAnswer.class.getClassLoader());
            return answer;
        }

        @Override
        public ZHAnswer[] newArray(int size) {
            return new ZHAnswer[size];
        }
    };
}
