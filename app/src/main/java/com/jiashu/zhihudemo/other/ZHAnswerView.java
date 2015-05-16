package com.jiashu.zhihudemo.other;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;


/**
 * Created by Jiashu on 2015/5/9.
 * 显示【答案】详细内容的 WebView, 实现了对 滚动 事件的监听
 */
public class ZHAnswerView extends WebView {

    private final String TAG = getClass().getSimpleName();
    private OnScrollListener mScrollListener;

    public ZHAnswerView(Context context) {
        this(context, null);
    }

    public ZHAnswerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        int cotentHeight = (int) (getContentHeight() * getScale());
        int currentHeight = getScrollY() + getHeight();

        mScrollListener.onScrollChanged(l, t, oldl, oldt, cotentHeight == currentHeight);
    }


    public void setOnScrollListener(OnScrollListener listener) {
        mScrollListener = listener;
    }

    public interface OnScrollListener {
        void onScrollChanged(int l, int t, int oldl, int oldt, boolean isScrollToBottom);
    }
}
