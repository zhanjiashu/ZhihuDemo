package com.jiashu.zhihudemo.presenter.fragment.main;


import android.content.Intent;

import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.events.http.FetchFeedListHRE;
import com.jiashu.zhihudemo.events.http.FetchLoadingHRE;
import com.jiashu.zhihudemo.events.http.HttpResponseEvent;
import com.jiashu.zhihudemo.mode.ZHAnswer;
import com.jiashu.zhihudemo.mode.ZHArticle;
import com.jiashu.zhihudemo.mode.ZHFeed;
import com.jiashu.zhihudemo.mode.ZHQuestion;
import com.jiashu.zhihudemo.presenter.activity.AnswerActivity;

import com.jiashu.zhihudemo.presenter.activity.FrameActivity;
import com.jiashu.zhihudemo.task.FetchFeedListTask;
import com.jiashu.zhihudemo.task.FetchLoadingTask;

import com.jiashu.zhihudemo.events.OnRefreshEvent;
import com.jiashu.zhihudemo.other.ZHListView;
import com.jiashu.zhihudemo.presenter.adapter.ZHFeedListAdapter;
import com.jiashu.zhihudemo.presenter.fragment.BasePresenterFragment;
import com.jiashu.zhihudemo.utils.HttpUtils;
import com.jiashu.zhihudemo.utils.LogUtils;

import com.jiashu.zhihudemo.utils.ToastUtils;
import com.jiashu.zhihudemo.vu.general.NormalListVu;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link BasePresenterFragment} subclass.
 * 【首页】 页面
 */
public class HomeFragment extends BasePresenterFragment<NormalListVu> {

    private final String TAG = getClass().getSimpleName();
    public static final int INDEX = 0;


    private List<ZHFeed> mZHFeedList;

    private ZHFeedListAdapter mZHAdapter;

    private int mCurrentIndex;

    private String mResponse;

    @Override
    protected void onBindVu() {
        mBus.register(this);

        // 设置 ListView 的item 为不可点击
        mVu.setItemClickable(false);


        mZHFeedList = new ArrayList<>();


        mZHAdapter = new ZHFeedListAdapter(mZHFeedList);


        mVu.setAdapter(mZHAdapter);

        fetchFeedList();

        // 监听 ZHListView 底部上拉加载事件
        mVu.setOnLoadingListener(new ZHListView.OnLoadingListener() {
            @Override
            public void onLoading() {

                int feedsCount = mZHFeedList.size();
                if (feedsCount > 0) {

                    ZHFeed lastFeed = mZHFeedList.get(feedsCount - 1);

                    FetchLoadingTask task = null;
                    String nodeName = HttpUtils.getNodeName();
                    if (nodeName.equals(HttpConstants.NODE_NAME_TOP_STORY)) {
                        task = new FetchLoadingTask(
                                HttpConstants.LOADING_URL_TOP_STORY,
                                lastFeed.getDataBlock(),
                                lastFeed.getDataOffset()
                        );
                    } else {
                        task = new FetchLoadingTask(HttpConstants.LOADING_URL_HOME, lastFeed.getFeedId(), mZHFeedList.size());
                    }

                    HttpUtils.executeTask(task);

                }
            }
        });

        // 监听 item 不同区域的点击事件
        mZHAdapter.setZHOnItemClickListener(new ZHFeedListAdapter.ZHOnItemClickListener() {
            @Override
            public void onSourceClick(int position) {
                //ToastUtils.show(mZHFeedList.get(position).getSource().getUrl());
            }

            @Override
            public void onTitleClick(int position) {
                ZHFeed feed = mZHFeedList.get(position);
                String titleUrl = feed.getTitleUrl();
                String title = feed.getTitle();
                if ("p".equals(feed.getFeedType())) {
                    FrameActivity.startBy(getActivity(), new ZHArticle(titleUrl));
                } else {
                    FrameActivity.startBy(getActivity(), new ZHQuestion(titleUrl, title));
                }
            }

            @Override
            public void onContentClick(int position) {
                ZHFeed feed = mZHFeedList.get(position);

                if ("a".equals(feed.getFeedType())) {
                    AnswerActivity.startBy(HomeFragment.this, feed.getZHAnswer());
                    mCurrentIndex = position;
                } else if ("p".equals(feed.getFeedType())) {
                    FrameActivity.startBy(getActivity(), new ZHArticle(feed.getTitleUrl()));
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == getActivity().RESULT_OK) {
                    ZHAnswer answer = data.getParcelableExtra("data_return");
                    mZHFeedList.get(mCurrentIndex).setZHAnswer(answer);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "Home Fragment Destroy");
    }

    /**
     * 获取 [首页] 内容
     */
    private void fetchFeedList() {
        FetchFeedListTask task = new FetchFeedListTask();
        HttpUtils.executeTask(task);
    }


    public void onEvent(HttpResponseEvent event) {
        if (event instanceof FetchFeedListHRE) {
            mZHFeedList = (List<ZHFeed>) event.data;
            mZHAdapter.replace(mZHFeedList);
        } else if (event instanceof FetchLoadingHRE) {
            List<ZHFeed> loadFeeds = (List<ZHFeed>) event.data;

            if (mZHFeedList.addAll(loadFeeds)) {
                mZHAdapter.addAll(loadFeeds);
            }
            mVu.setLoadCompleted();
        }
    }

    /**
     * 下拉刷新时触发
     * @param event
     */
    public void onEvent(OnRefreshEvent event) {
        fetchFeedList();
    }


    /**
     * 关联 通用的 ListView 视图
     * @return
     */
    @Override
    protected Class<NormalListVu> getVuClass() {
        return NormalListVu.class;
    }
}
