package com.jiashu.zhihudemo.fragment;


import android.support.v4.app.Fragment;

import com.jiashu.zhihudemo.adapter.MyFragmentPagerAdapter;
import com.jiashu.zhihudemo.vu.ViewPagerVu;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.fragment.inner.DiscCollectFragment;
import com.jiashu.zhihudemo.fragment.inner.DiscMonthFragment;
import com.jiashu.zhihudemo.fragment.inner.DiscRecomFragment;
import com.jiashu.zhihudemo.fragment.inner.DiscTodayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link BasePresenterFragment} subclass.
 */
public class DiscoveryFragment extends BasePresenterFragment<ViewPagerVu> {

    public static final int INDEX = 1;

    private List<Fragment> mFragments;
    private MyFragmentPagerAdapter mAdapter;

    @Override
    protected void onBindVu() {
        // 允许Fragment添加菜单项
        setHasOptionsMenu(true);

        initData();

        mAdapter = new MyFragmentPagerAdapter(
                getActivity().getSupportFragmentManager(),
                mFragments,
                Constants.DISCOVERY_TAB_TITLES
        );
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
        mFragments.add(new DiscRecomFragment());
        mFragments.add(new DiscCollectFragment());
        mFragments.add(new DiscMonthFragment());
        mFragments.add(new DiscTodayFragment());
    }
}
