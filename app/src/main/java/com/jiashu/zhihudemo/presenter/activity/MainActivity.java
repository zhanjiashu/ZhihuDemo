package com.jiashu.zhihudemo.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.presenter.adapter.NavListAdapter;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.events.FetchHomePageRE;
import com.jiashu.zhihudemo.events.FetchFailEvent;
import com.jiashu.zhihudemo.events.OnRefreshEvent;
import com.jiashu.zhihudemo.presenter.fragment.main.CollectionFragment;
import com.jiashu.zhihudemo.presenter.fragment.main.DiscoveryFragment;
import com.jiashu.zhihudemo.presenter.fragment.main.DraftFragment;
import com.jiashu.zhihudemo.presenter.fragment.main.FollowFragment;
import com.jiashu.zhihudemo.presenter.fragment.main.HomeFragment;
import com.jiashu.zhihudemo.net.ZhiHuCookieManager;
import com.jiashu.zhihudemo.other.ZHRefreshHeaderTransformer;
import com.jiashu.zhihudemo.utils.LogUtils;
import com.jiashu.zhihudemo.vu.MainVu;
import com.jiashu.zhihudemo.vu.VuCallback;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * Created by Jiashu on 2015/4/25.
 * 主界面的 Activiy: 显示 【首页】、【发现】、【关注】、【收藏】、【草稿】等
 */

public class MainActivity extends BasePresenterActivity<MainVu> implements OnRefreshListener {

    private static final String TAG = "MainActivity<MainVu>";

    private ActionBarDrawerToggle mToggle;
    private NavListAdapter mAdapter;

    private SparseArray<Fragment> mFragmentCollector;
    private Fragment mCurrentFragment;
    private int mCurrentFragmentIndex = HomeFragment.INDEX;

    @Override
    protected Class getVuClass() {
        return MainVu.class;
    }

    @Override
    protected void onBindVu() {
        mBus.register(this);

        mFragmentCollector = new SparseArray<>();

        initActionBar();

        setContentFragment(mCurrentFragmentIndex);

        initNav();

        initRefreshLayout();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onBindMenu(int menuItem) {
        if (menuItem == R.id.action_logout) {
            ZhiHuCookieManager.clearCookies();
            LoginActivity.startBy(MainActivity.this);
            finish();
        }
    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
        LogUtils.d(TAG, "Activity destroy");
    }



    /**
     * 下拉时触发，通知 当前 Fragment 刷新内容
     * @param view
     */
    @Override
    public void onRefreshStarted(View view) {
        LogUtils.d(TAG, "refresh start...");
        mBus.post(new OnRefreshEvent());
    }

    /**
     * 初始化 ActionBar, 以 Toolbar 取代 ActionBar
     */
    private void initActionBar() {
        setSupportActionBar(mVu.getToolbar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Constants.NAV_NAMES[0]);

        mToggle = new ActionBarDrawerToggle(
                this,
                mVu.getDrawLayout(),
                mVu.getToolbar(),
                0,
                0
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(Constants.DEFAULT_TITLE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(Constants.NAV_NAMES[mCurrentFragmentIndex]);
            }
        };

        mVu.setDrawListener(mToggle);
    }

    /**
     * 关联 Fragment
     * @param index
     */
    private void setContentFragment(int index) {
        mCurrentFragment = mFragmentCollector.get(index);
        if (mCurrentFragment == null) {
            switch (index) {
                case HomeFragment.INDEX:
                    mCurrentFragment = new HomeFragment();
                    break;
                case DiscoveryFragment.INDEX:
                    mCurrentFragment = new DiscoveryFragment();
                    break;
                case FollowFragment.INDEX:
                    mCurrentFragment = new FollowFragment();
                    break;
                case CollectionFragment.INDEX:
                    mCurrentFragment = new CollectionFragment();
                    break;
                case DraftFragment.INDEX:
                    mCurrentFragment = new DraftFragment();
                    break;
                default:
                    break;
            }
            Log.d(TAG, mCurrentFragment.getClass().getSimpleName() + " is null");
        }

        if (mCurrentFragment != null) {
            mFm.beginTransaction()
                    .replace(mVu.getContainerLayoutId(), mCurrentFragment)
                    .commit();
        }

    }

    /**
     * 初始化 侧边栏的导航
     */
    private void initNav() {
        mAdapter = new NavListAdapter(Constants.NAV_LIST_DATA);
        mVu.setAdapter(mAdapter);

        mVu.setDefaultItemSelected(mCurrentFragmentIndex);

        mVu.setVuCallback(new VuCallback<Integer>() {
            @Override
            public void execute(Integer result) {
                mVu.getDrawLayout().closeDrawer(Gravity.START);

                if (result == QuizActivity.INDEX) {
                    QuizActivity.startBy(MainActivity.this);
                } else {
                    mCurrentFragmentIndex = result;
                    setContentFragment(mCurrentFragmentIndex);
                }
            }
        });
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initRefreshLayout() {
        ActionBarPullToRefresh.from(this)
                .options(Options.create()
                        .scrollDistance(0.5f)
                        .headerLayout(R.layout.refresh_header)
                        .headerTransformer(new ZHRefreshHeaderTransformer())
                        .build())
                .allChildrenArePullable()
                .listener(this)
                .setup(mVu.getRefreshLayout());
    }

    public static void startBy(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * 下拉刷新 成功时触发，关闭 下拉刷新 的动画
     * @param event
     */
    public void onEventMainThread(FetchHomePageRE event) {
        LogUtils.d(TAG, "refresh success");
        mVu.getRefreshLayout().setRefreshComplete();
    }

    /**
     * 下拉刷新 失败时触发，关闭 下拉刷新 的动画
     * @param event
     */
    public void onEventMainThread(FetchFailEvent event) {
        LogUtils.d(TAG, "refresh fail");
        mVu.getRefreshLayout().setRefreshComplete();
    }
}
