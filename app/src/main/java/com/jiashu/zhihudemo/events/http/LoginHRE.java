package com.jiashu.zhihudemo.events.http;

/**
 * Created by Jiashu on 2015/5/27.
 */
public class LoginHRE extends HttpResponseEvent<Integer> {
    public LoginHRE(int stateCode) {
        super.data = stateCode;
    }
}
