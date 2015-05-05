package com.jiashu.zhihudemo.utils;

import android.content.Context;

import com.jiashu.zhihudemo.ZhiHuApp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/** 解析 html 的工具类
 * Created by Jiashu on 2015/5/3.
 */
public class HtmlUtils {

    private static final String TAG = "HtmlUtils";

    public static void parseFeedList(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("div[id=js-home-feed-list]>div[id^=feed]");
        for (Element element : elements) {
            LogUtil.d(TAG, "feed = " + element.toString());
            //saveToFile("response.html", html);
        }
    }


}
