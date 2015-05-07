package com.jiashu.zhihudemo.event;

/**
 * Created by Jiashu on 2015/5/2.
 */
public class UserBean {

    private String mEmail;
    private String mPassword;
    private String mCode;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

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
