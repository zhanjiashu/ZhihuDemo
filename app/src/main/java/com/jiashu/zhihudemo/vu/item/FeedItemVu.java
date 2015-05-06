package com.jiashu.zhihudemo.vu.item;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.vu.Vu;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/5/4.
 */
public class FeedItemVu implements Vu {

    View mView;

    @InjectView(R.id.tv_left)
    TextView mLeftView;
    @InjectView(R.id.tv_right)
    TextView mRightView;
    @InjectView(R.id.tv_title)
    TextView mTitleView;
    @InjectView(R.id.tv_voteups)
    TextView mVoteupsView;
    @InjectView(R.id.tv_summary)
    TextView mSummaryView;

    @InjectView(R.id.iv_avatar)
    NetworkImageView mAvatarView;

    @InjectView(R.id.rl_content)
    RelativeLayout mContentLayout;

    @InjectView(R.id.rl_source)
    RelativeLayout mSourceLayout;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.list_item_feed, container, false);
        ButterKnife.inject(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public void initMenu(MenuInflater inflater, Menu menu) {

    }


    public void setLeftText(String str) {
        mLeftView.setText(str);
    }

    public void setLeftTextColor(int color) {
        mLeftView.setTextColor(color);
    }

    public void setRightTextColor(int color) {
        mRightView.setTextColor(color);
    }

    public void setRightText(String str) {
        mRightView.setText(str);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setVoteups(int voteups) {
        if (voteups > 1000) {
            float voteupsFloat = (float)(voteups / 100) / 10; 
            mVoteupsView.setText(voteupsFloat+"K");
        } else {
            mVoteupsView.setText(voteups+"");
        }
    }

    public void setSummary(String content) {
        mSummaryView.setText(content);
    }

    public void setContentVisibility(int visibility) {
        mContentLayout.setVisibility(visibility);
    }

    public void setAvatar(String imgUrl, ImageLoader loader) {
        mAvatarView.setDefaultImageResId(R.mipmap.ic_launcher);
        mAvatarView.setErrorImageResId(R.mipmap.ic_launcher);
        mAvatarView.setImageUrl(imgUrl, loader);
    }

    public void setOnTitleClickListener (View.OnClickListener listener){
        mTitleView.setOnClickListener(listener);
    }

    public void setOnContentClickListener(View.OnClickListener listener) {
        mContentLayout.setOnClickListener(listener);
    }

    public void setOnSourceClickListener(View.OnClickListener listener) {
        mSourceLayout.setOnClickListener(listener);
    }
}
