package com.jiashu.zhihudemo.vu;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.adapter.MyFragmentPagerAdapter;
import com.jiashu.zhihudemo.vu.Vu;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/4/25.
 */
public class ViewPagerVu implements Vu {

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

    @Override
    public void initMenu(MenuInflater inflater, Menu menu) {

    }

    public void setPagerAdapter(MyFragmentPagerAdapter adapter) {
        mPager.setAdapter(adapter);
        mTab.setViewPager(mPager);
    }
}
