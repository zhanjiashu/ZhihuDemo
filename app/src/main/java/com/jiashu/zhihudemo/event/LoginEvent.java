package com.jiashu.zhihudemo.event;

/**
 * Created by Jiashu on 2015/5/6.
 */
public class LoginEvent {

    private int mStateCode;

    public LoginEvent(int stateCode) {
        this.mStateCode = stateCode;
    }

    public int getStateCode() {
        return mStateCode;
    }

    public void setStateCode(int stateCode) {
        mStateCode = stateCode;
    }
}
