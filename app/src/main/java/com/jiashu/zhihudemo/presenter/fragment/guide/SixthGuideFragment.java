package com.jiashu.zhihudemo.presenter.fragment.guide;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.presenter.fragment.BasePresenterFragment;
import com.jiashu.zhihudemo.vu.guide.GuideSixthVu;

/**
 * Created by Jiashu on 2015/4/29.
 * 【引导页】 第六页
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
