package com.jiashu.zhihudemo.activity;


import android.animation.ArgbEvaluator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.ZhiHuApp;
import com.jiashu.zhihudemo.adapter.MyFragmentPagerAdapter;
import com.jiashu.zhihudemo.cmd.FetchXRSFNetCmd;
import com.jiashu.zhihudemo.cmd.LoginNetCmd;
import com.jiashu.zhihudemo.data.StringConstants;
import com.jiashu.zhihudemo.event.LoginEvent;
import com.jiashu.zhihudemo.fragment.dialog.LoginDialogFragment;
import com.jiashu.zhihudemo.fragment.guide.FifthGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.FirstGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.FourthGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.SecondGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.SixthGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.ThirdGuideFragment;
import com.jiashu.zhihudemo.net.ZhiHuCookieManager;
import com.jiashu.zhihudemo.utils.LogUtil;
import com.jiashu.zhihudemo.utils.NetUtil;
import com.jiashu.zhihudemo.vu.GuidePageVu;
import com.jiashu.zhihudemo.vu.VuCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BasePresenterActivity<GuidePageVu> implements ViewSwitcher.ViewFactory{

    private static final String TAG = "LoginActivity";

    private static final float ORIGIN_PARALLAX_COEFFICIENT = 1.2f;
    private static final float DECLINE_PARALLAX_COEFFICIENT = 0.5f;

    public static final String PREF_KEY_EMAIL = "login_email";

    private SharedPreferences mPref;

    MyFragmentPagerAdapter mAdapter;
    List<Fragment> mFragments;

    ArgbEvaluator mEvaluator;

    int mPagerCount;

    int mGuideStargBgc;
    int mGuideEndBgc;

    @Override
    protected Class<GuidePageVu> getVuClass() {
        return GuidePageVu.class;
    }

    @Override
    protected void onBindVu() {

        mBus.register(this);

        mPref = PreferenceManager.getDefaultSharedPreferences(ZhiHuApp.getContext());

        boolean isLogined = ZhiHuCookieManager.hasCookie("z_c0");

        if (isLogined) {
            MainActivity.startBy(LoginActivity.this);
            finish();
            return;
        }

        initData();
        mAdapter = new MyFragmentPagerAdapter(mFm, mFragments, null);
        mVu.setPageAdapter(mAdapter);

        mVu.setTextSwitchFactory(this);
        mVu.setTip(getString(StringConstants.TIPS[0]));

        initBackground();

        mVu.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float ratio = (position + positionOffset) / mPagerCount;
                int color = (int) mEvaluator.evaluate(ratio, mGuideStargBgc, mGuideEndBgc);
                mVu.setBackground(color);
            }

            @Override
            public void onPageSelected(int position) {
                String tip = getString(StringConstants.TIPS[position]);
                mVu.setTip(tip);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mVu.setPageTransformer(true, new ParallaxTransformer(ORIGIN_PARALLAX_COEFFICIENT, DECLINE_PARALLAX_COEFFICIENT));

        mVu.setLoginCallback(new VuCallback<Void>() {
            @Override
            public void execute(Void result) {
                LoginDialogFragment dialog = new LoginDialogFragment();
                Bundle args = new Bundle();
                args.putString(PREF_KEY_EMAIL, mPref.getString(PREF_KEY_EMAIL, ""));
                dialog.setArguments(args);
                dialog.show(mFm, null);

                FetchXRSFNetCmd netCmd = new FetchXRSFNetCmd();
                netCmd.setOnNetCmdCallback(new FetchXRSFNetCmd.CallbackListener() {
                    @Override
                    public void callback(String xsrf) {
                        LogUtil.d(TAG, "_xsrf = " + xsrf);
                        //login(null);
                    }
                });

                NetUtil.execNetCmd(netCmd);
            }
        });

        mVu.setRegisterCallback(new VuCallback<Void>() {
            @Override
            public void execute(Void result) {
                MainActivity.startBy(LoginActivity.this);
                finish();
            }
        });
    }

    private void initBackground() {
        mEvaluator = new ArgbEvaluator();

        mPagerCount = mAdapter.getCount();

        mGuideStargBgc = getResources().getColor(R.color.bg_guide_start);
        mGuideEndBgc = getResources().getColor(R.color.bg_guide_end);
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new FirstGuideFragment());
        mFragments.add(new SecondGuideFragment());
        mFragments.add(new ThirdGuideFragment());
        mFragments.add(new FourthGuideFragment());
        mFragments.add(new FifthGuideFragment());
        mFragments.add(new SixthGuideFragment());
    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
        Log.d("LoginDiaogFragment", "onDestroyVu");
    }

    @Override
    public View makeView() {
        TextView tv = new TextView(this);
        tv.setTextSize(15);
        tv.setTextColor(Color.parseColor("#FF7C91AC"));
        return tv;
    }

    public static void startBy(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public void onEvent(LoginEvent event) {
        mPref.edit()
                .putString(PREF_KEY_EMAIL, event.getEmail())
                .commit();
        login(event);
    }

    private void login(LoginEvent event) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        LoginNetCmd netCmd = new LoginNetCmd(event);
        netCmd.setOnNetCmdCallback(new LoginNetCmd.CallbackListener() {
            @Override
            public void callback(int status) {
                if (status == LoginNetCmd.LOGIN_SUCCESS) {
                    progressDialog.dismiss();
                    MainActivity.startBy(LoginActivity.this);
                    finish();
                }
            }
        });
        NetUtil.execNetCmd(netCmd);
    }

    /**
     * ViewPager 的视差滚动效果
     */
    class ParallaxTransformer implements ViewPager.PageTransformer {
        private float origin;
        private float decline;

        public ParallaxTransformer(float origin, float decline) {
            this.origin = origin;
            this.decline = decline;
        }

        @Override
        public void transformPage(View page, float position) {
            float childScrollXOffset = page.getWidth() * origin;
            ViewGroup pageWrapper = (ViewGroup) page;
            // 遍历 page 中的 子View ,首个 子View 的滑动距离 为 page 的 1.2 倍，之后的 子View 逐降一半
            for (int i = 0; i < pageWrapper.getChildCount(); i++) {
                pageWrapper.getChildAt(i).setTranslationX(childScrollXOffset * position);
                childScrollXOffset *= decline;
            }
        }
    }
}

