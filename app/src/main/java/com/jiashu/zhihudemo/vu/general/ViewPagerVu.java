package com.jiashu.zhihudemo.vu.general;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.presenter.adapter.ZHFragmentPagerAdapter;
import com.jiashu.zhihudemo.vu.Vu;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/4/25.
 */
public class ViewPagerVu extends Vu {

    View mView;

    @InjectView(R.id.psts_tab)
    PagerSlidingTabStrip mTab;

    @InjectView(R.id.vp_content)
    ViewPager mPager;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.view_pager_layout, container, false);
        ButterKnife.inject(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    public void setPagerAdapter(ZHFragmentPagerAdapter adapter) {
        mPager.setAdapter(adapter);
        mTab.setViewPager(mPager);
    }
}
