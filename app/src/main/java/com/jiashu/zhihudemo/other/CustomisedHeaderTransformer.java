package com.jiashu.zhihudemo.other;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jiashu.zhihudemo.R;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import uk.co.senab.actionbarpulltorefresh.library.HeaderTransformer;

/**
 * Created by Jiashu on 2015/4/24.
 */
public class CustomisedHeaderTransformer extends HeaderTransformer {

    private static final String TAG = "Customised";
    private View mHeaderView;
    private TextView mHintTextView;
    private SmoothProgressBar mProgressBar;

    private boolean isStarted = false;

    @Override
    public void onViewCreated(Activity activity, View headerView) {
        mHeaderView = headerView;
        mHintTextView = (TextView) headerView.findViewById(R.id.tv_ptr);
        mProgressBar = (SmoothProgressBar) headerView.findViewById(R.id.spb_ptr);
        Log.d(TAG, "Created");
    }

    @Override
    public void onReset() {
        mHintTextView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.pull_to_refresh_pull_label);

        mProgressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "Reset");
    }

    @Override
    public void onPulled(float percentagePulled) {
        if (!isStarted) {
            isStarted = true;
            mHintTextView.setText(R.string.pull_to_refresh_pull_label);
            mProgressBar.setSmoothProgressDrawableSectionsCount(1);
            mProgressBar.setSmoothProgressDrawableReversed(true);
            mProgressBar.setSmoothProgressDrawableMirrorMode(true);
            Log.d(TAG, "onPulled");
        }
    }

    @Override
    public void onRefreshStarted() {
        mHintTextView.setText(R.string.pull_to_refresh_refreshing_label);
        mProgressBar.setSmoothProgressDrawableSectionsCount(3);
        mProgressBar.setSmoothProgressDrawableReversed(false);
        mProgressBar.setSmoothProgressDrawableMirrorMode(false);
        Log.d(TAG, "Started");
        isStarted = false;
    }

    @Override
    public void onReleaseToRefresh() {
        mHintTextView.setText(R.string.pull_to_refresh_release_label);
        Log.d(TAG, "Release");
    }

    @Override
    public boolean showHeaderView() {
        final boolean isHiding = mHeaderView.getVisibility() != View.VISIBLE;
        if (isHiding) {
            mHeaderView.setVisibility(View.VISIBLE);
        }
        return isHiding;
    }

    @Override
    public boolean hideHeaderView() {
        final boolean isShowing = mHeaderView.getVisibility() == View.VISIBLE;
        if (isShowing) {
            mHeaderView.setVisibility(View.GONE);
        }
        return isShowing;
    }
}
