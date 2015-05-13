package com.jiashu.zhihudemo.vu.guide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.vu.Vu;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/4/30.
 */
public class GuideSixthVu extends Vu {
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

    public void setAnimation(Animation animation) {
        mLogoLayout.startAnimation(animation);
    }
}
