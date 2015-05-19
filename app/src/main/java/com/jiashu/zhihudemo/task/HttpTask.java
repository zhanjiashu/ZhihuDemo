package com.jiashu.zhihudemo.task;

import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.utils.HttpUtils;
import com.jiashu.zhihudemo.utils.VolleyUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/5/3.
 * 封装 客户端 与 网络 的数据交互：如登录【知乎】、获取首页内容等
 * execute() : 执行任务
 * cancel() ：取消任务
 */
public abstract class HttpTask {

    protected VolleyUtils mVolleyUtils;
    protected static String mXSRF;

    protected EventBus mBus;

    public HttpTask() {
        mVolleyUtils = VolleyUtils.getInstance(ZHApp.getContext());
        mXSRF = HttpUtils.getXSRF();

        mBus = EventBus.getDefault();
    }

    public abstract void execute();
    public abstract void cancel();
}
