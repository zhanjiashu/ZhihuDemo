package com.jiashu.zhihudemo.presenter.fragment.inner;


import com.jiashu.zhihudemo.vu.general.EditVu;
import com.jiashu.zhihudemo.presenter.fragment.BasePresenterFragment;

/**
 * A simple {@link BasePresenterFragment} subclass.
 */
public class QuestionFragment extends BasePresenterFragment<EditVu> {

    @Override
    protected void onBindVu() {
        mVu.setEditHint("写下你的问题");
    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<EditVu> getVuClass() {
        return EditVu.class;
    }
}
