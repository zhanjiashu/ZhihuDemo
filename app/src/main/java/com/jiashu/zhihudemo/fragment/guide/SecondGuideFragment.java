package com.jiashu.zhihudemo.fragment.guide;

import com.jiashu.zhihudemo.fragment.BasePresenterFragment;
import com.jiashu.zhihudemo.vu.GuideSecondVu;

/**
 * Created by Jiashu on 2015/4/29.
 */
public class SecondGuideFragment extends BasePresenterFragment<GuideSecondVu> {
    @Override
    protected void onBindVu() {
    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<GuideSecondVu> getVuClass() {
        return GuideSecondVu.class;
    }
}
