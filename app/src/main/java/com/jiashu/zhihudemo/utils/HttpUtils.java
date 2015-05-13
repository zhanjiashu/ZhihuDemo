package com.jiashu.zhihudemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.command.Command;
import com.jiashu.zhihudemo.mode.ZhiHuFeed;
import com.jiashu.zhihudemo.net.ZhiHuCookieManager;

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

    private static final String XSRF_KEY = "_xsrf";

    private static ConnectivityManager mCM;

    private static SharedPreferences mPref;

    /**
     * 获取 _xsrf 参数：
     * 首先从 cookie 中获取，若在 cookie 中获取不到， 则从 SharedPreference 中获取
     * @return
     */
    public static String getXSRF() {

        if (ZhiHuCookieManager.hasCookie(XSRF_KEY)) {
            return ZhiHuCookieManager.getCookieValue(XSRF_KEY);
        }
        mPref = PreferenceManager
                .getDefaultSharedPreferences(ZHApp.getContext());
        return mPref.getString(XSRF_KEY, null);
    }

    /**
     * 将 _xsrf 参数 保存到 SharedPreference 中
     * @param xsrf
     */
    public static void setXSRF(String xsrf) {
        mPref.edit()
                .putString(XSRF_KEY, xsrf)
                .commit();
    }

    /**
     * 执行一个 网络数据交互 的命令
     * @param cmd
     */
    public static void exeCmd(Command cmd) {
        cmd.execute();
    }

    /**
     * 取消一个 网络数据交互 的命令
     * @param cmd
     */
    public static void cancleCmd(Command cmd) {
        cmd.cancel();
    }

    /**
     * 通过 Jsoup 解析响应的 html,并获取 【知乎】首页的 Feed 信息流
     * @param html
     * @return
     */
    public static List<ZhiHuFeed> getFeedList(String html) {
        List<ZhiHuFeed> feedList = new ArrayList<>();
        saveToFile("response.html", html);
        Document doc = Jsoup.parse(html);

        // 遍历返回数据中 包含着 Feed 的 div
        Elements elements = doc.select("div[id^=feed]");
        for (Element element : elements) {
            ZhiHuFeed.Builder builder = new ZhiHuFeed.Builder(element);
            ZhiHuFeed feed = builder.build();
            feedList.add(feed);
            LogUtils.d(TAG, "feed content url : " + feed.getContentUrl());
            //LogUtils.d(TAG, "feed'id: " + feed.getFeedID());
        }
        return feedList;
    }

    /**
     * 注意：有个Bug待解决
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
    public static boolean hasNetwork() {
        mCM = (ConnectivityManager) ZHApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mCM.getActiveNetworkInfo() == null) {
            return false;
        }
        return true;
    }
}
