package com.jiashu.zhihudemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jiashu.zhihudemo.ZhiHuApp;
import com.jiashu.zhihudemo.cmd.NetCmd;
import com.jiashu.zhihudemo.mode.ZhiHuFeed;
import com.jiashu.zhihudemo.net.ZhiHuCookieManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiashu on 2015/5/3.
 */
public class NetUtil {

    private static final String TAG = "NetUtil";

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

    /**
     * 获取 [首页] 的 Feed 信息流，并进行封装
     * @param html
     * @return
     */
    public static List<ZhiHuFeed> getFeedList(String html) {
        List<ZhiHuFeed> feedList = new ArrayList<>();
        saveToFile("response.html", html);
        Document doc = Jsoup.parse(html);
        // 遍历返回数据中 包含着 Feed 的 div
        Elements elements = doc.select("div[id=js-home-feed-list]>div[id^=feed]");
        for (Element element : elements) {
            ZhiHuFeed.Builder builder = new ZhiHuFeed.Builder(element);
            ZhiHuFeed feed = builder.build();
            feedList.add(feed);
        }
        LogUtil.d(TAG, "feedList's size " + feedList.size());
        return feedList;
    }

    private static void saveToFile(final String fileName, final String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream out = null;
                BufferedWriter writer = null;

                try {
                    out = ZhiHuApp.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
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
}
