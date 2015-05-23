package com.jiashu.zhihudemo.presenter.fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.jiashu.zhihudemo.events.http.FetchQuestionHRE;
import com.jiashu.zhihudemo.events.http.HttpResponseEvent;
import com.jiashu.zhihudemo.mode.ZHAnswer;
import com.jiashu.zhihudemo.mode.ZHQuestion;
import com.jiashu.zhihudemo.presenter.activity.AnswerActivity;
import com.jiashu.zhihudemo.presenter.activity.FrameActivity;
import com.jiashu.zhihudemo.presenter.adapter.ZHAnswerAdapter;
import com.jiashu.zhihudemo.task.FetchQuestionTask;

import com.jiashu.zhihudemo.utils.HttpUtils;
import com.jiashu.zhihudemo.utils.ToastUtils;
import com.jiashu.zhihudemo.vu.QuestionVu;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiashu on 2015/5/22.
 */
public class QuestionFragment extends BasePresenterFragment<QuestionVu> {

    private final String TAG = getClass().getSimpleName();

    private ActionBar mActionBar;

    private ZHAnswerAdapter mAdapter;
    private List<ZHAnswer> mAnswerList;

    @Override
    protected void onBindVu() {

        mBus.register(this);

        ZHQuestion question = getArguments().getParcelable(FrameActivity.EXTRA_PARAM);

        final ActionBarActivity activity = (ActionBarActivity) getActivity();
        activity.setSupportActionBar(mVu.getToolbar());

        mActionBar = activity.getSupportActionBar();

        mActionBar.setTitle(formatActionBarTitle(0));
        mVu.setTitle(question.getTitle());

        mAnswerList = new ArrayList<>();
        mAdapter = new ZHAnswerAdapter(mAnswerList);
        mVu.setAnswerAdapter(mAdapter);

        mVu.setOnAnswerItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AnswerActivity.startBy(getActivity(), mAnswerList.get(position - 1));
            }
        });

        FetchQuestionTask task = new FetchQuestionTask(question.getUrl());
        HttpUtils.executeTask(task);
    }

    private String formatActionBarTitle(int count) {
        String format = "共 {0} 回答";
        return MessageFormat.format(format, count);
    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
    }

    @Override
    protected Class<QuestionVu> getVuClass() {
        return QuestionVu.class;
    }

    private void showQuestionDetail(ZHQuestion question) {
        mActionBar.setTitle(formatActionBarTitle(question.getAnswerCount()));
        String description = question.getDescription();
        if (!TextUtils.isEmpty(description)) {
            mVu.setDescription(Html.fromHtml(description));
        }

        mAdapter.replace(question.getAnswers());
    }

    public void onEvent(HttpResponseEvent event) {
        if (event instanceof FetchQuestionHRE) {
            ZHQuestion question = (ZHQuestion) event.data;

            showQuestionDetail(question);
        }
    }

}
