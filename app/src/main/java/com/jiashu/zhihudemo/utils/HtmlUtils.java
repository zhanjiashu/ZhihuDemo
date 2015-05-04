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

    public static void parseContentItem(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("div[id=js-home-feed-list]>div[id^=feed]");
        for (Element element : elements) {
            LogUtil.d(TAG, "contentItem = " + element.toString());
            saveToFile("response.html", html);
        }
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
