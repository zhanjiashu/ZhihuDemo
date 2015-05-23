package com.jiashu.zhihudemo.events.http;

import com.jiashu.zhihudemo.mode.ZHQuestion;

/**
 * Created by Jiashu on 2015/5/22.
 */
public class FetchQuestionHRE extends HttpResponseEvent<ZHQuestion> {
    public FetchQuestionHRE(ZHQuestion question) {
        super.data = question;
    }
}
