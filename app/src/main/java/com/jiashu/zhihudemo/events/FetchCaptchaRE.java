package com.jiashu.zhihudemo.events;

import android.graphics.Bitmap;

/**
 * Created by Jiashu on 2015/5/6.
 * 获取 验证码 成功事件
 */
public class FetchCaptchaRE {
    public Bitmap mCaptcha;

    public FetchCaptchaRE(Bitmap captcha) {
        mCaptcha = captcha;
    }
}
