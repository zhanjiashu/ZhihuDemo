package com.jiashu.zhihudemo.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.mode.ZhiHuFeed;
import com.jiashu.zhihudemo.net.ZhiHuStringRequest;
import com.jiashu.zhihudemo.other.ZHWebView;
import com.jiashu.zhihudemo.utils.HttpUtil;
import com.jiashu.zhihudemo.utils.LogUtil;
import com.jiashu.zhihudemo.utils.ToastUtils;
import com.jiashu.zhihudemo.vu.AnswerVu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;


public class AnswerActivity extends BasePresenterActivity<AnswerVu> {

    private static final String TAG = "AnswerActivity";

    private ImageLoader mImageLoader;

    private boolean isTopHide;

    @Override
    protected Class<AnswerVu> getVuClass() {
        return AnswerVu.class;
    }

    @Override
    protected void onBindVu() {

        mBus.register(this);

        mImageLoader = mVolleyUtil.getImageLoader();

        setSupportActionBar(mVu.getToolbar());

        Intent intent = getIntent();
        String question = intent.getStringExtra("question");
        String authorName = intent.getStringExtra("name");
        String authorProfile = intent.getStringExtra("profile");
        String content = intent.getStringExtra("content");
        String contentUrl = intent.getStringExtra("contentUrl");
        int voteups = intent.getIntExtra("voteups", 0);

        getSupportActionBar().setTitle(question);

        mVu.setAuthorName(authorName);
        mVu.setAuthorProfile(authorProfile);
        mVu.setQuestion(question);
        mVu.setVoteups(voteups + "");

        mVu.setContent(HttpConstants.ANSWER_HTML_PRE + content + HttpConstants.ANSWER_HTML_SUF);

        fetchAnswer(contentUrl);

        mVu.setWebViewScrollListener(new ZHWebView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

                if (t > 220 && !isTopHide) {
                    mVu.hideTop();
                    LogUtil.d(TAG, "hide");
                    isTopHide = true;
                }

                if (t <= 220 && isTopHide) {
                    mVu.showTop();
                    LogUtil.d(TAG, "show");
                    isTopHide = false;
                }

            }
        });
    }

    private void fetchAnswer(String contentUrl) {
        StringRequest request = new StringRequest(
                contentUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        mBus.post(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtil.d(TAG, volleyError.toString());
                    }
                }
        );
        mVolleyUtil.addRequest(request);
    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
        mVolleyUtil.cancelAll();
    }

    private int dp2px(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static void startBy(Context context, ZhiHuFeed feed) {
        LogUtil.d(TAG, feed.getContentUrl());
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra("question", feed.getTitle());
        intent.putExtra("name", feed.getAuthorName());
        intent.putExtra("profile", feed.getAuthorProfile());
        intent.putExtra("voteups", feed.getVoteups());
        intent.putExtra("content", feed.getContent());
        intent.putExtra("contentUrl", feed.getContentUrl());
        context.startActivity(intent);
    }

    public void onEventMainThread(String response) {
        Document doc = Jsoup.parse(response);
        String avatarUrl = doc.select("img[class=zm-list-avatar]").attr("src");
        if (TextUtils.isEmpty(avatarUrl)) {
            avatarUrl = "http://pic1.zhimg.com/da8e974dc_l.jpg";
        }
        mVu.setAvatar(avatarUrl, mImageLoader);
        LogUtil.d(TAG, "avatarUrl = " + avatarUrl);
    }

}
