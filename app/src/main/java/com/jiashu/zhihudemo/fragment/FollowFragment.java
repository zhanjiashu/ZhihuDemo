package com.jiashu.zhihudemo.fragment;


import android.support.v4.app.Fragment;

import com.jiashu.zhihudemo.adapter.MyFragmentPagerAdapter;
import com.jiashu.zhihudemo.vu.ViewPagerVu;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.fragment.inner.FolCollectionFragment;
import com.jiashu.zhihudemo.fragment.inner.FolColumnFragment;
import com.jiashu.zhihudemo.fragment.inner.FolQuestionFragment;
import com.jiashu.zhihudemo.fragment.inner.FolTopicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link BasePresenterFragment} subclass.
 */
public class FollowFragment extends BasePresenterFragment<ViewPagerVu> {

    public static final int INDEX = 2;

    private List<Fragment> mFragments;
    private MyFragmentPagerAdapter mAdapter;

    @Override
    protected void onBindVu() {
        initData();
        mAdapter = new MyFragmentPagerAdapter(
                getActivity().getSupportFragmentManager(),
                mFragments,
                Constants.FOLLOW_TAB_TITLES);
        mVu.setPagerAdapter(mAdapter);
    }


    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<ViewPagerVu> getVuClass() {
        return ViewPagerVu.class;
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new FolQuestionFragment());
        mFragments.add(new FolCollectionFragment());
        mFragments.add(new FolTopicFragment());
        mFragments.add(new FolColumnFragment());
    }
}
