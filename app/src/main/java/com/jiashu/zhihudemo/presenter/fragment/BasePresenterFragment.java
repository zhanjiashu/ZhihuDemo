package com.jiashu.zhihudemo.presenter.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.utils.VolleyUtils;
import com.jiashu.zhihudemo.vu.Vu;

import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/4/23.
 * 作为 Presenter Fragment 的基类
 */
public abstract class BasePresenterFragment<V extends Vu> extends Fragment {

    protected V mVu;
    protected EventBus mBus;
    protected VolleyUtils mVolleyUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBus = EventBus.getDefault();
        mVolleyUtils = VolleyUtils.getInstance(ZHApp.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        try {
            mVu = getVuClass().newInstance();
            mVu.initView(inflater, container);
            onBindVu();
            view = mVu.getView();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        afterResume();
    }

    @Override
    public void onPause() {
        beforePause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        onDestroyVu();
        mVu = null;
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mVu.initMenu(inflater, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * 在这里实现对 视图 的操作逻辑
     */
    protected abstract void onBindVu();

    /**
     * 销毁视图
     */
    protected abstract void onDestroyVu();

    /**
     * 返回关联 视图 的 class
     * @return
     */
    protected abstract Class<V> getVuClass();

    protected void afterResume() {}

    protected void beforePause() {}


}
