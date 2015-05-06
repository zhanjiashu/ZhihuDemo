package com.jiashu.zhihudemo.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;


/**
 * 自定义的 ListView ，嵌套于 ScrollView 使用。
 * 当 ListView 不位于顶端时，截断 父控件（ScrollView）对于 触摸 事件的处理；
 * 当 ListView 位于顶端时，恢复 父控件 （ScrollView) 对于 触摸 事件的处理。
 * 应用场景：
 *     配合 ActionBarPullToRereshLayout 和 ScrollView 使用，由于 ListView 是嵌套
 *     在 ScrollView 中的，无论 ListView 如何滚动 ScrollView 始终是位于屏幕顶端的，
 *     所以此时 无论 ListView 是否位于顶端，只要在屏幕向上滑动都会触发 ActionBarPullToRereshLayout 的
 *     刷新事件，同时 ListView 是无法滑动回顶端的。
 * Created by Jiashu on 2015/5/5.
 */
public class ZHListView extends ListView implements AbsListView.OnScrollListener {

    private static final String TAG = "CustomListView";
    private int mFirstVisibleItem;      // 当前屏幕ListView第一个可见子项的位置
    private int mLastVisibleItem;
    private int mTotalItemCount;

    private boolean isLoading;

    private OnLoadingListener mListener;

    public ZHListView(Context context) {
        super(context);
        this.setOnScrollListener(this);
    }

    public ZHListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
    }

    public ZHListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnScrollListener(this);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mLastVisibleItem == mTotalItemCount && scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                mListener.onLoading();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        mLastVisibleItem = firstVisibleItem + visibleItemCount;
        mTotalItemCount = totalItemCount;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            // 当手指触摸listview时，让父控件交出ontouch权限,不能滚动
            case MotionEvent.ACTION_DOWN:
                if (mFirstVisibleItem != 0) {
                    setParentScrollAble(false);
                } else {
                    setParentScrollAble(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 当手指松开时，让父控件重新获取onTouch权限
                setParentScrollAble(true);
                break;

        }

        return super.onInterceptTouchEvent(ev);
    }

    // 设置父控件是否可以获取到触摸处理权限
    private void setParentScrollAble(boolean flag) {
        getParent().requestDisallowInterceptTouchEvent(!flag);
    }


    public void setOnLoadingListener(OnLoadingListener listener) {
        this.mListener = listener;
    }

    public void setLoadCompleted() {
        isLoading = false;
    }

    public interface OnLoadingListener {
        void onLoading();
    }

}
