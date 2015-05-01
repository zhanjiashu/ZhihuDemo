package com.jiashu.zhihudemo.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiashu.zhihudemo.vu.Vu;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BasePresenterFragment<V extends Vu> extends Fragment {

    protected V mVu;

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

    protected abstract void onBindVu();

    protected abstract void onDestroyVu();

    protected abstract Class<V> getVuClass();

    protected void afterResume() {}

    protected void beforePause() {}


}
