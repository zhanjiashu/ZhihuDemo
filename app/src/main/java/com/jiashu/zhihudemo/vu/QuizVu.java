package com.jiashu.zhihudemo.vu;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.jiashu.zhihudemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/4/24.
 */
public class QuizVu extends Vu {

    View mView;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.psts_tab)
    PagerSlidingTabStrip mTabStrip;

    @InjectView(R.id.vp_content)
    ViewPager mContentPager;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.activity_quiz, container);
        ButterKnife.inject(this, mView);

    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public void initMenu(MenuInflater inflater, Menu menu) {
        inflater.inflate(R.menu.menu_quiz, menu);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setAdapter(FragmentStatePagerAdapter adapter) {
        mContentPager.setAdapter(adapter);
        mTabStrip.setViewPager(mContentPager);
    }

}
