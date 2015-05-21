package com.jiashu.zhihudemo.events.http;

import com.jiashu.zhihudemo.mode.ZHArticle;


/**
 * Created by Jiashu on 2015/5/20.
 */
public class FetchArticleHRE extends HttpResponseEvent<ZHArticle>{

    public FetchArticleHRE(ZHArticle article) {
        super.data = article;
    }
}
