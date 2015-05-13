package com.jiashu.zhihudemo.presenter.fragment.main;


import com.jiashu.zhihudemo.presenter.activity.AnswerActivity;
import com.jiashu.zhihudemo.presenter.adapter.FeedListAdapter;
import com.jiashu.zhihudemo.command.FetchHomePageCmd;
import com.jiashu.zhihudemo.command.FetchLoadingCmd;
import com.jiashu.zhihudemo.events.FetchHomePageRE;
import com.jiashu.zhihudemo.events.FetchFailEvent;
import com.jiashu.zhihudemo.events.FetchLoadingRE;
import com.jiashu.zhihudemo.events.OnRefreshEvent;
import com.jiashu.zhihudemo.mode.ZhiHuFeed;
import com.jiashu.zhihudemo.other.ZHListView;
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

    private List<ZhiHuFeed> mFeedList;

    private FeedListAdapter mAdapter;

    private String mResponse;

    @Override
    protected void onBindVu() {
        mBus.register(this);

        // 设置 ListView 的item 为不可点击
        mVu.setItemClickable(false);

        mFeedList = new ArrayList<>();
        mAdapter = new FeedListAdapter(mFeedList);
        mVu.setAdapter(mAdapter);

        fetchHomePage();

        // 监听 ZHListView 底部上拉加载事件
        mVu.setOnLoadingListener(new ZHListView.OnLoadingListener() {
            @Override
            public void onLoading() {
                String lastFeedID = mFeedList.get(mFeedList.size() - 1).getFeedID();
                LogUtils.d(TAG, "The current feedList' size : " + mFeedList.size());
                LogUtils.d(TAG, "The last feed'id : " + lastFeedID);

                // 向服务器请求加载 Feed 流信息
                FetchLoadingCmd cmd = new FetchLoadingCmd(lastFeedID);
                HttpUtils.exeCmd(cmd);

            }
        });

        mAdapter.setZHOnItemClickListener(new FeedListAdapter.ZHOnItemClickListener() {
            @Override
            public void onSourceClick(int position) {
                ToastUtils.show(mFeedList.get(position).getSource());
            }

            @Override
            public void onTitleClick(int position) {
                ToastUtils.show(mFeedList.get(position).getTitle());
            }

            @Override
            public void onContentClick(int position) {
                ZhiHuFeed feed = mFeedList.get(position);
                AnswerActivity.startBy(getActivity(), feed);
            }
        });
    }

    @Override
    protected void beforePause() {
        // 持久化 【首页】html
        HttpUtils.saveToFile(TAG, mResponse);
        LogUtils.d(TAG, "pause");
    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
    }

    /**
     * 获取 [首页] 内容
     */
    private void fetchHomePage() {
        FetchHomePageCmd netCmd = new FetchHomePageCmd();
        HttpUtils.exeCmd(netCmd);
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
        mFeedList = HttpUtils.getFeedList(event.response);
        mAdapter.replace(mFeedList);
    }

    /**
     * 向服务器请求 加载Feed流信息成功后触发
     * @param event
     */
    public void onEventMainThread(FetchLoadingRE event) {
        List<ZhiHuFeed> loadFeeds = HttpUtils.getFeedList(event.data);
        if (mFeedList.addAll(loadFeeds)) {
            mAdapter.addAll(loadFeeds);
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
