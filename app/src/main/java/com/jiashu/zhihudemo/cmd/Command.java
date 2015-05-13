package com.jiashu.zhihudemo.cmd;

import com.jiashu.zhihudemo.ZhiHuApp;
import com.jiashu.zhihudemo.utils.HttpUtils;
import com.jiashu.zhihudemo.utils.VolleyUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/5/3.
 * 封装 客户端 与 网络 的数据交互：如登录【知乎】、获取首页内容等
 * execute() : 执行命令
 * cancel() ：取消命令
 */
public abstract class Command {

    protected VolleyUtils mVolleyUtils;
    protected static String mXSRF;

    protected EventBus mBus;

    public Command() {
        mVolleyUtils = VolleyUtils.getInstance(ZhiHuApp.getContext());
        mXSRF = HttpUtils.getXSRF();

        mBus = EventBus.getDefault();
    }

    public abstract void execute();
    public abstract void cancel();
}
