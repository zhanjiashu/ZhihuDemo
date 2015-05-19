package com.jiashu.zhihudemo.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jiashu on 2015/5/19.
 */
public class ZHAnswer extends ZHContent implements Parcelable {
    private String mQuestion;
    private String mAnswer;
    private String mUrl;
    private String mAnswerSummary;
    private int mVoteupCount;
    private int mCommentCount;
    private boolean isVoteUp;
    private boolean isVoteDown;
    private boolean isNoHelped;
    private boolean isThanked;
    private ZHMember mAuthor;

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getAnswerSummary() {
        return mAnswerSummary;
    }

    public void setAnswerSummary(String answerSummary) {
        mAnswerSummary = answerSummary;
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
    public String toString() {
        return "ZHAnswer{" +
                "mQuestion='" + mQuestion + '\'' +
                ", mAnswer='" + mAnswer + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mAnswerSummary='" + mAnswerSummary + '\'' +
                ", mVoteupCount=" + mVoteupCount +
                ", mCommentCount=" + mCommentCount +
                ", isVoteUp=" + isVoteUp +
                ", isVoteDown=" + isVoteDown +
                ", isNoHelped=" + isNoHelped +
                ", isThanked=" + isThanked +
                ", mAuthor=" + mAuthor +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQuestion);
        dest.writeString(mAnswer);
        dest.writeString(mUrl);
        dest.writeString(mAnswerSummary);
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
            ZHAnswer zhAnswer = new ZHAnswer();
            zhAnswer.mQuestion = source.readString();
            zhAnswer.mAnswer = source.readString();
            zhAnswer.mUrl = source.readString();
            zhAnswer.mAnswerSummary = source.readString();
            zhAnswer.mVoteupCount = source.readInt();
            zhAnswer.mCommentCount = source.readInt();
            zhAnswer.isVoteUp = source.readByte() != 0;
            zhAnswer.isVoteDown = source.readByte() != 0;
            zhAnswer.isNoHelped = source.readByte() != 0;
            zhAnswer.isThanked = source.readByte() != 0;
            zhAnswer.mAuthor = source.readParcelable(null);
            return zhAnswer;
        }

        @Override
        public ZHAnswer[] newArray(int size) {
            return new ZHAnswer[size];
        }
    };
}
