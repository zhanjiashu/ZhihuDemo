package com.jiashu.zhihudemo.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;

import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.events.http.FetchArticleHRE;
import com.jiashu.zhihudemo.events.http.HttpResponseEvent;
import com.jiashu.zhihudemo.mode.ZHArticle;
import com.jiashu.zhihudemo.task.FetchArticleTask;
import com.jiashu.zhihudemo.task.HttpTask;
import com.jiashu.zhihudemo.utils.HttpUtils;
import com.jiashu.zhihudemo.utils.LogUtils;
import com.jiashu.zhihudemo.vu.ArticleVu;


import java.text.MessageFormat;

public class ArticleActivity extends BasePresenterActivity<ArticleVu> {

    private static final String TAG = "Article";

    private static final String EXTRA_ARTICLE_URL = "articleUrl";

    private ActionBar mActionBar;

    private String mArticleUrl;
    private String mColumnToken;
    private String mArticleId;

    private HttpTask mTask;

    @Override
    protected Class<ArticleVu> getVuClass() {
        return ArticleVu.class;
    }

    @Override
    protected void onBindVu() {
        mBus.register(this);

        setSupportActionBar(mVu.getToobar());
        mActionBar = getSupportActionBar();

        mActionBar.setTitle("专栏");

        mArticleUrl = getIntent().getStringExtra(EXTRA_ARTICLE_URL);

        getApiParams(mArticleUrl);

        String apiFormat = "http://zhuanlan.zhihu.com/api/columns/{0}/posts/{1}";

        String articleAPI = MessageFormat.format(apiFormat, mColumnToken, mArticleId);

        mTask = new FetchArticleTask(ArticleActivity.this, articleAPI);
        HttpUtils.executeTask(mTask);

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

    public void onEvent(HttpResponseEvent event) {
        if (event instanceof FetchArticleHRE) {
            ZHArticle article = (ZHArticle) event.data;
            setArticle(article);
        }
    }

    private void setArticle(ZHArticle article) {
        mActionBar.setTitle(article.getColumn().getName());
        mVu.setTitle(article.getTitle());
        mVu.setTitleImg(article.getTitleImage(), mVolleyUtils.getImageLoader());
        mVu.setContent(HttpConstants.ANSWER_HTML_PRE + article.getContent() + HttpConstants.ANSWER_HTML_SUF);
        mVu.setAuthorName(article.getAuthor().getName());

        String avatarTemplate = "http://pic1.zhimg.com/{0}_{1}.jpg";
        String avatarId= article.getAuthor().getAvatar().getId();

        String avatarImgUrl = MessageFormat.format(avatarTemplate, avatarId, "s");

        mVu.setAuthorAvatar(avatarImgUrl, mVolleyUtils.getImageLoader());
        mVu.setVoteUpBtn(false, article.getLikesCount());
        mVu.setCommentBtn(article.getCommentsCount());
    }
}
