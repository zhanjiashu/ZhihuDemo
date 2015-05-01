package com.jiashu.zhihudemo.fragment.guide;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.fragment.BasePresenterFragment;
import com.jiashu.zhihudemo.vu.GuideFirstVu;

/**
 * Created by Jiashu on 2015/4/29.
 */
public class FirstGuideFragment extends BasePresenterFragment<GuideFirstVu> {

    private int[] mChildViewIds = {
            R.id.iv_guide_fist_04,
            R.id.iv_guide_fist_05, R.id.iv_guide_fist_06, R.id.iv_guide_fist_07,
            R.id.iv_guide_fist_08, R.id.iv_guide_fist_09, R.id.iv_guide_fist_10,
            R.id.iv_guide_fist_11, R.id.iv_guide_fist_12, R.id.iv_guide_fist_13,
    };

    @Override
    protected void onBindVu() {

        // 实现引导页首页 渐次 显示的效果
        for (int i = 0; i < mChildViewIds.length; i++) {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.splash_guide_first);
            animation.setDuration(500);
            animation.setStartOffset(200 * i);
            mVu.findViewById(mChildViewIds[i]).startAnimation(animation);
        }

    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<GuideFirstVu> getVuClass() {
        return GuideFirstVu.class;
    }
}
