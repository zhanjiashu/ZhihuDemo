package com.jiashu.zhihudemo.task;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jiashu.zhihudemo.events.http.FetchAnswerHRE;
import com.jiashu.zhihudemo.events.http.FetchArticleHRE;
import com.jiashu.zhihudemo.mode.ZHArticle;
import com.jiashu.zhihudemo.net.ZHGsonRequest;
import com.jiashu.zhihudemo.net.ZHJsonObjectRequest;
import com.jiashu.zhihudemo.presenter.activity.ArticleActivity;
import com.jiashu.zhihudemo.utils.LogUtils;
import com.jiashu.zhihudemo.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by Jiashu on 2015/5/20.
 */
public class FetchArticleTask extends HttpTask {

    private final String TAG = getClass().getSimpleName();
    private String mArticleAPI;
    private Context mContext;
    private Request mRequest;

    public FetchArticleTask(Context context, String url) {
        mContext = context;
        mArticleAPI = buildArticleAPI(url);
    }

    @Override
    public void execute() {
        mRequest = new ZHGsonRequest<ZHArticle>(
                mArticleAPI,
                ZHArticle.class,
                new Response.Listener<ZHArticle>() {
                    @Override
                    public void onResponse(ZHArticle article) {
                        mBus.post(new FetchArticleHRE(article));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtils.d(TAG, volleyError.toString());
                    }
                }
        );

        mVolleyUtils.addRequest(mRequest);
    }

    @Override
    public void cancel() {
        mRequest.cancel();
    }

    // 根据传入的 文章 url, 构造 Article API
    private String buildArticleAPI(String articleUrl) {
        String[] params = articleUrl.split("/");
        String columnSlug = params[params.length - 2];
        String articleId = params[params.length - 1];

        String apiFormat = "http://zhuanlan.zhihu.com/api/columns/{0}/posts/{1}";

        return MessageFormat.format(apiFormat, columnSlug, articleId);
    }
}
