package com.jiashu.zhihudemo.mode;

/**
 * Created by Jiashu on 2015/5/20.
 */
public class ZHAuthor {
    private String name;
    private String bio;
    private String profileUrl;
    private String description;
    private ZHAvatar avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZHAvatar getAvatar() {
        return avatar;
    }

    public void setAvatar(ZHAvatar avatar) {
        this.avatar = avatar;
    }
}
