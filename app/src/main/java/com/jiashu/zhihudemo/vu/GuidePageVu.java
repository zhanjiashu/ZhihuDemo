package com.jiashu.zhihudemo.vu;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.adapter.MyFragmentPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jiashu on 2015/4/29.
 */
public class GuidePageVu implements Vu {
    View mView;

    @InjectView(R.id.vp_guide)
    ViewPager mViewPager;

    @InjectView(R.id.cpi_guide)
    CirclePageIndicator mIndicator;

    @InjectView(R.id.ts_guide_tip)
    TextSwitcher mTip;

    VuCallback<Void> mLoginCallback;

    VuCallback<Void> mRegisterCallback;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.activity_guide, container, false);
        ButterKnife.inject(this, mView);

    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public void initMenu(MenuInflater inflater, Menu menu) {

    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        mLoginCallback.execute(null);
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        mRegisterCallback.execute(null);
    }

    public void setPageAdapter(MyFragmentPagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
    }

    public void setTextSwitchFactory(ViewSwitcher.ViewFactory factory) {
        mTip.setFactory(factory);
    }

    public void setTip(CharSequence text) {
        mTip.setText(text);
    }

    public void setBackground(int color) {
        mView.setBackgroundColor(color);
    }

    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mIndicator.setOnPageChangeListener(listener);
    }

    public void setLoginCallback(VuCallback<Void> callback) {
        mLoginCallback = callback;
    }

    public void setRegisterCallback(VuCallback<Void> callback) {
        mRegisterCallback = callback;
    }

}
