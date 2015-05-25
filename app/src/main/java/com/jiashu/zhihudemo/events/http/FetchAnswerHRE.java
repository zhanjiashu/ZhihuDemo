package com.jiashu.zhihudemo.events.http;

import com.jiashu.zhihudemo.mode.ZHAnswer;

/**
 * Created by Jiashu on 2015/5/16.
 */
public class FetchAnswerHRE extends HttpResponseEvent {
    private ZHAnswer mAnswer;

    public FetchAnswerHRE(ZHAnswer answer) {
        mAnswer = answer;
    }

    public ZHAnswer getAnswer() {
        return mAnswer;
    }

    public void setAnswer(ZHAnswer answer) {
        mAnswer = answer;
    }
}
