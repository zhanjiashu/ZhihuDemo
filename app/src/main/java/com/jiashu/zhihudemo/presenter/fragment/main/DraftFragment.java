package com.jiashu.zhihudemo.presenter.fragment.main;


import com.jiashu.zhihudemo.presenter.fragment.BasePresenterFragment;
import com.jiashu.zhihudemo.vu.general.BigListVu;
import com.jiashu.zhihudemo.data.Constants;

/**
 * A simple {@link BasePresenterFragment} subclass.
 * 【草稿】 页面
 */
public class DraftFragment extends BasePresenterFragment<BigListVu> {

    public static final int INDEX = 4;

    @Override
    protected void onBindVu() {
        mVu.setText(Constants.NAV_NAMES[INDEX]);
    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<BigListVu> getVuClass() {
        return BigListVu.class;
    }
}
