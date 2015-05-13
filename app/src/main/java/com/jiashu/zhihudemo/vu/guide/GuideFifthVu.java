package com.jiashu.zhihudemo.vu.guide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.vu.Vu;

/**
 * Created by Jiashu on 2015/4/30.
 */
public class GuideFifthVu extends Vu {
    View mView;
    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_guide_fifth, container, false);
    }

    @Override
    public View getView() {
        return mView;
    }

}
