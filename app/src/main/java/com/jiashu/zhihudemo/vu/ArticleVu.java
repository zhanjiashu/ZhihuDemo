package com.jiashu.zhihudemo.vu;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.other.ZHScrollView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/5/20.
 * 显示【文章】
 */
public class ArticleVu extends Vu {
    View mView;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.niv_title_image)
    NetworkImageView mTitleImageView;

    @InjectView(R.id.sv_content)
    ZHScrollView mScrollView;

    @InjectView(R.id.v_placeholder)
    View mPlaceHolderView;

    @InjectView(R.id.tv_title)
    TextView mTitleView;

    @InjectView(R.id.niv_avatar)
    NetworkImageView mAvatarView;

    @InjectView(R.id.tv_author)
    TextView mAuthorView;

    @InjectView(R.id.wv_content)
    WebView mWebView;

    @InjectView(R.id.tbtn_vote_up)
    ToggleButton mVoteUpBtn;

    @InjectView(R.id.tbtn_vote_down)
    ToggleButton mVoteDownBtn;

    @InjectView(R.id.tbtn_comment)
    ToggleButton mCommentBtn;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.activity_article, null);
        ButterKnife.inject(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    public Toolbar getToobar() {
        return mToolbar;
    }

    // 显示文章内容
    public void setContent(String html) {
        mWebView.getSettings().setTextZoom(160);
        mWebView.loadDataWithBaseURL(HttpConstants.HOST, html, "text/html", "utf-8", null);
    }

    // 显示文章标题
    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    // 显示顶部的图片
    public void setTitleImg(String imgUrl, ImageLoader loader) {

        mTitleImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mTitleImageView.setImageUrl(imgUrl, loader);
    }

    // 显示文章的作者
    public void setAuthorName(String text) {
        mAuthorView.setText(text);
    }

    // 显示文章作者的头像
    public void setAuthorAvatar(String imgUrl, ImageLoader loader) {
        mAvatarView.setImageUrl(imgUrl, loader);
    }

    // 设置 赞同 按键
    public void setVoteUpBtn(boolean isChecked, int voteupCount) {
        mVoteUpBtn.setChecked(isChecked);
        if (isChecked) {
            voteupCount = voteupCount + 1;
        }
        mVoteUpBtn.setText("   " + voteupCount + " 赞");
        mVoteUpBtn.setTextOn("   " + voteupCount + " 赞");
        mVoteUpBtn.setTextOff("   " + voteupCount + " 赞");
    }

    public void setVoteDownBtn(boolean isChecked) {
        mVoteDownBtn.setChecked(isChecked);
    }

    // 设置 评论 按键
    public void setCommentBtn(int commentCount) {
        mCommentBtn.setText("   " + commentCount + " 评论");
        mCommentBtn.setTextOn("   " + commentCount + " 评论");
        mCommentBtn.setTextOff("   " + commentCount + " 评论");
    }

    // 设置ScrollView 滚动监听器
    public void setScrollLisener(ZHScrollView.OnScrollListener lisener) {
        mScrollView.setOnScrollListener(lisener);
    }

    // 隐藏顶部区域
    public void dimissHead() {
        mTitleImageView.setVisibility(View.GONE);
        mPlaceHolderView.setVisibility(View.GONE);
    }

    // 移动顶部图片
    public void moveTitleImage(float offset) {
        mTitleImageView.setTranslationY(offset);
    }


    public void setVoteUpListener(CompoundButton.OnCheckedChangeListener listener) {
        mVoteUpBtn.setOnCheckedChangeListener(listener);
    }

    public void setVoteDownListener(CompoundButton.OnCheckedChangeListener listener) {
        mVoteDownBtn.setOnCheckedChangeListener(listener);
    }
}
