package com.jiashu.zhihudemo.presenter.fragment.guide;

import com.jiashu.zhihudemo.presenter.fragment.BasePresenterFragment;
import com.jiashu.zhihudemo.vu.guide.GuideSecondVu;

/**
 * Created by Jiashu on 2015/4/29.
 * 【引导页】 第二页
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
