package com.jiashu.zhihudemo.vu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.other.ZHWebView;
import com.jiashu.zhihudemo.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

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
    ZHWebView mContentView;

    @InjectView(R.id.ll_source)
    LinearLayout mSourceLayout;

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


    public void setContent(String html) {

        mContentView.getSettings().setJavaScriptEnabled(true);
        mContentView.getSettings().setTextZoom(120);
        mContentView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    public void setWebViewScrollListener(ZHWebView.OnScrollListener listener) {
        mContentView.setOnScrollListener(listener);
    }

    public void hideTop() {
        ObjectAnimator animToolBar = ObjectAnimator.ofFloat(mToolbar, "translationY", 0, -mToolbar.getHeight());
        animToolBar.setDuration(200);
        animToolBar.start();

        ObjectAnimator animTitle = ObjectAnimator.ofFloat(mTitleView, "translationY", 0, -(mToolbar.getHeight() + mTitleView.getHeight()));
        animTitle.setDuration(300);
        animTitle.start();

        ObjectAnimator animSource = ObjectAnimator.ofFloat(mSourceLayout, "translationY", 0, -(mToolbar.getHeight() + mTitleView.getHeight()));
        animSource.setDuration(300);
        animSource.start();
    }

    public void showTop() {

        ObjectAnimator animToolBar = ObjectAnimator.ofFloat(mToolbar, "translationY", -mToolbar.getHeight(), 0);
        animToolBar.setDuration(200);
        animToolBar.start();

        ObjectAnimator animTitle = ObjectAnimator.ofFloat(mTitleView, "translationY", -(mToolbar.getHeight() + mTitleView.getHeight()), 0);
        animTitle.setDuration(300);
        animTitle.start();

        ObjectAnimator animSource = ObjectAnimator.ofFloat(mSourceLayout, "translationY", -(mToolbar.getHeight() + mTitleView.getHeight()), 0);
        animSource.setDuration(350);
        animSource.start();
    }

}
