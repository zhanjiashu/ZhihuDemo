package com.jiashu.zhihudemo.vu;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiashu.zhihudemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/4/24.
 */
public class NavListItemVu implements Vu {

    View mView;

    @InjectView(R.id.iv_nav)
    ImageView mNavIcon;

    @InjectView(R.id.tv_nav)
    TextView mNavName;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.nav_list_item, container, false);
        ButterKnife.inject(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    public void setNavIcon(int resId) {
        mNavIcon.setImageResource(resId);
    }

    public void setNavName(CharSequence name) {
        mNavName.setText(name);
    }

    @Override
    public void initMenu(MenuInflater inflater, Menu menu) {}
}
