package com.jiashu.zhihudemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.adapter.NavListAdapter;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.fragment.CollectionFragment;
import com.jiashu.zhihudemo.fragment.DiscoveryFragment;
import com.jiashu.zhihudemo.fragment.DraftFragment;
import com.jiashu.zhihudemo.fragment.FollowFragment;
import com.jiashu.zhihudemo.fragment.HomeFragment;
import com.jiashu.zhihudemo.other.CustomisedHeaderTransformer;
import com.jiashu.zhihudemo.vu.MainVu;
import com.jiashu.zhihudemo.vu.VuCallback;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

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
            LoginActivity.startBy(MainActivity.this);
            finish();
        }
    }

    @Override
    protected void onDestroyVu() {}

    @Override
    public void onRefreshStarted(View view) {
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mVu.getRefreshLayout().setRefreshComplete();
                    }
                }, 5000);
    }

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

    private void initRefreshLayout() {
        ActionBarPullToRefresh.from(this)
                .options(Options.create()
                        .scrollDistance(0.5f)
                        .headerLayout(R.layout.refresh_header)
                        .headerTransformer(new CustomisedHeaderTransformer())
                        .build())
                .allChildrenArePullable()
                .listener(this)
                .setup(mVu.getRefreshLayout());
    }

    public static void startBy(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
