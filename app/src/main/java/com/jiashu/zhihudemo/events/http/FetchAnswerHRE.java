package com.jiashu.zhihudemo.events.http;

/**
 * Created by Jiashu on 2015/5/16.
 */
public class FetchAnswerHRE extends HttpResponseEvent {
    private String mAnswerContent;

    public FetchAnswerHRE(String answerContent) {
        mAnswerContent = answerContent;
    }

    public String getAnswerContent() {
        return mAnswerContent;
    }

    public void setAnswerContent(String answerContent) {
        mAnswerContent = answerContent;
    }
}
