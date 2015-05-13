package com.jiashu.zhihudemo.vu;

import android.animation.ObjectAnimator;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.other.ZHAnswerContentView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;

/**
 * Created by Jiashu on 2015/5/8.
 */
public class AnswerVu implements Vu {

    View mView;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.tv_title)
    TextView mTitleView;

    @InjectView(R.id.tv_author)
    TextView mAuthorView;

    @InjectView(R.id.tv_profile)
    TextView mProfileView;

    @InjectView(R.id.tv_voteups)
    TextView mVoteupsView;


    @InjectView(R.id.iv_avatar)
    NetworkImageView mAvatarView;

    @InjectView(R.id.wv_content)
    ZHAnswerContentView mContentView;

    @InjectView(R.id.ll_author)
    LinearLayout mAuthorLayout;

    @InjectView(R.id.ptr_layout)
    PullToRefreshLayout mRefreshLayout;

    ViewTreeObserver viewTreeObserver;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.activity_answer, container, false);
        ButterKnife.inject(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public void initMenu(MenuInflater inflater, Menu menu) {

    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setQuestion(String question) {
        mTitleView.setText(question);
    }

    public void setAuthorName(String name) {
        mAuthorView.setText(name);
    }

    public void setAuthorProfile(String profile) {
        mProfileView.setText(profile);
    }

    public void setVoteups(String voteups) {
        mVoteupsView.setText(voteups);
    }

    public void setAvatar(String url, ImageLoader loader) {
        mAvatarView.setDefaultImageResId(R.mipmap.ic_launcher);
        mAvatarView.setErrorImageResId(R.mipmap.ic_launcher);
        mAvatarView.setImageUrl(url, loader);
    }

    public void initWebContent(Runnable callback) {
        mContentView.post(callback);
        mContentView.getSettings().setJavaScriptEnabled(true);
        mContentView.getSettings().setTextZoom(120);
    }


    public void setContent(final String html) {
        mContentView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    public void setWebViewScrollListener(ZHAnswerContentView.OnScrollListener listener) {
        mContentView.setOnScrollListener(listener);
    }

    public void hideTop() {
        ObjectAnimator animToolBar = ObjectAnimator.ofFloat(mToolbar, "translationY", 0, -mToolbar.getHeight());
        animToolBar.setDuration(200);
        animToolBar.start();

        ObjectAnimator animTitle = ObjectAnimator.ofFloat(mTitleView, "translationY", 0, -(mToolbar.getHeight() + mTitleView.getHeight()));
        animTitle.setDuration(300);
        animTitle.start();

        ObjectAnimator animAuthor = ObjectAnimator.ofFloat(mAuthorLayout, "translationY", 0, -(mToolbar.getHeight() + mTitleView.getHeight()));
        animAuthor.setDuration(300);
        animAuthor.start();
    }

    public void showTop() {

        ObjectAnimator animToolBar = ObjectAnimator.ofFloat(mToolbar, "translationY", -mToolbar.getHeight(), 0);
        animToolBar.setDuration(200);
        animToolBar.start();

        ObjectAnimator animTitle = ObjectAnimator.ofFloat(mTitleView, "translationY", -(mToolbar.getHeight() + mTitleView.getHeight()), 0);
        animTitle.setDuration(300);
        animTitle.start();

        ObjectAnimator animAuthor = ObjectAnimator.ofFloat(mAuthorLayout, "translationY", -(mToolbar.getHeight() + mTitleView.getHeight()), 0);
        animAuthor.setDuration(350);
        animAuthor.start();
    }

    public int getTopHeight() {
        return mToolbar.getHeight() + mTitleView.getHeight() + mAuthorLayout.getHeight();
    }

}
