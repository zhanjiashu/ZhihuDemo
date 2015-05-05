package com.jiashu.zhihudemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.jiashu.zhihudemo.ZhiHuApp;
import com.jiashu.zhihudemo.data.NetConstants;
import com.jiashu.zhihudemo.mode.ZhiHuFeed;
import com.jiashu.zhihudemo.utils.VolleyUtil;
import com.jiashu.zhihudemo.vu.item.FeedItemVu;

import java.util.List;

/**
 * Created by Jiashu on 2015/5/5.
 */
public class FeedListAdapter extends SimpleBaseAdapter<FeedItemVu, ZhiHuFeed> {

    public FeedListAdapter(List<ZhiHuFeed> data) {
        super(data);
    }

    @Override
    protected void onBindListItemVu(int position) {
        ZhiHuFeed feed = mData.get(position);
        mVu.setLeftText("来自");
        mVu.setRightText(feed.getAuthor());
        mVu.setRightTextColor(Color.BLUE);
        mVu.setTitle(feed.getTitle());
        mVu.setSummary(feed.getSummary());

        String feedType = feed.getFeedType();
        if (feedType.equals(NetConstants.QUESTION_TOPIC) ||
                feedType.equals(NetConstants.QUESTION_MEMBER_FOLLOW) ||
                feedType.equals(NetConstants.COLUMN_MEMBER_FOLLOW)) {
            mVu.setContentVisibility(View.GONE);
        } else {
            mVu.setVoteups(feed.getVoteups());
        }

        ImageLoader loader = VolleyUtil.getInstance(ZhiHuApp.getContext()).getImageLoader();
        mVu.setAvatar(feed.getAvatarImgUrl(), loader);
    }

    @Override
    protected Class<FeedItemVu> getVuClass() {
        return FeedItemVu.class;
    }
}
