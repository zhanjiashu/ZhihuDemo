package com.jiashu.zhihudemo.vu;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jiashu on 2015/4/23.
 */
public interface Vu {

    void initView(LayoutInflater inflater, ViewGroup container);

    View getView();

    void initMenu(MenuInflater inflater, Menu menu);
}
