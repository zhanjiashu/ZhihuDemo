package com.jiashu.zhihudemo.presenter.adapter;

import com.android.volley.toolbox.ImageLoader;
import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.mode.ZHAnswer;
import com.jiashu.zhihudemo.utils.VolleyUtils;
import com.jiashu.zhihudemo.vu.item.AnswerItemVu;

import java.util.List;

/**
 * Created by Jiashu on 2015/5/22.
 */
public class ZHAnswerAdapter extends SimpleBaseAdapter<AnswerItemVu, ZHAnswer> {

    public ZHAnswerAdapter(List<ZHAnswer> data) {
        super(data);
    }

    @Override
    protected void onBindListItemVu(final int position) {
        final ZHAnswer answer = mData.get(position);

        ImageLoader loader = VolleyUtils.getInstance(ZHApp.getContext()).getImageLoader();

        mVu.setAvatar(answer.getAuthor().getAvatarUrl(), loader);

        mVu.setAuthorName(answer.getAuthor().getName());

        mVu.setVoteups(answer.getVoteupCount());

        mVu.setSummary(answer.getSummary());
    }

    @Override
    protected Class<AnswerItemVu> getVuClass() {
        return AnswerItemVu.class;
    }
}
