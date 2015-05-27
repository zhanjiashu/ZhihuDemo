package com.jiashu.zhihudemo.events.http;

import android.graphics.Bitmap;

/**
 * Created by Jiashu on 2015/5/27.
 */
public class FetchCaptchaHRE extends HttpResponseEvent<Bitmap> {
    public FetchCaptchaHRE(Bitmap bitmap) {
        super.data = bitmap;
    }
}
