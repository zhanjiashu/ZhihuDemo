package com.jiashu.zhihudemo.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jiashu.zhihudemo.ZhiHuApp;
import com.jiashu.zhihudemo.cmd.NetCmd;
import com.jiashu.zhihudemo.net.ZhiHuCookieManager;

/**
 * Created by Jiashu on 2015/5/3.
 */
public class NetUtil {

    private static final String XSRF_KEY = "_xsrf";

    private static SharedPreferences mPref;

    public static String getXSRF() {

        if (ZhiHuCookieManager.hasCookie(XSRF_KEY)) {
            return ZhiHuCookieManager.getCookieValue(XSRF_KEY);
        }
        mPref = PreferenceManager
                .getDefaultSharedPreferences(ZhiHuApp.getContext());
        return mPref.getString(XSRF_KEY, null);
    }

    public static void setXSRF(String xsrf) {
        mPref.edit()
                .putString(XSRF_KEY, xsrf)
                .commit();
    }

    public static void execNetCmd(NetCmd netCmd) {
        netCmd.execute();
    }

    public static void cancleNetCmd(NetCmd netCmd) {
        netCmd.cancel();
    }
}
