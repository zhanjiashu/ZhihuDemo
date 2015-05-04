package com.jiashu.zhihudemo.fragment;

import android.content.Context;

import com.jiashu.zhihudemo.cmd.FetchHomePageNetCmd;
import com.jiashu.zhihudemo.utils.HtmlUtils;
import com.jiashu.zhihudemo.utils.LogUtil;
import com.jiashu.zhihudemo.utils.NetUtil;
import com.jiashu.zhihudemo.vu.NormalListVu;
import com.jiashu.zhihudemo.data.Constants;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * A simple {@link BasePresenterFragment} subclass.
 */
public class HomeFragment extends BasePresenterFragment<NormalListVu> {

    private final String TAG = getClass().getSimpleName();
    public static final int INDEX = 0;

    @Override
    protected void onBindVu() {
        mVu.setText(Constants.NAV_NAMES[INDEX]);
        LogUtil.d(TAG, "HomeFragment");
        FetchHomePageNetCmd netCmd = new FetchHomePageNetCmd();
        netCmd.setOnNetCmdCallback(new FetchHomePageNetCmd.CallbackListener() {
            @Override
            public void callback(String response) {
                LogUtil.d(TAG, "Fetch home page success!");
                HtmlUtils.parseContentItem(response);
            }
        });
        NetUtil.execNetCmd(netCmd);

    }

    @Override
    protected void onDestroyVu() {

    }

    @Override
    protected Class<NormalListVu> getVuClass() {
        return NormalListVu.class;
    }
}
