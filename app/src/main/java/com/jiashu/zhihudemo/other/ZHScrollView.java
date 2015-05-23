package com.jiashu.zhihudemo.other;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Jiashu on 2015/5/21.
 */
public class ZHScrollView extends ScrollView {

    private OnScrollListener mScrollListener;

    public ZHScrollView(Context context) {
        this(context, null);
    }

    public ZHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mScrollListener != null) {
            mScrollListener.onScrollChanged(l, t, oldl, oldt);
        }

    }

    public void setOnScrollListener(OnScrollListener listener) {
        mScrollListener = listener;
    }

    public interface OnScrollListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
