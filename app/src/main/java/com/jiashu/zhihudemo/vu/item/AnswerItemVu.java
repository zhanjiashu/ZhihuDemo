package com.jiashu.zhihudemo.vu.item;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.vu.Vu;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/5/22.
 */
public class AnswerItemVu extends Vu {
    View mView;

    @InjectView(R.id.niv_avatar)
    NetworkImageView mAvatarView;

    @InjectView(R.id.tv_voteups)
    TextView mVoteupsView;

    @InjectView(R.id.tv_author)
    TextView mAuthorView;

    @InjectView(R.id.tv_summary)
    TextView mSummaryView;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.list_item_answer, null);
        ButterKnife.inject(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    public void setAvatar(String avatarUrl, ImageLoader loader) {
        if (!TextUtils.isEmpty(avatarUrl)) {
            mAvatarView.setImageUrl(avatarUrl, loader);
        }
    }

    public void setVoteups(int voteups) {
        if (voteups > 1000) {
            float voteupsFloat = (float)(voteups / 100) / 10;
            mVoteupsView.setText(voteupsFloat+"K");
        } else {
            mVoteupsView.setText(voteups+"");
        }
    }

    public void setAuthorName(String name) {
        mAuthorView.setText(name);
    }

    public void setSummary(String text) {
        mSummaryView.setText(text);
    }
}
