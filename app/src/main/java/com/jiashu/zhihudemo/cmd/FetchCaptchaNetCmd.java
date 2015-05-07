package com.jiashu.zhihudemo.cmd;

import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.event.FetchCaptchaEvent;
import com.jiashu.zhihudemo.net.ZHImageRequest;
import com.jiashu.zhihudemo.net.ZhiHuStringRequest;
import com.jiashu.zhihudemo.utils.HttpUtil;
import com.jiashu.zhihudemo.utils.LogUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/5/3.
 */
public class FetchCaptchaNetCmd extends NetCmd {

    private final String TAG = getClass().getSimpleName();

    public CallbackListener mListener;

    public ZHImageRequest mRequest;

    private long mCurrentTime;
    private String mImgUrl;

    public FetchCaptchaNetCmd() {
        mCurrentTime = System.currentTimeMillis();
        mImgUrl = HttpConstants.CAPTCHA_URL + "?r=" + mCurrentTime;
    }

    @Override
    public void execute() {
        mRequest = new ZHImageRequest(mImgUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                //mListener.callback(bitmap);
                EventBus.getDefault().post(new FetchCaptchaEvent(bitmap));
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.d(TAG, "fetch the captcha img faild");
            }
        });
        mVolleyUtil.addRequest(mRequest);
    }

    @Override
    public void cancel() {
        mRequest.cancel();
    }

    @Override
    public <T extends NetCmdCallback> void setOnNetCmdCallback(T callback) {
        mListener = (CallbackListener) callback;
    }

    public interface CallbackListener extends NetCmdCallback {
        void callback(Bitmap img);
    }
}
