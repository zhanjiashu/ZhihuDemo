package com.jiashu.zhihudemo.mode;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jiashu on 2015/5/19.
 */
public class ZHQuestion implements Parcelable{
    private String mUrl;
    private String mTitle;
    private String mDescription;
    private String[] mTopics;
    private int mAnswerCount;
    private int mFollows;
    private int mComments;
    private List<ZHAnswer> mAnswers;

    public ZHQuestion(String url, String title) {
        mTopics = new String[5];
        mAnswers = new ArrayList<>();
        mUrl = url;
        mTitle = title;
    }

    public ZHQuestion() {
        this(null, null);
    }

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

    public int getAnswerCount() {
        return mAnswerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.mAnswerCount = answerCount;
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
    public String toString() {
        return "ZHQuestion{" +
                "mUrl='" + mUrl + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mTopics=" + Arrays.toString(mTopics) +
                ", mAnswerCount=" + mAnswerCount +
                ", mFollows=" + mFollows +
                ", mComments=" + mComments +
                ", mAnswers=" + mAnswers +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeInt(mAnswerCount);
        dest.writeInt(mFollows);
        dest.writeInt(mComments);
        dest.writeStringArray(mTopics);
        dest.writeList(mAnswers);
    }

    public static final Parcelable.Creator<ZHQuestion> CREATOR = new Creator<ZHQuestion>() {
        @Override
        public ZHQuestion createFromParcel(Parcel source) {
            ZHQuestion question = new ZHQuestion();
            question.mUrl = source.readString();
            question.mTitle = source.readString();
            question.mDescription = source.readString();
            question.mAnswerCount = source.readInt();
            question.mFollows = source.readInt();
            question.mComments = source.readInt();
            source.readStringArray(question.mTopics);
            question.mAnswers = source.readArrayList(ZHAnswer.class.getClassLoader());

            return question;
        }

        @Override
        public ZHQuestion[] newArray(int size) {
            return new ZHQuestion[size];
        }
    };
}
