package com.jiashu.zhihudemo.cmd;

import android.content.Context;

import com.jiashu.zhihudemo.ZhiHuApp;
import com.jiashu.zhihudemo.utils.NetUtil;
import com.jiashu.zhihudemo.utils.VolleyUtil;

/**
 * Created by Jiashu on 2015/5/3.
 */
public abstract class NetCmd {

    protected VolleyUtil mVolleyUtil;
    protected static String mXSRF;

    public NetCmd() {
        mVolleyUtil = VolleyUtil.getInstance(ZhiHuApp.getContext());
        mXSRF = NetUtil.getXSRF();
    }

    public abstract void execute();
    public abstract void cancel();

    public abstract <T extends NetCmdCallback> void setOnNetCmdCallback(T callback);

    public interface NetCmdCallback {

    }
}
