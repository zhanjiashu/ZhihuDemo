package com.jiashu.zhihudemo.presenter.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.mode.ZHFeed;
import com.jiashu.zhihudemo.utils.VolleyUtils;
import com.jiashu.zhihudemo.vu.item.FeedItemVu;

import java.util.List;

/**
 * Created by Jiashu on 2015/5/5.
 */
public class ZHFeedListAdapter extends SimpleBaseAdapter<FeedItemVu, ZHFeed> {

    private ZHOnItemClickListener mListener;

    public ZHFeedListAdapter(List<ZHFeed> data) {
        super(data);
    }

    @Override
    protected void onBindListItemVu(final int position) {
        final ZHFeed feed = mData.get(position);

        String sourceSupplement = feed.getSourceSupplement();

        if ("来自".equals(sourceSupplement)|| "热门回答".equals(sourceSupplement)) {
            mVu.setLeftText(feed.getSourceSupplement());
            mVu.setRightText(feed.getSourceName());

            mVu.setLeftTextColor(Color.GRAY);
            mVu.setRightTextColor(Color.BLUE);
        } else {
            mVu.setLeftText(feed.getSourceName());
            mVu.setRightText(feed.getSourceSupplement());

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

        ImageLoader loader = VolleyUtils.getInstance(ZHApp.getContext()).getImageLoader();
        mVu.setAvatar(feed.getSourceAvatarUrl(), loader);

        mVu.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onTitleClick(position);
            }
        });

        mVu.setOnContentClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onContentClick(position);
            }
        });

        mVu.setOnSourceClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSourceClick(position);
            }
        });
    }

    @Override
    protected Class<FeedItemVu> getVuClass() {
        return FeedItemVu.class;
    }

    public void setZHOnItemClickListener(ZHOnItemClickListener listener) {
        mListener = listener;
    }

    public interface ZHOnItemClickListener {
        void onSourceClick(int position);
        void onTitleClick(int position);
        void onContentClick(int position);
    }
}
