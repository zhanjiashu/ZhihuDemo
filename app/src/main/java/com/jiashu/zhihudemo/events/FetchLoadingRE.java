package com.jiashu.zhihudemo.events;

import android.support.annotation.Nullable;

/**
 * Created by Jiashu on 2015/5/8.
 * 获取【首页】加载数据成功的 事件
 */
public class FetchLoadingRE {
    public boolean isSuccess;
    @Nullable
    public String data;

    public FetchLoadingRE(boolean isSuccess, String data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }
}
