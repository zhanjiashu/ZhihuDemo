package com.jiashu.zhihudemo.fragment;

import android.util.Log;

import com.jiashu.zhihudemo.adapter.FeedListAdapter;
import com.jiashu.zhihudemo.cmd.FetchHomePageNetCmd;
import com.jiashu.zhihudemo.mode.ZhiHuFeed;
import com.jiashu.zhihudemo.utils.HtmlUtils;
import com.jiashu.zhihudemo.utils.LogUtil;
import com.jiashu.zhihudemo.utils.NetUtil;
import com.jiashu.zhihudemo.vu.NormalListVu;
import com.jiashu.zhihudemo.data.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link BasePresenterFragment} subclass.
 */
public class HomeFragment extends BasePresenterFragment<NormalListVu> {

    private final String TAG = getClass().getSimpleName();
    public static final int INDEX = 0;

    private List<ZhiHuFeed> mFeedList;

    private FeedListAdapter mAdapter;

    @Override
    protected void onBindVu() {
        //mVu.setText(Constants.NAV_NAMES[INDEX]);

        mBus.register(this);

        mFeedList = new ArrayList<>();
        mAdapter = new FeedListAdapter(mFeedList);
        mVu.setAdapter(mAdapter);

        LogUtil.d(TAG, "HomeFragment");
        LogUtil.d(TAG, "HomeFragment's thread" + Thread.currentThread());
        FetchHomePageNetCmd netCmd = new FetchHomePageNetCmd();
/*        netCmd.setOnNetCmdCallback(new FetchHomePageNetCmd.CallbackListener() {

            @Override
            public void callback(List<ZhiHuFeed> feedList) {
                LogUtil.d(TAG, "Fetch home page success!");
                LogUtil.d(TAG, "FeedList'size " + feedList.size());
                mAdapter.addAll(feedList);
            }
        });*/
        NetUtil.execNetCmd(netCmd);

    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
    }

    public void onEventMainThread(String response) {
        LogUtil.d(TAG, response);
        mFeedList = NetUtil.getFeedList(response);
        mAdapter.replace(mFeedList);
    }

    @Override
    protected Class<NormalListVu> getVuClass() {
        return NormalListVu.class;
    }
}
