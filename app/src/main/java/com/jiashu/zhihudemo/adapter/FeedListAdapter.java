package com.jiashu.zhihudemo.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.jiashu.zhihudemo.ZhiHuApp;
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

        if (feed.getSuppSide() == ZhiHuFeed.SUPP_LEFT) {
            mVu.setLeftText(feed.getSourceSupp());
            mVu.setRightText(feed.getSource());

            mVu.setLeftTextColor(Color.GRAY);
            mVu.setRightTextColor(Color.BLUE);

        } else if (feed.getSuppSide() == ZhiHuFeed.SUPP_RIGHT){
            mVu.setLeftText(feed.getSource());
            mVu.setRightText(feed.getSourceSupp());

            mVu.setLeftTextColor(Color.BLUE);
            mVu.setRightTextColor(Color.GRAY);
        }

        mVu.setTitle(feed.getTitle());
        String summary = feed.getSummary();
        if (TextUtils.isEmpty(summary)) {
            mVu.setContentVisibility(View.GONE);
        } else {
            mVu.setContentVisibility(View.VISIBLE);
            mVu.setSummary(summary);
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
