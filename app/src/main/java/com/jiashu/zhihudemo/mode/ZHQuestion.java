package com.jiashu.zhihudemo.mode;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiashu on 2015/5/19.
 */
public class ZHQuestion implements Parcelable{
    private String mUrl;
    private String mQuestion;
    private String mDescription;
    private String[] mTopics;
    private int mFollows;
    private int mComments;
    private List<ZHAnswer> mAnswers;

    public ZHQuestion() {
        mAnswers = new ArrayList<>();
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String[] getTopics() {
        return mTopics;
    }

    public void setTopics(String[] topics) {
        mTopics = topics;
    }

    public int getFollows() {
        return mFollows;
    }

    public void setFollows(int follows) {
        mFollows = follows;
    }

    public int getComments() {
        return mComments;
    }

    public void setComments(int comments) {
        mComments = comments;
    }

    public List<ZHAnswer> getAnswers() {
        return mAnswers;
    }

    public void setAnswers(List<ZHAnswer> answers) {
        mAnswers = answers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeString(mQuestion);
        dest.writeString(mDescription);
        dest.writeInt(mFollows);
        dest.writeInt(mComments);
        dest.writeStringArray(mTopics);
        dest.writeList(mAnswers);
    }

    public static final Parcelable.Creator<ZHQuestion> CREATOR = new Creator<ZHQuestion>() {
        @Override
        public ZHQuestion createFromParcel(Parcel source) {
            ZHQuestion zhQuestion = new ZHQuestion();
            zhQuestion.mUrl = source.readString();
            zhQuestion.mQuestion = source.readString();
            zhQuestion.mDescription = source.readString();
            zhQuestion.mFollows = source.readInt();
            zhQuestion.mComments = source.readInt();
            source.readStringArray(zhQuestion.mTopics);
            zhQuestion.mAnswers = source.readArrayList(null);

            return zhQuestion;
        }

        @Override
        public ZHQuestion[] newArray(int size) {
            return new ZHQuestion[size];
        }
    };
}
