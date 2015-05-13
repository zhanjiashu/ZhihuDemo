package com.jiashu.zhihudemo.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jiashu.zhihudemo.presenter.adapter.ZHFragmentPagerAdapter;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.presenter.fragment.inner.DestribeFragment;
import com.jiashu.zhihudemo.presenter.fragment.inner.QuestionFragment;
import com.jiashu.zhihudemo.presenter.fragment.inner.TopicFragment;
import com.jiashu.zhihudemo.vu.QuizVu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiashu on 2015/5/3.
 * 【提问】 的Activity
 */
public class QuizActivity extends BasePresenterActivity<QuizVu> {

    public static final int INDEX = 5;

    private List<Fragment> mFragments;
    private ZHFragmentPagerAdapter mAdapter;

    @Override
    protected Class<QuizVu> getVuClass() {
        return QuizVu.class;
    }

    @Override
    protected void onBindVu() {
        setSupportActionBar(mVu.getToolbar());
        getSupportActionBar().setTitle(Constants.QUIZ_TITLE);

        initData();

        mAdapter = new ZHFragmentPagerAdapter(mFm, mFragments, Constants.QUIZ_TAB_TITLES);
        mVu.setAdapter(mAdapter);

    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new QuestionFragment());
        mFragments.add(new DestribeFragment());
        mFragments.add(new TopicFragment());
    }

    @Override
    protected void onDestroyVu() {

    }

    public static void startBy(Context context) {
        Intent intent = new Intent(context, QuizActivity.class);
        context.startActivity(intent);
    }
}
