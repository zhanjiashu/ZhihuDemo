package com.jiashu.zhihudemo.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.jiashu.zhihudemo.mode.ZHArticle;
import com.jiashu.zhihudemo.mode.ZHQuestion;
import com.jiashu.zhihudemo.presenter.fragment.ArticleFragment;
import com.jiashu.zhihudemo.presenter.fragment.QuestionFragment;
import com.jiashu.zhihudemo.utils.ToastUtils;
import com.jiashu.zhihudemo.vu.FrameVu;

public class FrameActivity extends BasePresenterActivity<FrameVu> {

    public static final String EXTRA_PARAM = "param";

    private Fragment mFragment;
    private Bundle mArgs;

    @Override
    protected Class<FrameVu> getVuClass() {
        return FrameVu.class;
    }

    @Override
    protected void onBindVu() {

        Parcelable param = getIntent().getParcelableExtra(EXTRA_PARAM);

        if (param instanceof ZHQuestion) {
            mFragment = new QuestionFragment();

        } else if (param instanceof ZHArticle) {
            mFragment = new ArticleFragment();
        }

        mArgs = new Bundle();
        mArgs.putParcelable(EXTRA_PARAM, param);
        mFragment.setArguments(mArgs);

        if (mVu.getContainer() != null && mFragment != null) {
            mFm.beginTransaction()
                    .replace(mVu.getContainer().getId(), mFragment)
                    .commit();
        }
    }

    @Override
    protected void onDestroyVu() {

    }

    public static <T extends Parcelable> void startBy(Context context, T t) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(EXTRA_PARAM, t);
        context.startActivity(intent);
    }
}
