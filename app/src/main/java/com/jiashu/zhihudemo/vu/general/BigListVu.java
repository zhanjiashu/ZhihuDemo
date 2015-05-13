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
import com.jiashu.zhihudemo.vu.Vu;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/4/25.
 */
public class BigListVu extends Vu {
    View mView;

    @InjectView(R.id.lv_big)
    ListView mListView;

    @InjectView(R.id.tv_big)
    TextView mTextView;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.list_big, container, false);
        ButterKnife.inject(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    public void setText(CharSequence msg) {
        mTextView.setText(msg);
    }

    public void setAdapter(BaseAdapter adapter) {
        mListView.setAdapter(adapter);
    }
}
