package com.jiashu.zhihudemo.vu;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.presenter.adapter.SimpleBaseAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;

/**
 * Created by Jiashu on 2015/4/23.
 */
public class MainVu extends Vu {

    View mView;

    @InjectView(R.id.toolbar) Toolbar mToolbar;

    @InjectView(R.id.dl_layout) DrawerLayout mDrawerLayout;

    @InjectView(R.id.lv_nav) ListView mNavListView;

    @InjectView(R.id.fl_container)
    ScrollView mConainerLayout;

    @InjectView(R.id.ptr_layout)
    PullToRefreshLayout mRefreshLayout;

    VuCallback<Integer> mCallback;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.activity_main, container);
        ButterKnife.inject(this, mView);

        mNavListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.execute(position);
            }
        });
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public void initMenu(MenuInflater inflater, Menu menu) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public DrawerLayout getDrawLayout() {
        return mDrawerLayout;
    }

    public int getContainerLayoutId() {
        return mConainerLayout.getId();
    }

    public void setDrawListener(ActionBarDrawerToggle toggle) {
        mDrawerLayout.setDrawerListener(toggle);
    }

    public void setAdapter(SimpleBaseAdapter adapter) {
        mNavListView.setAdapter(adapter);
    }

    public void setDefaultItemSelected(int position) {
        mNavListView.setItemChecked(position, true);
    }

    public PullToRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public void setVuCallback(VuCallback<Integer> callback) {
        mCallback = callback;
    }
}
