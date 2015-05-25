package com.jiashu.zhihudemo.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jiashu on 2015/5/19.
 */
public class ZHArticle implements Parcelable {

    private int commentsCount;
    private int likesCount;
    private String url;
    private String content;
    private String publishedTime;
    private String title;
    private String titleImage;
    private String rating;
    private ZHAuthor author;
    private ZHColumn column;

    public ZHArticle(String url) {
        this.url = url;
    }

    public ZHArticle() {}

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ZHAuthor getAuthor() {
        return author;
    }

    public void setAuthor(ZHAuthor author) {
        this.author = author;
    }

    public ZHColumn getColumn() {
        return column;
    }

    public void setColumn(ZHColumn column) {
        this.column = column;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(likesCount);
        dest.writeInt(commentsCount);
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(titleImage);
        dest.writeString(content);
        dest.writeString(publishedTime);
    }

    public static final Parcelable.Creator<ZHArticle> CREATOR = new Creator<ZHArticle>() {
        @Override
        public ZHArticle createFromParcel(Parcel source) {
            ZHArticle article = new ZHArticle();

            article.likesCount = source.readInt();
            article.commentsCount = source.readInt();
            article.url = source.readString();
            article.title = source.readString();
            article.titleImage = source.readString();
            article.content = source.readString();
            article.publishedTime = source.readString();

            return article;
        }

        @Override
        public ZHArticle[] newArray(int size) {
            return new ZHArticle[size];
        }
    };
}
