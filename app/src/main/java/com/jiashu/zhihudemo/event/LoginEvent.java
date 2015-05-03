package com.jiashu.zhihudemo.event;

/**
 * Created by Jiashu on 2015/5/2.
 */
public class LoginEvent {
    private String mEmail;
    private String mPassword;

    public boolean isLogined() {
        return mIsLogined;
    }

    public void setIsLogined(boolean mIsLogined) {
        this.mIsLogined = mIsLogined;
    }

    private boolean mIsLogined;


    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }
}
