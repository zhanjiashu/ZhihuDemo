package com.jiashu.zhihudemo.task;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.events.FetchFailEvent;
import com.jiashu.zhihudemo.events.http.FetchFeedListHRE;
import com.jiashu.zhihudemo.mode.ZHFeed;
import com.jiashu.zhihudemo.net.ZHStringRequest;
import com.jiashu.zhihudemo.utils.LogUtils;
import com.jiashu.zhihudemo.utils.ParseUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Jiashu on 2015/5/3.
 * 获取【知乎】首页的内容
 */
public class FetchFeedListTask extends HttpTask {

    private final String TAG = getClass().getSimpleName();

    private ZHStringRequest mRequest;

    @Override
    public void execute() {
        mRequest = new ZHStringRequest(
                HttpConstants.HOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<ZHFeed> feedList = ParseUtils.parseHtmlToFeedList(response);
                        mBus.post(new FetchFeedListHRE(feedList));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mBus.post(new FetchFailEvent());
                        LogUtils.d(TAG, volleyError.toString());
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
        mVolleyUtils.addRequest(mRequest);
    }

    @Override
    public void cancel() {
        mRequest.cancel();
    }
}
