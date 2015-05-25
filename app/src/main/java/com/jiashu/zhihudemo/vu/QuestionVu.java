package com.jiashu.zhihudemo.vu;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.presenter.adapter.SimpleBaseAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/5/22.
 */
public class QuestionVu extends Vu {

    View mView;
    View mHeaderView;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.lv_answers)
    ListView mAnswerListView;

    TextView mTitleView;

    TextView mDescriptionView;

    Button mFollowCountBtn;

    Button mCommentCountBtn;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_question, null);

        ButterKnife.inject(this, mView);

        mHeaderView = inflater.inflate(R.layout.list_head_question, null);

        mAnswerListView.addHeaderView(mHeaderView);

        mTitleView = (TextView) mHeaderView.findViewById(R.id.tv_title);
        mDescriptionView = (TextView) mHeaderView.findViewById(R.id.tv_description);

        mFollowCountBtn = (Button) mHeaderView.findViewById(R.id.btn_follower_count);
        mCommentCountBtn = (Button) mHeaderView.findViewById(R.id.btn_comment_count);

    }

    @Override
    public View getView() {
        return mView;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setDescription(Spanned spanned) {
        if (spanned != null) {
            mDescriptionView.setVisibility(View.VISIBLE);
            mDescriptionView.setText(spanned);
            mDescriptionView.setLinkTextColor(Color.BLUE);
            mDescriptionView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public void setAnswerAdapter(SimpleBaseAdapter adapter) {
        mAnswerListView.setAdapter(adapter);
    }

    public void setOnAnswerItemClickListener(AdapterView.OnItemClickListener listener) {
        mAnswerListView.setOnItemClickListener(listener);
    }

    public void setFollowCount(int count) {
        mFollowCountBtn.setText(count + "");

        if (count > 1000) {
            float countFloat = (float)(count / 100) / 10;
            mFollowCountBtn.setText(countFloat+"K");
        } else {
            mFollowCountBtn.setText(count+"");
        }
    }

    public void setCommentCount(int count) {
        mCommentCountBtn.setText(count+"");
    }
}
