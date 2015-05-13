package com.jiashu.zhihudemo.presenter.fragment.inner;


import com.jiashu.zhihudemo.vu.general.EditVu;
import com.jiashu.zhihudemo.presenter.fragment.BasePresenterFragment;

/**
 * A simple {@link BasePresenterFragment} subclass.
 */
public class TopicFragment extends BasePresenterFragment<EditVu> {

    @Override
    protected void onBindVu() {
        mVu.setEditHint("添加相关话题");
    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<EditVu> getVuClass() {
        return EditVu.class;
    }
}
