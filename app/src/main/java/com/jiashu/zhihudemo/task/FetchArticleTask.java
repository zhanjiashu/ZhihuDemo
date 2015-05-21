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
        mArticleAPI = url;
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

                    }
                }
        );

        // 专栏文章的API 不同于获取其他内容的 url, 它们会返回不同 cookie 的。
        // 如果这里采用全局的消息队列来 处理这个 request, 势必会更改客户端所持有的cookie。
        // 这样会导致返回首页时，刷新以及加载 Feed流信息失败。
        // 所以，这里采用一个Volley默认的请求队列来处理这个 request。
        Volley.newRequestQueue(mContext).add(mRequest);
    }

    @Override
    public void cancel() {
        mRequest.cancel();
    }
}
