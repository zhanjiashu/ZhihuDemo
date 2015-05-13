package com.jiashu.zhihudemo.vu;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jiashu on 2015/4/23.
 */
public abstract class Vu {

    public abstract void initView(LayoutInflater inflater, ViewGroup container);

    public abstract View getView();

    public void initMenu(MenuInflater inflater, Menu menu) {

    }
}
