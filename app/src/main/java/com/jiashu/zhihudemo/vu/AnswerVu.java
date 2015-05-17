package com.jiashu.zhihudemo.vu;

import android.animation.ObjectAnimator;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.other.ZHAnswerView;
import com.jiashu.zhihudemo.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/5/8.
 */
public class AnswerVu extends Vu {

    private final String TAG = getClass().getSimpleName();
    View mView;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.tv_title)
    TextView mTitleView;

    @InjectView(R.id.tv_author)
    TextView mAuthorView;

    @InjectView(R.id.tv_profile)
    TextView mProfileView;

    @InjectView(R.id.tbtn_vote)
    ToggleButton mVoteBtn;

    @InjectView(R.id.iv_avatar)
    NetworkImageView mAvatarView;

    @InjectView(R.id.wv_content)
    ZHAnswerView mAnswerView;

    @InjectView(R.id.ll_author)
    LinearLayout mAuthorLayout;

    @InjectView(R.id.ll_bottom)
    LinearLayout mBottomLayout;

    @InjectView(R.id.tbtn_nohelp)
    ToggleButton mNoHelpBtn;

    @InjectView(R.id.tbtn_thank)
    ToggleButton mThankBtn;

    @InjectView(R.id.tbtn_comment)
    ToggleButton mCommentBtn;

    WebSettings mWebSettings;

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

    /**
     * 显示【问题】
     * @param question
     */
    public void setQuestion(String question) {
        mTitleView.setText(question);
    }

    /**
     * 显示 答案作者的名字
     * @param name
     */
    public void setAuthorName(String name) {
        mAuthorView.setText(name);
    }

    /**
     * 显示 答案作者的签名
     * @param profile
     */
    public void setAuthorProfile(String profile) {
        mProfileView.setText(profile);
    }

    /**
     * 显示 赞同数、以及用户的 投票状态
     * @param voteups
     */

    public void setVoteBtn(boolean isUpChecked, boolean isDownChecked, int voteups) {

        if (isDownChecked) {
            mVoteBtn.setButtonDrawable(R.drawable.ic_vote_down_checked);
        } else if (isUpChecked) {
            mVoteBtn.setButtonDrawable(R.drawable.ic_vote_checked);

        } else {
            mVoteBtn.setButtonDrawable(R.drawable.ic_btn_vote);
        }

        mVoteBtn.setText(voteups + "");
        mVoteBtn.setTextOff(voteups + "");
        mVoteBtn.setTextOn(voteups + "");
    }

    /**
     * 显示 答案作者头像
     * @param url
     * @param loader
     */
    public void setAvatar(String url, ImageLoader loader) {
        mAvatarView.setDefaultImageResId(R.drawable.ic_action_person_outline);
        mAvatarView.setErrorImageResId(R.drawable.ic_action_person_outline);
        mAvatarView.setImageUrl(url, loader);
    }

    /**
     * 初始化显示答案内容的 WebView，并通过子线程获取 顶部区域的高度
     * @param callback
     */

    public void initWebContent(int textZoom, Runnable callback) {
        mAnswerView.post(callback);
        mWebSettings = mAnswerView.getSettings();
        mWebSettings.setTextZoom(textZoom);
    }

    /**
     * 显示 答案
     * @param html
     */
    public void setAnswer(final String html) {
        mAnswerView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    /**
     * 设置 显示答案的 定制WebView 的滚动事件监听器
     * @param listener
     */
    public void setAnswerViewScrollListener(ZHAnswerView.OnScrollListener listener) {
        mAnswerView.setOnScrollListener(listener);
    }

    /**
     * 计算顶部区域的高度
     * @return
     */
    public int getTopHeight() {
        return mToolbar.getHeight() + mTitleView.getHeight() + mAuthorLayout.getHeight();
    }

    /**
     * 计算底部区域的高度
     * @return
     */
    public int getBottomHeight() {
        return mBottomLayout.getHeight();
    }

    /**
     * 隐藏顶部区域
     */
    public boolean hideTop() {
        ObjectAnimator animToolBar = ObjectAnimator.ofFloat(mToolbar, "translationY", 0, -mToolbar.getHeight());
        animToolBar.setDuration(200);
        animToolBar.start();

        ObjectAnimator animTitle = ObjectAnimator.ofFloat(mTitleView, "translationY", 0, -(mToolbar.getHeight() + mTitleView.getHeight()));
        animTitle.setDuration(300);
        animTitle.start();

        ObjectAnimator animAuthor = ObjectAnimator.ofFloat(mAuthorLayout, "translationY", 0, -(mToolbar.getHeight() + mTitleView.getHeight()));
        animAuthor.setDuration(300);
        animAuthor.start();

        return true;
    }

    /**
     * 显示顶部区域
     */
    public boolean showTop() {

        ObjectAnimator animToolBar = ObjectAnimator.ofFloat(mToolbar, "translationY", -mToolbar.getHeight(), 0);
        animToolBar.setDuration(200);
        animToolBar.start();

        ObjectAnimator animTitle = ObjectAnimator.ofFloat(mTitleView, "translationY", -(mToolbar.getHeight() + mTitleView.getHeight()), 0);
        animTitle.setDuration(300);
        animTitle.start();

        ObjectAnimator animAuthor = ObjectAnimator.ofFloat(mAuthorLayout, "translationY", -(mToolbar.getHeight() + mTitleView.getHeight()), 0);
        animAuthor.setDuration(350);
        animAuthor.start();

        return false;
    }

    /**
     * 隐藏 底部区域
     */
    public boolean hideBottom() {
        ObjectAnimator animBottom = ObjectAnimator.ofFloat(mBottomLayout, "translationY", 0, mBottomLayout.getHeight());
        animBottom.setDuration(350);
        animBottom.start();

        return true;
    }

    /**
     * 显示 底部区域
     */
    public boolean showBottom() {
        ObjectAnimator animBottom = ObjectAnimator.ofFloat(mBottomLayout, "translationY", mBottomLayout.getTranslationY(), 0);
        animBottom.setDuration(350);
        animBottom.start();

        return false;
    }

    /**
     * 设置 显示答案的 定制WebView 上的手势 监听器
     * @param listener
     */
    public void setContentViewTouchListener(View.OnTouchListener listener) {
        mAnswerView.setOnTouchListener(listener);
    }

    public void setComment(String text) {
        mCommentBtn.setText(text);
    }

    public void setVoteOnClickListener(View.OnClickListener listener) {
        mVoteBtn.setOnClickListener(listener);
    }

    public void setNoHelpBtn(boolean isNoHelped) {
        mNoHelpBtn.setChecked(isNoHelped);
    }

    public void setThankBtn(boolean isThanked) {
        mThankBtn.setChecked(isThanked);
    }
}
