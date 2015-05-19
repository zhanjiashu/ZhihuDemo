package com.jiashu.zhihudemo.presenter.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.task.FetchAnswerTask;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.events.VoteEvent;
import com.jiashu.zhihudemo.events.http.FetchAnswerHRE;
import com.jiashu.zhihudemo.events.http.HttpResponseEvent;
import com.jiashu.zhihudemo.mode.ZHFeed;

import com.jiashu.zhihudemo.net.ZHStringRequest;
import com.jiashu.zhihudemo.other.ZHAnswerView;
import com.jiashu.zhihudemo.presenter.fragment.dialog.VoteDialogFragment;
import com.jiashu.zhihudemo.utils.HttpUtils;
import com.jiashu.zhihudemo.utils.LogUtils;

import com.jiashu.zhihudemo.vu.AnswerVu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Jiashu on 2015/5/3.
 * 【回答】 的 内容详情页面
 */
public class AnswerActivity extends BasePresenterActivity<AnswerVu> {

    private static final String TAG = "AnswerActivity";

    private static final int OFFSET = 220;

    private static final float CURRENT_DENSITY = ZHApp.getContext().getResources().getDisplayMetrics().density;

    private static final String EXTRA_QUEStTION = "question";
    private static final String EXTRA_AUTHOR_NAME = "name";
    private static final String EXTRA_AUTHOR_PROFILE = "profile";
    private static final String EXTRA_AUTHOR_URL = "url";
    private static final String EXTRA_VOTEUPS = "voteups";
    private static final String EXTRA_COMMENTS = "comments";
    private static final String EXTRA_ANSWER_URL = "contentUrl";
    private static final String EXTRA_NO_HELP = "isNoHelped";
    private static final String EXTRA_THANKS = "isThanked";

    private ImageLoader mImageLoader;
    private GestureDetector mDetector;

    private boolean isTopHide;
    private boolean isBottomHide;
    private boolean isTouchEnable;

    private String mAnswer;
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

        setSupportActionBar(mVu.getToolbar());

        Intent intent = getIntent();
        String question = intent.getStringExtra(EXTRA_QUEStTION);
        String authorName = intent.getStringExtra(EXTRA_AUTHOR_NAME);
        String authorProfile = intent.getStringExtra(EXTRA_AUTHOR_PROFILE);
        String authorUrl = intent.getStringExtra(EXTRA_AUTHOR_URL);
        mVoteups = intent.getIntExtra(EXTRA_VOTEUPS, 0);
        int comments = intent.getIntExtra(EXTRA_COMMENTS, 0);
        String answerUrl = intent.getStringExtra(EXTRA_ANSWER_URL);
        boolean isThanked = intent.getBooleanExtra(EXTRA_THANKS, false);
        boolean isNoHelped = intent.getBooleanExtra(EXTRA_NO_HELP, false);

        LogUtils.d(TAG, "isThanked = " + isThanked);
        LogUtils.d(TAG, "isNoHelped= " + isNoHelped);
        LogUtils.d(TAG, "answerUrl = " + answerUrl);

        getSupportActionBar().setTitle(question);

        mVu.setAuthorName(authorName);
        mVu.setAuthorProfile(authorProfile);
        mVu.setQuestion(question);
        mVu.setVoteBtn(false, false, mVoteups);

        mVu.setNoHelpBtn(isNoHelped);
        mVu.setThankBtn(isThanked);
        mVu.setComment("评论 " + comments);

        FetchAnswerTask fetchAnswerTask = new FetchAnswerTask(answerUrl);
        HttpUtils.executeTask(fetchAnswerTask);


        // 预先执行一次，以便计算 顶部区域 的高度
        mVu.setAnswer("");

        // 获取答案作者的头像url
        fetchAuthorImg(authorUrl);

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

    private void fetchAuthorImg(String url) {
        ZHStringRequest request = new ZHStringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        mBus.post(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtils.d(TAG, volleyError.toString());
                    }
                }
        );
        mVolleyUtils.addRequest(request);
    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
        mVolleyUtils.cancelAll();
    }

    public static void startBy(Context context, ZHFeed feed) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(EXTRA_QUEStTION, feed.getTitle());
        intent.putExtra(EXTRA_AUTHOR_NAME, feed.getAuthorName());
        intent.putExtra(EXTRA_AUTHOR_PROFILE, feed.getAuthorHeadline());
        intent.putExtra(EXTRA_VOTEUPS, feed.getVoteups());
        intent.putExtra(EXTRA_AUTHOR_URL, feed.getAuthorUrl());
        intent.putExtra(EXTRA_COMMENTS, feed.getComments());
        intent.putExtra(EXTRA_ANSWER_URL, feed.getContentUrl());
        intent.putExtra(EXTRA_NO_HELP, feed.isNohelped());
        intent.putExtra(EXTRA_THANKS, feed.isThanked());
        context.startActivity(intent);
    }

    public void onEventMainThread(String response) {
        Document doc = Jsoup.parse(response);
        // 获取答题者的头像url
        String avatarUrl = doc.select("div[class=zm-profile-header-avatar-container]>img").attr("src");

        mVu.setAvatar(avatarUrl, mImageLoader);
        LogUtils.d(TAG, "avatarUrl = " + avatarUrl);
    }

    public void onEvent(VoteEvent event) {
        isVoteDownChecked = event.isVoteDown();
        isVoteUpChecked = event.isVoteUp();

        if (isVoteUpChecked) {
            mVu.setVoteBtn(event.isVoteUp(), event.isVoteDown(), mVoteups + 1);
        } else {
            mVu.setVoteBtn(event.isVoteUp(), event.isVoteDown(), mVoteups);
        }
    }

    public void onEvent(HttpResponseEvent event) {
        if (event instanceof FetchAnswerHRE) {
            mAnswer = ((FetchAnswerHRE) event).getAnswerContent();

            // 设置答案内容，并动态设置其内容的 padding-top 和 padding-bottom, 使内容不会被顶、底部区域遮盖
            mVu.initWebContent(160, new Runnable() {
                @Override
                public void run() {
                    int paddingTop = (int) (mVu.getTopHeight() / CURRENT_DENSITY);
                    int paddingBottom = (int) (mVu.getBottomHeight() / CURRENT_DENSITY);
                    String preDiv = "<div style=\"padding-bottom:" + paddingBottom + "px;padding-top:" + paddingTop + "px\">";

                    mVu.setAnswer(HttpConstants.ANSWER_HTML_PRE + preDiv + mAnswer + HttpConstants.ANSWER_HTML_SUF);
                }
            });
        }
    }

}
