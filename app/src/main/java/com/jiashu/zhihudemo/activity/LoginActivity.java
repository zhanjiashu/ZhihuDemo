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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.ZhiHuApp;
import com.jiashu.zhihudemo.adapter.MyFragmentPagerAdapter;
import com.jiashu.zhihudemo.cmd.FetchCaptchaNetCmd;
import com.jiashu.zhihudemo.cmd.FetchXRSFNetCmd;
import com.jiashu.zhihudemo.cmd.LoginNetCmd;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.data.StringConstants;
import com.jiashu.zhihudemo.event.FetchCaptchaEvent;
import com.jiashu.zhihudemo.event.LoginEvent;
import com.jiashu.zhihudemo.event.UserBean;
import com.jiashu.zhihudemo.fragment.dialog.LoginDialogFragment;
import com.jiashu.zhihudemo.fragment.guide.FifthGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.FirstGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.FourthGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.SecondGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.SixthGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.ThirdGuideFragment;
import com.jiashu.zhihudemo.net.ZhiHuCookieManager;
import com.jiashu.zhihudemo.utils.HttpUtil;
import com.jiashu.zhihudemo.utils.LogUtil;
import com.jiashu.zhihudemo.utils.ToastUtils;
import com.jiashu.zhihudemo.utils.VolleyUtil;
import com.jiashu.zhihudemo.vu.GuidePageVu;
import com.jiashu.zhihudemo.vu.VuCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BasePresenterActivity<GuidePageVu>{

    private static final String TAG = "LoginActivity";

    private static final float ORIGIN_PARALLAX_COEFFICIENT = 1.2f;
    private static final float DECLINE_PARALLAX_COEFFICIENT = 0.5f;

    public static final String PREF_KEY_EMAIL = "login_email";

    private SharedPreferences mPref;

    MyFragmentPagerAdapter mAdapter;
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

        mPref = PreferenceManager.getDefaultSharedPreferences(ZhiHuApp.getContext());

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

        mAdapter = new MyFragmentPagerAdapter(mFm, mFragments, null);
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
                FetchXRSFNetCmd netCmd = new FetchXRSFNetCmd();
                HttpUtil.execNetCmd(netCmd);

                // 获取 验证码 图片
                /*FetchCaptchaNetCmd cmd = new FetchCaptchaNetCmd();
                HttpUtil.execNetCmd(cmd);*/
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
    public void onEventMainThread(UserBean event) {
        // 记住email
        mPref.edit()
                .putString(PREF_KEY_EMAIL, event.getEmail())
                .commit();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();

        LoginNetCmd netCmd = new LoginNetCmd(event);
        HttpUtil.execNetCmd(netCmd);
    }

    /**
     * 登录返回 的相关处理
     * @param event
     */
    public void onEvent(LoginEvent event) {
        LogUtil.d(TAG, "Login Response");
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

