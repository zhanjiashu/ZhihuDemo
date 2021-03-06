package com.jiashu.zhihudemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.jiashu.zhihudemo.app.ZHApp;

import com.jiashu.zhihudemo.task.HttpTask;
import com.jiashu.zhihudemo.mode.ZHFeed;

import com.jiashu.zhihudemo.net.ZhiHuCookieManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiashu on 2015/5/3.
 * 网络操作 的工具类
 */
public class HttpUtils {

    private static final String TAG = "HttpUtils";

    public static final String KEY_XSRF = "_xsrf";

    public static final String KEY_NODE_NAME = "ZH_HOME_PAGE_TYPE";

    private static ConnectivityManager mCM;

    private static SharedPreferences mPref = PreferenceManager
            .getDefaultSharedPreferences(ZHApp.getContext());

    /**
     * 获取 _xsrf 参数：
     * 首先从 cookie 中获取，若在 cookie 中获取不到， 则从 SharedPreference 中获取
     * @return
     */
    public static String getXSRF() {

        if (ZhiHuCookieManager.hasCookie(KEY_XSRF)) {
            return ZhiHuCookieManager.getCookieValue(KEY_XSRF);
        }

        return mPref.getString(KEY_XSRF, null);
    }

    /**
     * 将 _xsrf 参数 保存到 SharedPreference 中
     * @param xsrf
     */
    public static void setXSRF(String xsrf) {

        if (!TextUtils.isEmpty(xsrf)) {
            mPref.edit()
                    .putString(KEY_XSRF, xsrf)
                    .apply();
        }
    }

    /**
     * 将【首页】的 nodename 保存至 SharedPreference，该 nodename 用于识别 当前首页 的版本
     * 【注】 知乎的首页有 新版 与 旧版之分，两者在上拉加载时所需的 url 以及 参数均不相同。
     * @param nodeName
     */
    public static void setNodeName(String nodeName) {
        if (!TextUtils.isEmpty(nodeName)) {
            mPref.edit()
                    .putString(KEY_NODE_NAME, nodeName)
                    .apply();
        }
    }

    /**
     * 从 SharedPreference 读取知乎首页的 nodename
     * @return
     */
    public static String getNodeName() {
        return mPref.getString(KEY_NODE_NAME, "");
    }

    /**
     * 执行一个 网络数据交互 的命令
     * @param task
     */
    public static void executeTask(HttpTask task) {
        task.execute();
    }

    /**
     * 取消一个 网络数据交互 的命令
     * @param task
     */
    public static void cancelTask(HttpTask task) {
        if (task != null) {
            task.cancel();
        }
    }


    /**
     * 保存内容到指定文件
     * @param fileName
     * @param content
     */
    public static void saveToFile(final String fileName, final String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream out = null;
                BufferedWriter writer = null;
                try {
                    out = ZHApp.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
                    writer = new BufferedWriter(new OutputStreamWriter(out));
                    writer.write(content);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 从指定文件中读取内容
     * @param fileName
     * @return
     */
    public static String readFromFile(String fileName) {
        FileInputStream is = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = ZHApp.getContext().openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 判断当前是否有网络连接
     * @return
     */
    public static boolean isNetworkAvailable() {
        mCM = (ConnectivityManager) ZHApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = mCM.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
