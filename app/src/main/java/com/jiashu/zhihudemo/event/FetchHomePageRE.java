package com.jiashu.zhihudemo.event;

/**
 * Created by Jiashu on 2015/5/5.
 * 获取【首页】内容成功 的事件
 */
public class FetchHomePageRE {
    public String response;

    public FetchHomePageRE(String response) {
        this.response = response;
    }
}
