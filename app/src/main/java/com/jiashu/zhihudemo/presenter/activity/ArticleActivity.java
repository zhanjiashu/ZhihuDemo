package com.jiashu.zhihudemo.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;

import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.events.http.FetchArticleHRE;
import com.jiashu.zhihudemo.events.http.HttpResponseEvent;
import com.jiashu.zhihudemo.mode.ZHArticle;
import com.jiashu.zhihudemo.other.ZHScrollView;
import com.jiashu.zhihudemo.task.FetchArticleTask;
import com.jiashu.zhihudemo.task.HttpTask;
import com.jiashu.zhihudemo.utils.HttpUtils;
import com.jiashu.zhihudemo.vu.ArticleVu;


import java.text.MessageFormat;

public class ArticleActivity extends BasePresenterActivity<ArticleVu> {

    private static final String TAG = "Article";

    private static final String EXTRA_ARTICLE_URL = "articleUrl";

    private static final int HEADER_HIGHT = 240;

    private ActionBar mActionBar;
    private Drawable mActionBarDrawable;

    private String mArticleUrl;
    private String mColumnToken;
    private String mArticleId;

    private boolean mHasTilteImg;

    private HttpTask mTask;


    @Override
    protected Class<ArticleVu> getVuClass() {
        return ArticleVu.class;
    }

    @Override
    protected void onBindVu() {
        mBus.register(this);

        mActionBarDrawable = mVu.getToobar().getBackground();

        setSupportActionBar(mVu.getToobar());
        mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(mActionBarDrawable);

        mActionBar.setTitle("专栏");

        mArticleUrl = getIntent().getStringExtra(EXTRA_ARTICLE_URL);

        getApiParams(mArticleUrl);

        String apiFormat = "http://zhuanlan.zhihu.com/api/columns/{0}/posts/{1}";

        String articleAPI = MessageFormat.format(apiFormat, mColumnToken, mArticleId);

        mTask = new FetchArticleTask(ArticleActivity.this, articleAPI);
        HttpUtils.executeTask(mTask);

        mVu.setScrollLisener(new ZHScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

                int headHeight = dp2px(HEADER_HIGHT);

                // 动态设置 Toolbar 背景的透明度、以及移动 titleImg
                if (mHasTilteImg && t <= headHeight) {
                    float ratio = (float) Math.min(Math.max(t, 0), headHeight) / headHeight;
                    int newAlpha = (int) (ratio * 255);
                    mActionBarDrawable.setAlpha(newAlpha);
                    mVu.hideTitleImg(-t * Math.min(ratio, 0.5f));
                }
            }
        });

    }

    private void getApiParams(String articleUrl) {
        String[] params = articleUrl.split("/");
        mColumnToken = params[params.length - 2];
        mArticleId = params[params.length - 1];
    }

    @Override
    protected void beforePause() {
        super.beforePause();
        HttpUtils.cancelTask(mTask);
    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
    }

    public static void startBy(Context context, String articleUrl) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(EXTRA_ARTICLE_URL, articleUrl);
        context.startActivity(intent);
    }

    private void setArticle(ZHArticle article) {
        mActionBar.setTitle(article.getColumn().getName());
        mVu.setTitle(article.getTitle());

        String titleImgUrl = article.getTitleImage();
        if (!TextUtils.isEmpty(titleImgUrl)) {
            mActionBarDrawable.setAlpha(0);
            mHasTilteImg = true;
        } else {
            mVu.dimissHead();
        }
        mVu.setTitleImg(titleImgUrl, mVolleyUtils.getImageLoader());

        mVu.setContent(HttpConstants.ANSWER_HTML_PRE + article.getContent() + HttpConstants.ANSWER_HTML_SUF);
        mVu.setAuthorName(article.getAuthor().getName());

        String avatarTemplate = "http://pic1.zhimg.com/{0}_{1}.jpg";
        String avatarId= article.getAuthor().getAvatar().getId();

        String avatarImgUrl = MessageFormat.format(avatarTemplate, avatarId, "s");

        mVu.setAuthorAvatar(avatarImgUrl, mVolleyUtils.getImageLoader());
        mVu.setVoteUpBtn(false, article.getLikesCount());
        mVu.setCommentBtn(article.getCommentsCount());

    }

    public int dp2px(float dp) {
        final float scale = ZHApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void onEvent(HttpResponseEvent event) {
        if (event instanceof FetchArticleHRE) {
            ZHArticle article = (ZHArticle) event.data;
            setArticle(article);
        }
    }
}
