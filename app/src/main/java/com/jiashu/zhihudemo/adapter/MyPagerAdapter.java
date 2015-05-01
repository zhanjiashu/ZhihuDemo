package com.jiashu.zhihudemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Jiashu on 2015/4/24.
 */
public class MyPagerAdapter extends PagerAdapter {

    private List<View> mViews;
    private String[] mTabTitles;

    public MyPagerAdapter(List<View> views, String[] tabTitles) {
        mViews = views;
        mTabTitles = tabTitles;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }
}
