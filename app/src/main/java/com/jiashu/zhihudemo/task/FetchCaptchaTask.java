package com.jiashu.zhihudemo.task;

import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.events.FetchCaptchaRE;
import com.jiashu.zhihudemo.net.ZHImageRequest;
import com.jiashu.zhihudemo.utils.LogUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/5/3.
 * 获取【知乎】登录验证码
 */
public class FetchCaptchaTask extends HttpTask {

    private final String TAG = getClass().getSimpleName();

    public ZHImageRequest mRequest;

    private long mCurrentTime;
    private String mImgUrl;

    public FetchCaptchaTask() {
        mCurrentTime = System.currentTimeMillis();
        mImgUrl = HttpConstants.CAPTCHA_URL + "?r=" + mCurrentTime;
    }

    @Override
    public void execute() {
        mRequest = new ZHImageRequest(mImgUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                //mListener.callback(bitmap);
                EventBus.getDefault().post(new FetchCaptchaRE(bitmap));
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.d(TAG, "fetch the captcha img faild");
            }
        });
        mVolleyUtils.addRequest(mRequest);
    }

    @Override
    public void cancel() {
        mRequest.cancel();
    }

}
