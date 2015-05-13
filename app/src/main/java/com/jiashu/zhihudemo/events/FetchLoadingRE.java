package com.jiashu.zhihudemo.events;

/**
 * Created by Jiashu on 2015/5/8.
 * 获取【首页】加载数据成功的 事件
 */
public class FetchLoadingRE {
    public String data;

    public FetchLoadingRE(String data) {
        this.data = data;
    }
}
