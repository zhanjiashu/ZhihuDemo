package com.jiashu.zhihudemo.presenter.fragment.main;


import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.jiashu.zhihudemo.presenter.adapter.ZHFragmentPagerAdapter;
import com.jiashu.zhihudemo.events.OnRefreshEvent;
import com.jiashu.zhihudemo.presenter.fragment.BasePresenterFragment;
import com.jiashu.zhihudemo.utils.ToastUtils;
import com.jiashu.zhihudemo.vu.general.ViewPagerVu;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.presenter.fragment.inner.DiscCollectFragment;
import com.jiashu.zhihudemo.presenter.fragment.inner.DiscMonthFragment;
import com.jiashu.zhihudemo.presenter.fragment.inner.DiscRecomFragment;
import com.jiashu.zhihudemo.presenter.fragment.inner.DiscTodayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link BasePresenterFragment} subclass.
 * 【发现】 页面
 */
public class DiscoveryFragment extends BasePresenterFragment<ViewPagerVu> {

    public static final int INDEX = 1;

    private List<Fragment> mFragments;
    private ZHFragmentPagerAdapter mAdapter;

    @Override
    protected void onBindVu() {
        mBus.register(this);

        // 允许Fragment添加菜单项
        setHasOptionsMenu(true);

        initData();

        mAdapter = new ZHFragmentPagerAdapter(
                getActivity().getSupportFragmentManager(),
                mFragments,
                Constants.DISCOVERY_TAB_TITLES
        );
        mVu.setPagerAdapter(mAdapter);
    }


    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
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

    public void onEvent(OnRefreshEvent event) {
        ToastUtils.show("refresh .....", Toast.LENGTH_SHORT);
    }
}
