package com.jiashu.zhihudemo.data;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.ZhiHuApp;

/**
 * Created by Jiashu on 2015/5/3.
 */
public class NetConstants {
    public static final String HOST = "http://www.zhihu.com";
    public static final String LOGIN_URL = HOST + "/login";
    public static final String CAPTCHA_URL = HOST + "/captcha.gif";

    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_FAIL = 1;

    public static final int ERRCODE_PWD_EMAIL_ERROR = 4038;
    public static final int ERRCODE_EMAIL_FORMAT_ERROR = 4003;
    public static final int ERRCODE_PWD_LENGTH_ERROR = 4000;
    public static final int ERRCODE_INPUT_CAPTCHA = 269;

    public static final String ERRMSG_PWD_EMAIL_ERROR = getString(R.string.toast_login_error_default);
    public static final String ERRMSG_EMAIL_FORMAT_ERROR = getString(R.string.toast_login_error_email);
    public static final String ERRMSG_PWD_LENGTH_ERROR = getString(R.string.toast_login_error_pwd);
    public static final String ERRMSG__INPUT_CAPTCHA = getString(R.string.toast_login_error_captcha);

    public static String getString(int resId) {
        return ZhiHuApp.getContext().getResources().getString(resId);
    }
}
