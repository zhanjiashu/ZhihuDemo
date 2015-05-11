package com.jiashu.zhihudemo.fragment;


import android.text.Html;

import com.jiashu.zhihudemo.activity.AnswerActivity;
import com.jiashu.zhihudemo.adapter.FeedListAdapter;
import com.jiashu.zhihudemo.cmd.FetchHomePageNetCmd;
import com.jiashu.zhihudemo.cmd.FetchLoading;
import com.jiashu.zhihudemo.event.FetchCompletedEvent;
import com.jiashu.zhihudemo.event.FetchFailEvent;
import com.jiashu.zhihudemo.event.LoadingEvent;
import com.jiashu.zhihudemo.event.RefreshEvent;
import com.jiashu.zhihudemo.mode.ZhiHuFeed;
import com.jiashu.zhihudemo.other.ZHListView;
import com.jiashu.zhihudemo.utils.LogUtil;
import com.jiashu.zhihudemo.utils.HttpUtil;
import com.jiashu.zhihudemo.utils.ToastUtils;
import com.jiashu.zhihudemo.vu.AnswerVu;
import com.jiashu.zhihudemo.vu.NormalListVu;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/** [主页]
 * A simple {@link BasePresenterFragment} subclass.
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
                LogUtil.d(TAG, "The current feedList' size : " + mFeedList.size());
                LogUtil.d(TAG, "The last feed'id : " + lastFeedID);

                // 向服务器请求加载 Feed 流信息
                FetchLoading cmd = new FetchLoading(lastFeedID);
                HttpUtil.execNetCmd(cmd);

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
        HttpUtil.saveToFile(TAG, mResponse);
        LogUtil.d(TAG, "pause");
    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
    }

    /**
     * 获取 [首页] 内容
     */
    private void fetchHomePage() {
        FetchHomePageNetCmd netCmd = new FetchHomePageNetCmd();
        HttpUtil.execNetCmd(netCmd);
    }

    /**
     * 当前无网络连接时触发：读取持久化的 Feed 流信息（最新的 【首页】html）
     * @param event
     */
    public void onEventMainThread(FetchFailEvent event) {
        mResponse = HttpUtil.readFromFile(TAG);
        onEventMainThread(new FetchCompletedEvent(mResponse));
        //mBus.post(new FetchCompletedEvent(mResponse));
    }

    /**
     * 获取 [首页] 内容成功时触发，通知 ListView 显示
     * @param event
     */
    public void onEventMainThread(FetchCompletedEvent event) {

        mResponse = event.response;
        mFeedList = HttpUtil.getFeedList(event.response);
        mAdapter.replace(mFeedList);
    }

    /**
     * 向服务器请求 加载Feed流信息成功后触发
     * @param event
     */
    public void onEventMainThread(LoadingEvent event) {
        List<ZhiHuFeed> loadFeeds = HttpUtil.getFeedList(event.data);
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
    public void onEvent(RefreshEvent event) {
        fetchHomePage();
    }

    @Override
    protected Class<NormalListVu> getVuClass() {
        return NormalListVu.class;
    }
}
