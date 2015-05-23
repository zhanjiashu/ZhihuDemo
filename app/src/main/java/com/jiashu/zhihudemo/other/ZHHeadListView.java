package com.jiashu.zhihudemo.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.jiashu.zhihudemo.R;

/**
 * Created by Jiashu on 2015/5/22.
 */
public class ZHHeadListView extends ListView {
    public ZHHeadListView(Context context) {
        this(context, null);
    }

    public ZHHeadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeader(context);
    }

    private void initHeader(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View headerView = inflater.inflate(R.layout.list_head_question, null);
        this.addHeaderView(headerView);
    }
}
