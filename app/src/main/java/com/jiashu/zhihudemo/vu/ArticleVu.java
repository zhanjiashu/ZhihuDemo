package com.jiashu.zhihudemo.vu;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.data.HttpConstants;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/5/20.
 */
public class ArticleVu extends Vu {
    View mView;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.niv_title_image)
    NetworkImageView mTitleImageView;

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

    public void setContent(String html) {
        mWebView.getSettings().setTextZoom(160);
        mWebView.loadDataWithBaseURL(HttpConstants.HOST, html, "text/html", "utf-8", null);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setTitleImg(String imgUrl, ImageLoader loader) {

        if (TextUtils.isEmpty(imgUrl)) {
            mTitleImageView.setVisibility(View.GONE);
            mPlaceHolderView.setVisibility(View.GONE);
            return;
        }
        mTitleImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mTitleImageView.setImageUrl(imgUrl, loader);
    }

    public void setAuthorName(String text) {
        mAuthorView.setText(text);
    }

    public void setAuthorAvatar(String imgUrl, ImageLoader loader) {
        mAvatarView.setImageUrl(imgUrl, loader);
    }

    public void setVoteUpBtn(boolean isChecked, int voteupCount) {
        mVoteUpBtn.setChecked(isChecked);
        mVoteUpBtn.setText("   " + voteupCount + " 赞");
        mVoteUpBtn.setTextOn("   " + voteupCount + " 赞");
        mVoteUpBtn.setTextOff("   " + voteupCount + " 赞");
    }

    public void setCommentBtn(int commentCount) {
        mCommentBtn.setText("   " + commentCount + " 评论");
        mCommentBtn.setTextOn("   " + commentCount + " 评论");
        mCommentBtn.setTextOff("   " + commentCount + " 评论");
    }
}
