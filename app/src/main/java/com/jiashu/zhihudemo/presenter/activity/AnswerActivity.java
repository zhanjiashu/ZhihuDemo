package com.jiashu.zhihudemo.presenter.activity;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.mode.ZhiHuFeed;
import com.jiashu.zhihudemo.net.ZHStringRequest;
import com.jiashu.zhihudemo.other.ZHAnswerContentView;
import com.jiashu.zhihudemo.utils.LogUtils;

import com.jiashu.zhihudemo.vu.AnswerVu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Jiashu on 2015/5/3.
 * 【回答】 的 内容详情页面
 */
public class AnswerActivity extends BasePresenterActivity<AnswerVu> {

    private static final String TAG = "AnswerActivity";

    private static final int OFFSET = 120;

    private static final int PX_MARGIN_TOP = 20;

    private ImageLoader mImageLoader;

    private boolean isTopHide;

    private String mAnswer;

    @Override
    protected Class<AnswerVu> getVuClass() {
        return AnswerVu.class;
    }

    @Override
    protected void onBindVu() {

        mBus.register(this);

        mImageLoader = mVolleyUtils.getImageLoader();

        setSupportActionBar(mVu.getToolbar());

        Intent intent = getIntent();
        String question = intent.getStringExtra("question");
        String authorName = intent.getStringExtra("name");
        String authorProfile = intent.getStringExtra("profile");
        mAnswer = intent.getStringExtra("content");
        String authorUrl = intent.getStringExtra("authorUrl");
        int voteups = intent.getIntExtra("voteups", 0);

        getSupportActionBar().setTitle(question);

        mVu.setAuthorName(authorName);
        mVu.setAuthorProfile(authorProfile);
        mVu.setQuestion(question);
        mVu.setVoteups(voteups + "");

        // 动态调整 答案内容 的 paddingTop，以期 答案内容 与顶部区域的距离固定
        mVu.initWebContent(new Runnable() {
            @Override
            public void run() {
                int topHeight = mVu.getTopHeight();
                topHeight = topHeight / 3 + PX_MARGIN_TOP;
                String preDiv = "<div style=\"padding-left:4%;padding-right:4%;padding-top:" + topHeight + "px\">";

                mVu.setContent(HttpConstants.ANSWER_HTML_PRE + preDiv + mAnswer + HttpConstants.ANSWER_HTML_SUF);
            }
        });
        mVu.setContent("");

        fetchAuthorImg(authorUrl);

        mVu.setWebViewScrollListener(new ZHAnswerContentView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

                if (t > dp2px(OFFSET) && !isTopHide) {
                    mVu.hideTop();
                    LogUtils.d(TAG, "hide");
                    isTopHide = true;
                }

                if (t <= dp2px(OFFSET) && isTopHide) {
                    mVu.showTop();
                    LogUtils.d(TAG, "show");
                    isTopHide = false;
                }

            }
        });
    }

    private void fetchAuthorImg(String contentUrl) {
        ZHStringRequest request = new ZHStringRequest(
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
                        LogUtils.d(TAG, volleyError.toString());
                    }
                }
        );
        mVolleyUtils.addRequest(request);
    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
        mVolleyUtils.cancelAll();
    }

    public static int dp2px(int dp) {
        float scale = ZHApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static void startBy(Context context, ZhiHuFeed feed) {
        LogUtils.d(TAG, feed.getContentUrl());
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra("question", feed.getTitle());
        intent.putExtra("name", feed.getAuthorName());
        intent.putExtra("profile", feed.getAuthorProfile());
        intent.putExtra("voteups", feed.getVoteups());
        intent.putExtra("content", feed.getContent());
        intent.putExtra("authorUrl", feed.getAuthorUrl());
        context.startActivity(intent);
    }

    public void onEventMainThread(String response) {
        Document doc = Jsoup.parse(response);

        String avatarUrl = doc.select("div[class=zm-profile-header-avatar-container]>img").attr("src");

        // 如何获取到的头像地址为空，则显示知乎默认的头像
        if (TextUtils.isEmpty(avatarUrl)) {
            avatarUrl = "http://pic1.zhimg.com/da8e974dc_l.jpg";
        }

        mVu.setAvatar(avatarUrl, mImageLoader);
        LogUtils.d(TAG, "avatarUrl = " + avatarUrl);
    }

}
