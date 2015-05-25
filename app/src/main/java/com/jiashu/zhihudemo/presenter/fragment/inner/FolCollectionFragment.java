package com.jiashu.zhihudemo.presenter.fragment.inner;

import com.jiashu.zhihudemo.vu.general.NormalListVu;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.presenter.fragment.BasePresenterFragment;

/**
 * Created by Jiashu on 2015/4/25.
 */
public class FolCollectionFragment extends BasePresenterFragment<NormalListVu> {

    public static final int INDEX = 1;

    @Override
    protected void onBindVu() {
    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<NormalListVu> getVuClass() {
        return NormalListVu.class;
    }
}
