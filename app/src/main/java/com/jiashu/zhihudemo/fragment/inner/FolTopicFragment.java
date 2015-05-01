package com.jiashu.zhihudemo.fragment.inner;

import com.jiashu.zhihudemo.vu.NormalListVu;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.fragment.BasePresenterFragment;

/**
 * Created by Jiashu on 2015/4/25.
 */
public class FolTopicFragment extends BasePresenterFragment<NormalListVu> {

    public static final int INDEX = 2;

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
