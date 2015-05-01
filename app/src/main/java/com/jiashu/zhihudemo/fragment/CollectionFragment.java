package com.jiashu.zhihudemo.fragment;


import com.jiashu.zhihudemo.vu.BigListVu;
import com.jiashu.zhihudemo.data.Constants;

/**
 * A simple {@link CollectionFragment} subclass.
 */
public class CollectionFragment extends BasePresenterFragment<BigListVu> {

    public static final int INDEX = 3;

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
