package com.jiashu.zhihudemo.command;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.events.http.FetchAnswerHRE;
import com.jiashu.zhihudemo.net.ZHStringRequest;
import com.jiashu.zhihudemo.utils.LogUtils;

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
public class FetchAnswerCmd extends Command {

    private final String TAG = getClass().getSimpleName();
    private ZHStringRequest mRequest;

    private String mAnswerUrl;

    public FetchAnswerCmd(String url) {
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
        Elements cAnswer = doc.select("div[class=zm-item-answer ]");
        String answerTime = cAnswer.attr("data-created").trim();
        String answerContent = cAnswer.select("div[class= zm-editable-content clearfix]").html();
        String answerTimePre = cAnswer.select("span[class=answer-date-link-wrap]>a").attr("class");

        if (TextUtils.isEmpty(answerTime)) {
            answerTime = String.valueOf(System.currentTimeMillis() / 1000);
        }

        Date date = new Date(Long.valueOf(answerTime) * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        answerTime = format.format(date);



        if (answerTimePre.equals("answer-date-link last_updated meta-item")) {
            answerTime = "编辑于 " + answerTime;
        } else {
            answerTime = "创建于 " + answerTime;
        }

        answerTime = "<br>\n" +
                "<br>\n" +
                "<span style=\"float:right\" class=\""+ answerTimePre +"\">"+ answerTime +"</span>";

        answerContent = answerContent + answerTime;

        mBus.post(new FetchAnswerHRE(answerContent));
    }


    @Override
    public void cancel() {
        mRequest.cancel();
    }
}
