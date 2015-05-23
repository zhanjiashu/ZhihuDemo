package com.jiashu.zhihudemo.presenter.fragment.main;


import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.mode.ZHArticle;
import com.jiashu.zhihudemo.mode.ZHFeed;
import com.jiashu.zhihudemo.mode.ZHQuestion;
import com.jiashu.zhihudemo.presenter.activity.AnswerActivity;

import com.jiashu.zhihudemo.presenter.activity.ArticleActivity;
import com.jiashu.zhihudemo.presenter.activity.FrameActivity;
import com.jiashu.zhihudemo.task.FetchHomePageTask;
import com.jiashu.zhihudemo.task.FetchLoadingTask;
import com.jiashu.zhihudemo.events.FetchHomePageRE;
import com.jiashu.zhihudemo.events.FetchFailEvent;
import com.jiashu.zhihudemo.events.FetchLoadingRE;
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

    private String mResponse;

    @Override
    protected void onBindVu() {
        mBus.register(this);

        // 设置 ListView 的item 为不可点击
        mVu.setItemClickable(false);


        mZHFeedList = new ArrayList<>();


        mZHAdapter = new ZHFeedListAdapter(mZHFeedList);


        mVu.setAdapter(mZHAdapter);

        fetchHomePage();

        // 监听 ZHListView 底部上拉加载事件
        mVu.setOnLoadingListener(new ZHListView.OnLoadingListener() {
            @Override
            public void onLoading() {


                ZHFeed lastFeed = mZHFeedList.get(mZHFeedList.size() - 1);

                FetchLoadingTask cmd = null;
                String nodeName = HttpUtils.getNodeName();
                if (nodeName.equals(HttpConstants.NODE_NAME_TOP_STORY)) {
                    cmd = new FetchLoadingTask(
                            HttpConstants.LOADING_URL_TOP_STORY,
                            lastFeed.getDataBlock(),
                            lastFeed.getDataOffset()
                            );
                } else {
                    cmd = new FetchLoadingTask(HttpConstants.LOADING_URL_HOME, lastFeed.getFeedId(), mZHFeedList.size());
                }

                HttpUtils.executeTask(cmd);

            }
        });

        // 监听 item 不同区域的点击事件
        mZHAdapter.setZHOnItemClickListener(new ZHFeedListAdapter.ZHOnItemClickListener() {
            @Override
            public void onSourceClick(int position) {
                ToastUtils.show(mZHFeedList.get(position).getSource().getUrl());
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
                    AnswerActivity.startBy(getActivity(), feed.getZHAnswer());
                } else if ("p".equals(feed.getFeedType())) {
                    FrameActivity.startBy(getActivity(), new ZHArticle(feed.getTitleUrl()));
                }
            }
        });
    }

    @Override
    protected void beforePause() {

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
    private void fetchHomePage() {
        FetchHomePageTask netCmd = new FetchHomePageTask();
        HttpUtils.executeTask(netCmd);
    }

    /**
     * 当前无网络连接时触发：读取持久化的 Feed 流信息（最新的 【首页】html）
     * @param event
     */
    public void onEventMainThread(FetchFailEvent event) {
        mResponse = HttpUtils.readFromFile(TAG);
        onEventMainThread(new FetchHomePageRE(mResponse));
    }

    /**
     * 获取 [首页] 内容成功时触发，通知 ListView 显示
     * @param event
     */
    public void onEventMainThread(FetchHomePageRE event) {

        mResponse = event.response;

        mZHFeedList = HttpUtils.parseHtmlToFeedList(event.response);

        mZHAdapter.replace(mZHFeedList);
    }

    /**
     * 向服务器请求 加载Feed流信息成功后触发
     * @param event
     */
    public void onEventMainThread(FetchLoadingRE event) {

        if (event.isSuccess) {
            List<ZHFeed> loadFeeds = HttpUtils.parseHtmlToFeedList(event.data);
            if (mZHFeedList.addAll(loadFeeds)) {
                mZHAdapter.addAll(loadFeeds);
            }
        }

        // 通知ZHListView 数据已加载完毕
        mVu.setLoadCompleted();
    }


    /**
     * 下拉刷新时触发
     * @param event
     */
    public void onEvent(OnRefreshEvent event) {
        fetchHomePage();
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
