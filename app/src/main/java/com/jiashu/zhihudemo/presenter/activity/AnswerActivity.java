package com.jiashu.zhihudemo.presenter.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.mode.ZHAnswer;
import com.jiashu.zhihudemo.mode.ZHQuestion;
import com.jiashu.zhihudemo.task.FetchAnswerTask;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.events.VoteEvent;
import com.jiashu.zhihudemo.events.http.FetchAnswerHRE;
import com.jiashu.zhihudemo.events.http.HttpResponseEvent;

import com.jiashu.zhihudemo.other.ZHAnswerView;
import com.jiashu.zhihudemo.presenter.fragment.dialog.VoteDialogFragment;
import com.jiashu.zhihudemo.utils.HttpUtils;

import com.jiashu.zhihudemo.vu.AnswerVu;

/**
 * Created by Jiashu on 2015/5/3.
 * 【回答】 的 内容详情页面
 */
public class AnswerActivity extends BasePresenterActivity<AnswerVu> {

    private static final String TAG = "AnswerActivity";

    private static final int OFFSET = 360;

    private static final float CURRENT_DENSITY = ZHApp.getContext().getResources().getDisplayMetrics().density;

    private static final String EXTRA_ANSWER = "answer";

    private ImageLoader mImageLoader;
    private GestureDetector mDetector;

    private boolean isTopHide;
    private boolean isBottomHide;
    private boolean isTouchEnable;

    private String mAnswerContent;
    private int mVoteups;

    private boolean isVoteUpChecked;

    private boolean isVoteDownChecked;

    @Override
    protected Class<AnswerVu> getVuClass() {
        return AnswerVu.class;
    }

    @Override
    protected void onBindVu() {

        mBus.register(this);
        mImageLoader = mVolleyUtils.getImageLoader();

        Intent intent = getIntent();
        ZHAnswer answer = intent.getParcelableExtra(EXTRA_ANSWER);
        if (answer == null) {
            answer = new ZHAnswer();
        }

        initAnswerView(answer);

        handleTitleClick(answer);

        handleAnimation();

        handleVote();

    }

    /**
     * 初始化 Answer详情页的显示
     * @param answer
     */
    private void initAnswerView(ZHAnswer answer) {
        setSupportActionBar(mVu.getToolbar());
        getSupportActionBar().setTitle(answer.getQuestionTitle());

        mVoteups = answer.getVoteupCount();
        isVoteUpChecked = answer.isVoteUp();
        isVoteDownChecked = answer.isVoteDown();
        if (isVoteUpChecked) {
            mVoteups = mVoteups - 1;
        }

        mVu.setAuthorName(answer.getAuthor().getName());
        mVu.setAuthorProfile(answer.getAuthor().getHeadline());
        mVu.setQuestion(answer.getQuestionTitle());
        mVu.setVoteBtn(isVoteUpChecked, isVoteDownChecked, mVoteups);

        mVu.setNoHelpBtn(answer.isNoHelped());
        mVu.setThankBtn(answer.isThanked());
        mVu.setComment("评论 " + answer.getCommentCount());

        String authorAvatarUrl = answer.getAuthor().getAvatarUrl();
        mVu.setAvatar(authorAvatarUrl, mImageLoader);

        // 预先执行一次，以便计算 顶部区域 的高度
        mVu.setAnswer("");

        String content = answer.getContent();
        if (TextUtils.isEmpty(content)) {
            FetchAnswerTask fetchAnswerTask = new FetchAnswerTask(answer.getUrl());
            HttpUtils.executeTask(fetchAnswerTask);
        } else {
            mAnswerContent = answer.getContent();
            setAnswerContent();
        }
    }

    /**
     * 处理 【问题】标题点击事件：跳转至 Question详情页
     * @param answer
     */
    private void handleTitleClick(final ZHAnswer answer) {
        mVu.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionUrl = answer.getQUestionUrl();
                String questionTitle = answer.getQuestionTitle();
                FrameActivity.startBy(AnswerActivity.this, new ZHQuestion(questionUrl, questionTitle));
            }
        });
    }

    /**
     * 处理对 答案 的投票
     */
    private void handleVote() {
        mVu.setVoteOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoteDialogFragment dialogFragment = new VoteDialogFragment();
                Bundle args = new Bundle();
                args.putBoolean("isVoteUpChecked", isVoteUpChecked);
                args.putBoolean("isVoteDownChecked", isVoteDownChecked);
                dialogFragment.setArguments(args);
                dialogFragment.show(mFm, null);
            }
        });
    }

    /**
     * 顶、底部区域的 动画 ：
     * 1、向下滚动超过固定的高度： 隐藏顶部
     * 2、向上滚动超过固定的高度： 显示顶部
     * 3、向下滚动全过程：隐藏底部
     * 4、向上滚动全过程：显示底部
     * 5、滚动至底部时： 显示底部
     * 6、单击屏幕时，切换顶部、底部的显示状态
     */
    private void handleAnimation() {
        // 快速单击内容区域时，显示或隐藏顶部、底部区域
        mDetector = new GestureDetector(AnswerActivity.this,
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {

                        if (isTouchEnable) {
                            if (isTopHide) {

                                isTopHide = mVu.showTop();
                            } else {

                                isTopHide = mVu.hideTop();
                            }
                        }

                        if (isBottomHide) {

                            isBottomHide = mVu.showBottom();
                        } else {

                            isBottomHide = mVu.hideBottom();
                        }

                        return super.onSingleTapConfirmed(e);
                    }
                });

        //监听 内容 区域的手势事件：隐藏或显示顶部、底部区域
        mVu.setContentViewTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return false;
            }
        });

        // 监听 答案区域 的滚动事件
        mVu.setAnswerViewScrollListener(new ZHAnswerView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt, boolean isScrollToBottom) {

                // 往上滑超过预设的位置，则隐藏顶部区域
                if (t > OFFSET && !isTopHide) {
                    isTopHide = mVu.hideTop();
                    isTouchEnable = true;
                }

                // 往下滑超过预设的位置，则显示顶部区域
                if (t <= OFFSET && isTopHide) {
                    isTopHide = mVu.showTop();
                    isTouchEnable = false;
                }

                // 往上滑时，隐藏底部区域
                if (t > oldt && !isBottomHide) {

                    isBottomHide = mVu.hideBottom();
                }

                // 往下滑时，显示底部区域
                if (t < oldt && isBottomHide) {

                    isBottomHide = mVu.showBottom();
                }

                // 滑动到底部时，显示底部区域
                if (isScrollToBottom && isBottomHide) {
                    isBottomHide = mVu.showBottom();
                }
            }
        });
    }

    /**
     * 设置答案内容，并动态设置其内容的 padding-top 和 padding-bottom,
     * 使内容不会被顶、底部区域遮盖
     */
    private void setAnswerContent() {
        mVu.initWebContent(160, new Runnable() {
            @Override
            public void run() {
                int paddingTop = (int) (mVu.getTopHeight() / CURRENT_DENSITY);
                int paddingBottom = (int) (mVu.getBottomHeight() / CURRENT_DENSITY);
                String preDiv = "<div style=\"padding-bottom:" + paddingBottom + "px;padding-top:" + paddingTop + "px\">";

                mVu.setAnswer(HttpConstants.ANSWER_HTML_PRE + preDiv + mAnswerContent + HttpConstants.ANSWER_HTML_SUF);
            }
        });
    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
        mVolleyUtils.cancelAll();
    }

    public static void startBy(Context context, ZHAnswer answer) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(EXTRA_ANSWER, answer);
        context.startActivity(intent);
    }

    /**
     * 弹出对话框投票完毕后触发
     * @param event
     */
    public void onEvent(VoteEvent event) {
        isVoteDownChecked = event.isVoteDown();
        isVoteUpChecked = event.isVoteUp();

        mVu.setVoteBtn(event.isVoteUp(), event.isVoteDown(), mVoteups - 1);

    }

    /**
     * 通过网络获取 答案详情 成功后触发
     * @param event
     */
    public void onEvent(HttpResponseEvent event) {
        if (event instanceof FetchAnswerHRE) {
            ZHAnswer answer = ((FetchAnswerHRE) event).getAnswer();
            mAnswerContent = answer.getContent();

            mVu.setAvatar(answer.getAuthor().getAvatarUrl(), mImageLoader);
            setAnswerContent();
        }
    }
}
