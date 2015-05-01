package com.jiashu.zhihudemo.fragment;

import com.jiashu.zhihudemo.vu.NormalListVu;
import com.jiashu.zhihudemo.data.Constants;

/**
 * A simple {@link BasePresenterFragment} subclass.
 */
public class HomeFragment extends BasePresenterFragment<NormalListVu> {

    public static final int INDEX = 0;

    @Override
    protected void onBindVu() {
        mVu.setText(Constants.NAV_NAMES[INDEX]);
    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<NormalListVu> getVuClass() {
        return NormalListVu.class;
    }
}
