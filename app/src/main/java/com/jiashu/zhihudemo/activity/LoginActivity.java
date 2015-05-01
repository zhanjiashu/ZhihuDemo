package com.jiashu.zhihudemo.activity;


import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.adapter.MyFragmentPagerAdapter;
import com.jiashu.zhihudemo.data.StringContants;
import com.jiashu.zhihudemo.fragment.guide.FifthGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.FirstGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.FourthGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.SecondGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.SixthGuideFragment;
import com.jiashu.zhihudemo.fragment.guide.ThirdGuideFragment;
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
        initData();
        mAdapter = new MyFragmentPagerAdapter(mFm, mFragments, null);
        mVu.setPageAdapter(mAdapter);

        mVu.setTextSwitchFactory(this);
        mVu.setTip(getString(StringContants.TIPS[0]));

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
                String tip = getString(StringContants.TIPS[position]);
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
                MainActivity.startBy(LoginActivity.this);
                finish();
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

