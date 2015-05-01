package com.jiashu.zhihudemo.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jiashu.zhihudemo.adapter.MyFragmentPagerAdapter;
import com.jiashu.zhihudemo.adapter.MyPagerAdapter;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.fragment.inner.DestribeFragment;
import com.jiashu.zhihudemo.fragment.inner.QuestionFragment;
import com.jiashu.zhihudemo.fragment.inner.TopicFragment;
import com.jiashu.zhihudemo.vu.QuizVu;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends BasePresenterActivity<QuizVu> {

    public static final int INDEX = 5;

    private List<Fragment> mFragments;
    private MyFragmentPagerAdapter mAdapter;

    @Override
    protected Class<QuizVu> getVuClass() {
        return QuizVu.class;
    }

    @Override
    protected void onBindVu() {
        setSupportActionBar(mVu.getToolbar());
        getSupportActionBar().setTitle(Constants.QUIZ_TITLE);

        initData();

        mAdapter = new MyFragmentPagerAdapter(mFm, mFragments, Constants.QUIZ_TAB_TITLES);
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
