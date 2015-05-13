package com.jiashu.zhihudemo.presenter.fragment.inner;

import com.jiashu.zhihudemo.vu.general.BigListVu;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.presenter.fragment.BasePresenterFragment;

/**
 * Created by Jiashu on 2015/4/24.
 */
public class DiscTodayFragment extends BasePresenterFragment<BigListVu> {

    public static final int INDEX = 3;

    @Override
    protected void onBindVu() {
        mVu.setText(Constants.DISCOVERY_TAB_TITLES[INDEX]);
    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<BigListVu> getVuClass() {
        return BigListVu.class;
    }
}
