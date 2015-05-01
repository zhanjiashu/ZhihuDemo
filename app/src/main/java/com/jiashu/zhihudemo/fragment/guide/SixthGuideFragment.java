package com.jiashu.zhihudemo.fragment.guide;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.fragment.BasePresenterFragment;
import com.jiashu.zhihudemo.vu.GuideSixthVu;

/**
 * Created by Jiashu on 2015/4/29.
 */
public class SixthGuideFragment extends BasePresenterFragment<GuideSixthVu> {
    @Override
    protected void onBindVu() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.splash_guide_sixth);
        mVu.setAnimation(animation);
    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<GuideSixthVu> getVuClass() {
        return GuideSixthVu.class;
    }
}
