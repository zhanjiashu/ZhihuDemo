package com.jiashu.zhihudemo.presenter.activity;


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
import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.presenter.adapter.ZHFragmentPagerAdapter;
import com.jiashu.zhihudemo.task.FetchXrsfTask;
import com.jiashu.zhihudemo.task.LoginTask;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.data.StringConstants;
import com.jiashu.zhihudemo.events.LoginRE;
import com.jiashu.zhihudemo.events.OnLoginEvent;
import com.jiashu.zhihudemo.presenter.fragment.dialog.LoginDialogFragment;
import com.jiashu.zhihudemo.presenter.fragment.guide.FifthGuideFragment;
import com.jiashu.zhihudemo.presenter.fragment.guide.FirstGuideFragment;
import com.jiashu.zhihudemo.presenter.fragment.guide.FourthGuideFragment;
import com.jiashu.zhihudemo.presenter.fragment.guide.SecondGuideFragment;
import com.jiashu.zhihudemo.presenter.fragment.guide.SixthGuideFragment;
import com.jiashu.zhihudemo.presenter.fragment.guide.ThirdGuideFragment;
import com.jiashu.zhihudemo.net.ZhiHuCookieManager;
import com.jiashu.zhihudemo.utils.HttpUtils;
import com.jiashu.zhihudemo.utils.LogUtils;
import com.jiashu.zhihudemo.utils.ToastUtils;
import com.jiashu.zhihudemo.vu.guide.GuidePageVu;
import com.jiashu.zhihudemo.vu.VuCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiashu on 2015/5/3.
 * 启动页面的 Activity : 包括 引导页 和 登录
 */
public class LoginActivity extends BasePresenterActivity<GuidePageVu>{

    private final String TAG = getClass().getName();

    private static final float ORIGIN_PARALLAX_COEFFICIENT = 1.2f;
    private static final float DECLINE_PARALLAX_COEFFICIENT = 0.5f;

    public static final String PREF_KEY_EMAIL = "login_email";

    private SharedPreferences mPref;

    ZHFragmentPagerAdapter mAdapter;
    List<Fragment> mFragments;

    ArgbEvaluator mEvaluator;

    ProgressDialog mProgressDialog;

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

        mPref = PreferenceManager.getDefaultSharedPreferences(ZHApp.getContext());

        boolean isLogined = ZhiHuCookieManager.hasCookie("z_c0");

        if (isLogined) {
            MainActivity.startBy(LoginActivity.this);
            finish();
            return;
        }

        initViewPager();

        initTextSwitcher();

        initPageTransformer();

        handleButtonClick();

        fetchLoginParams();

    }

    private void fetchLoginParams() {

    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
        Log.d("LoginDiaogFragment", "onDestroyVu");
    }

    /**
     * 初始化 ViewPager 控件
     */
    private void initViewPager() {

        mFragments = new ArrayList<>();
        mFragments.add(new FirstGuideFragment());
        mFragments.add(new SecondGuideFragment());
        mFragments.add(new ThirdGuideFragment());
        mFragments.add(new FourthGuideFragment());
        mFragments.add(new FifthGuideFragment());
        mFragments.add(new SixthGuideFragment());

        mAdapter = new ZHFragmentPagerAdapter(mFm, mFragments, null);
        mVu.setPageAdapter(mAdapter);
    }

    /**
     * 初始化 initTextSwitcher 控件
     */
    private void initTextSwitcher() {
        mVu.setTextSwitchFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(LoginActivity.this);
                tv.setTextSize(15);
                tv.setTextColor(Color.parseColor("#FF7C91AC"));
                return tv;
            }
        });

        mVu.setTip(getString(StringConstants.TIPS[0]));
    }


    /**
     * 引导页的动画效果
     */
    private void initPageTransformer() {
        mEvaluator = new ArgbEvaluator();

        mPagerCount = mAdapter.getCount();

        mGuideStargBgc = getResources().getColor(R.color.bg_guide_start);
        mGuideEndBgc = getResources().getColor(R.color.bg_guide_end);

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

        mVu.setPageTransformer(true,
                new ParallaxTransformer(ORIGIN_PARALLAX_COEFFICIENT,
                        DECLINE_PARALLAX_COEFFICIENT));
    }

    /**
     * 处理 Login & Register 事件
     */
    private void handleButtonClick() {

        mVu.setLoginCallback(new VuCallback<Void>() {
            @Override
            public void execute(Void result) {
                // 显示 登录 对话框
                LoginDialogFragment dialog = new LoginDialogFragment();
                Bundle args = new Bundle();
                args.putString(PREF_KEY_EMAIL, mPref.getString(PREF_KEY_EMAIL, ""));
                dialog.setArguments(args);
                dialog.show(mFm, null);

                // 获取 _xrsf 参数
                FetchXrsfTask netCmd = new FetchXrsfTask();
                HttpUtils.executeTask(netCmd);

                // 获取 验证码 图片
                /*FetchCaptchaTask cmd = new FetchCaptchaTask();
                HttpUtils.executeTask(cmd);*/
            }
        });

        mVu.setRegisterCallback(new VuCallback<Void>() {
            @Override
            public void execute(Void result) {
                ToastUtils.show(Constants.TOAST_UNTAPPED);
            }
        });
    }

    /**
     * 跳转到另一个 Activity
     * @param context
     */
    public static void startBy(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 执行登录
     * @param event
     */
    public void onEventMainThread(OnLoginEvent event) {
        // 记住email
        mPref.edit()
                .putString(PREF_KEY_EMAIL, event.getEmail())
                .commit();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();

        LoginTask netCmd = new LoginTask(event);
        HttpUtils.executeTask(netCmd);
    }

    /**
     * 登录返回 的相关处理
     * @param event
     */
    public void onEvent(LoginRE event) {
        LogUtils.d(TAG, "Login Response");
        mProgressDialog.dismiss();

        switch (event.getStateCode()) {
            case HttpConstants.LOGIN_SUCCESS:
                MainActivity.startBy(LoginActivity.this);
                finish();
                break;
            case HttpConstants.ERRCODE_PWD_EMAIL_ERROR:
                ToastUtils.show(HttpConstants.ERRMSG_PWD_EMAIL_ERROR);
                break;
            case HttpConstants.ERRCODE_EMAIL_FORMAT_ERROR:
                ToastUtils.show(HttpConstants.ERRMSG_EMAIL_FORMAT_ERROR);
                break;
            case HttpConstants.ERRCODE_PWD_LENGTH_ERROR:
                ToastUtils.show(HttpConstants.ERRMSG_PWD_LENGTH_ERROR);
                break;
            case HttpConstants.ERRCODE_INPUT_CAPTCHA:
                ToastUtils.show(HttpConstants.ERRMSG__INPUT_CAPTCHA);
                break;
        }
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

