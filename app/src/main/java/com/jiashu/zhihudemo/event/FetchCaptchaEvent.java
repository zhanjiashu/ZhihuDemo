package com.jiashu.zhihudemo.event;

import android.graphics.Bitmap;

import com.jiashu.zhihudemo.cmd.FetchCaptchaNetCmd;

/**
 * Created by Jiashu on 2015/5/6.
 */
public class FetchCaptchaEvent {
    public Bitmap mCaptcha;

    public FetchCaptchaEvent(Bitmap captcha) {
        mCaptcha = captcha;
    }
}
