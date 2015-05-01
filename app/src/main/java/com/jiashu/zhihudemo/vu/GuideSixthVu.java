package com.jiashu.zhihudemo.vu;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.jiashu.zhihudemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/4/30.
 */
public class GuideSixthVu implements Vu {
    View mView;

    @InjectView(R.id.ll_logo)
    LinearLayout mLogoLayout;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_guide_sixth, container, false);
        ButterKnife.inject(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public void initMenu(MenuInflater inflater, Menu menu) {

    }

    public void setAnimation(Animation animation) {
        mLogoLayout.startAnimation(animation);
    }
}
