package com.jiashu.zhihudemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.jiashu.zhihudemo.ZhiHuApp;
import com.jiashu.zhihudemo.cmd.NetCmd;
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
 */
public class HttpUtil {

    private static final String TAG = "HttpUtil";

    private static final String XSRF_KEY = "_xsrf";

    private static ConnectivityManager mCM;

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
        Elements elements = doc.select("div[id^=feed]");
        for (Element element : elements) {
            ZhiHuFeed.Builder builder = new ZhiHuFeed.Builder(element);
            ZhiHuFeed feed = builder.build();
            feedList.add(feed);
            LogUtil.d(TAG, "feed content url : " + feed.getContentUrl());
            //LogUtil.d(TAG, "feed'id: " + feed.getFeedID());
        }
        return feedList;
    }

    /**
     * 注意：有个Bug待解决
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

    public static String readFromFile(String fileName) {
        FileInputStream is = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = ZhiHuApp.getContext().openFileInput(fileName);
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

    public static boolean hasNetwork() {
        mCM = (ConnectivityManager) ZhiHuApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mCM.getActiveNetworkInfo() == null) {
            return false;
        }
        return true;
    }
}
