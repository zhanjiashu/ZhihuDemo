package com.jiashu.zhihudemo.fragment.inner;


import com.jiashu.zhihudemo.vu.EditVu;
import com.jiashu.zhihudemo.fragment.BasePresenterFragment;

/**
 * A simple {@link BasePresenterFragment} subclass.
 */
public class DestribeFragment extends BasePresenterFragment<EditVu> {

    @Override
    protected void onBindVu() {
        setHasOptionsMenu(true);
        mVu.setEditHint("添加问题的补充说明");
    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<EditVu> getVuClass() {
        return EditVu.class;
    }
}
