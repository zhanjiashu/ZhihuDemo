package com.jiashu.zhihudemo.vu.general;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.other.ZHListView;
import com.jiashu.zhihudemo.vu.Vu;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/4/25.
 */
public class NormalListVu extends Vu {
    View mView;

    @InjectView(R.id.lv_normal)
    ZHListView mListView;


    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.list_normal, container, false);
        ButterKnife.inject(this, mView);

        View footerView = inflater.inflate(R.layout.list_footer_feed, null);
        mListView.addFooterView(footerView);
    }

    @Override
    public View getView() {
        return mView;
    }

    public void setAdapter(BaseAdapter adapter) {
        mListView.setAdapter(adapter);
    }

    public void setItemClickable(boolean clickable) {
        mListView.setClickable(clickable);
    }

    public void setOnLoadingListener(ZHListView.OnLoadingListener listener) {
        mListView.setOnLoadingListener(listener);
    }

    public void setLoadCompleted() {
        mListView.setLoadCompleted();
    }
}
