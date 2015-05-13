package com.jiashu.zhihudemo.events;

/**
 * Created by Jiashu on 2015/5/2.
 * 通知客户端执行模拟登录命令的 事件
 * 封装用户登录所需的数据
 */
public class OnLoginEvent {

    private String mEmail;
    private String mPassword;
    private String mCaptcha;

    public String getCaptcha() {
        return mCaptcha;
    }

    public void setCaptcha(String captcha) {
        mCaptcha = captcha;
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
