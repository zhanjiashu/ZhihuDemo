package com.jiashu.zhihudemo.task;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.events.http.FetchAnswerHRE;
import com.jiashu.zhihudemo.mode.ZHAnswer;
import com.jiashu.zhihudemo.mode.ZHMember;
import com.jiashu.zhihudemo.net.ZHStringRequest;
import com.jiashu.zhihudemo.utils.ParseUtils;
import com.jiashu.zhihudemo.utils.ToastUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jiashu on 2015/5/16.
 *
 * 获取【答案】
 */
public class FetchAnswerTask extends HttpTask {

    private final String TAG = getClass().getSimpleName();
    private ZHStringRequest mRequest;

    private String mAnswerUrl;

    public FetchAnswerTask(String url) {
        mAnswerUrl = url;
    }

    @Override
    public void execute() {
        mRequest = new ZHStringRequest(
                mAnswerUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "Fetch Answer failed : " + volleyError);
                    }
                }
        );
        mVolleyUtils.addRequest(mRequest);
    }

    /**
     * 解析获得 答案的内容，并拼接内容的时间属性
     * @param response
     */
    private void parseResponse(String response) {

        Document doc = Jsoup.parse(response);

        Elements elements = doc.select("div[class=zm-item-answer ]");

        ZHAnswer answer = ParseUtils.parseHtmlToAnswer(elements.get(0));

        mBus.post(new FetchAnswerHRE(answer));
    }


    @Override
    public void cancel() {
        mRequest.cancel();
    }
}
