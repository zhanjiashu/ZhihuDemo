package com.jiashu.zhihudemo.fragment.inner;

import com.jiashu.zhihudemo.vu.BigListVu;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.fragment.BasePresenterFragment;

/**
 * Created by Jiashu on 2015/4/24.
 */
public class DiscCollectFragment extends BasePresenterFragment<BigListVu> {

    public static final int INDEX = 1;

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
