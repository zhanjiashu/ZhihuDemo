package com.jiashu.zhihudemo.presenter.fragment.inner;

import com.jiashu.zhihudemo.vu.general.NormalListVu;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.presenter.fragment.BasePresenterFragment;

/**
 * Created by Jiashu on 2015/4/25.
 */
public class FolColumnFragment extends BasePresenterFragment<NormalListVu> {

    public static final int INDEX = 3;

    @Override
    protected void onBindVu() {
        mVu.setText(Constants.FOLLOW_TAB_TITLES[INDEX]);
    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<NormalListVu> getVuClass() {
        return NormalListVu.class;
    }
}
