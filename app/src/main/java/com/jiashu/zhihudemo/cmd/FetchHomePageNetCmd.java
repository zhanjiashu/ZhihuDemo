package com.jiashu.zhihudemo.cmd;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.event.FetchCompletedEvent;
import com.jiashu.zhihudemo.event.FetchFailEvent;
import com.jiashu.zhihudemo.mode.ZhiHuFeed;
import com.jiashu.zhihudemo.net.ZhiHuStringRequest;
import com.jiashu.zhihudemo.utils.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Jiashu on 2015/5/3.
 */
public class FetchHomePageNetCmd extends NetCmd {

    private final String TAG = getClass().getSimpleName();

    private ZhiHuStringRequest mRequest;
    private CallbackListener mListener;

    @Override
    public void execute() {
        mRequest = new ZhiHuStringRequest(
                HttpConstants.HOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //mListener.callback(response);
                        LogUtil.d(TAG, response);
                        // 获取首页数据成功发布事件
                        mBus.post(new FetchCompletedEvent(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mBus.post(new FetchFailEvent());
                        LogUtil.d(TAG, volleyError.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                headers.put("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                headers.put("Referer", "http://www.zhihu.com/");
                headers.put("Host", "www.zhihu.com");
                return headers;
            }
        };
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
        void callback(List<ZhiHuFeed> feedList);
    }
}
