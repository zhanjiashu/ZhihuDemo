package com.jiashu.zhihudemo.vu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.vu.Vu;

/**
 * Created by Jiashu on 2015/5/22.
 */
public class FrameVu extends Vu {

    View mView;

    FrameLayout mFragmentContainer;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.activity_frame, null);
        mFragmentContainer = (FrameLayout) mView.findViewById(R.id.fl_container);
    }

    @Override
    public View getView() {
        return mView;
    }

    public FrameLayout getContainer() {
        return mFragmentContainer;
    }
}
