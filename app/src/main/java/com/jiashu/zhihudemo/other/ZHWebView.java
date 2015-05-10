package com.jiashu.zhihudemo.other;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;


/**
 * Created by Jiashu on 2015/5/9.
 */
public class ZHWebView extends WebView {

    private OnScrollListener mScrollListener;

    public ZHWebView(Context context) {
        this(context, null);
    }

    public ZHWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollListener.onScrollChanged(l, t, oldl, oldt);
    }


    public void setOnScrollListener(OnScrollListener listener) {
        mScrollListener = listener;
    }

    public interface OnScrollListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
