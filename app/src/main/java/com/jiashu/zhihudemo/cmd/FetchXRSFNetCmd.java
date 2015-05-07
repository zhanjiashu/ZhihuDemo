package com.jiashu.zhihudemo.cmd;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.net.ZhiHuStringRequest;
import com.jiashu.zhihudemo.utils.LogUtil;
import com.jiashu.zhihudemo.utils.HttpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Jiashu on 2015/5/3.
 */
public class FetchXRSFNetCmd extends NetCmd {

    private final String TAG = getClass().getSimpleName();

    public CallbackListener mListener;

    public ZhiHuStringRequest mRequest;


    @Override
    public void execute() {
        mRequest = new ZhiHuStringRequest(
                HttpConstants.HOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document doc = Jsoup.parse(response);
                        mXSRF = doc.select("input[name=_xsrf]").attr("value");
                        LogUtil.d(TAG, "_xsrf = " + mXSRF);
                        HttpUtil.setXSRF(mXSRF);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtil.d(TAG, "fetch _xsrf fail : " + volleyError);
                    }
                }
        );
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
        void callback(String xsrf);
    }
}
