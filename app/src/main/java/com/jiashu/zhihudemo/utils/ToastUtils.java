package com.jiashu.zhihudemo.utils;
import android.widget.Toast;

import com.jiashu.zhihudemo.ZhiHuApp;

/**
 * 显示 Toast 的工具类
 * Created by Jiashu on 2015/5/4.
 */
public class ToastUtils {

    public static void show(String msg, int duration) {
        Toast.makeText(ZhiHuApp.getContext(), msg, duration).show();
    }
    public static void show(String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }
}
