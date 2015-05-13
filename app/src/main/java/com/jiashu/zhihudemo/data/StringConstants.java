package com.jiashu.zhihudemo.data;

import android.content.res.Resources;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.app.ZHApp;

/**
 * Created by Jiashu on 2015/4/30.
 */
public class StringConstants {
    private static Resources mRes = ZHApp.getContext().getResources();

    public static final int[] TIPS = {
            R.string.guide_tip_first,
            R.string.guide_tip_second,
            R.string.guide_tip_third,
            R.string.guide_tip_fourth,
            R.string.guide_tip_fifth,
            R.string.general_null
    };

    public static final String TOAST_CHECK_NETWORK = mRes.getString(R.string.toast_check_network);
}
