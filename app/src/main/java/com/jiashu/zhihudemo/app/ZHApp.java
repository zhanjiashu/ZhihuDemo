package com.jiashu.zhihudemo.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jiashu on 2015/4/23.
 */
public class ZHApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
