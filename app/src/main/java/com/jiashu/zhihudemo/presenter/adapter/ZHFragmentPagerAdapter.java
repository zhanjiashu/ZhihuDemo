package com.jiashu.zhihudemo.presenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiashu on 2015/4/24.
 * ViewPager 的 FragmentStatePagerAdapter
 */
public class ZHFragmentPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mFragments;
    String[] mTabTitles;

    public ZHFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] tabTitles) {
        super(fm);
        mFragments = fragments == null ? new ArrayList<Fragment>() : fragments;
        mTabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
