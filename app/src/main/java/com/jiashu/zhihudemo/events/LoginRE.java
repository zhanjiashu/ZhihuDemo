package com.jiashu.zhihudemo.events;

/**
 * Created by Jiashu on 2015/5/6.
 * 模拟登录成功的事件
 */
public class LoginRE {

    private int mStateCode;

    public LoginRE(int stateCode) {
        this.mStateCode = stateCode;
    }

    public int getStateCode() {
        return mStateCode;
    }

    public void setStateCode(int stateCode) {
        mStateCode = stateCode;
    }
}
