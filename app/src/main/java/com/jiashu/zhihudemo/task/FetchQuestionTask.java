package com.jiashu.zhihudemo.task;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.events.http.FetchQuestionHRE;
import com.jiashu.zhihudemo.mode.ZHQuestion;
import com.jiashu.zhihudemo.net.ZHStringRequest;
import com.jiashu.zhihudemo.utils.LogUtils;
import com.jiashu.zhihudemo.utils.ParseUtils;

import java.util.Map;

/**
 * Created by Jiashu on 2015/5/22.
 */
public class FetchQuestionTask extends HttpTask {

    private final String TAG = getClass().getSimpleName();
    private ZHStringRequest mRequest;

    private String mUrl;

    public FetchQuestionTask(String url) {
        mUrl = url;
    }

    @Override
    public void execute() {
        mRequest = new ZHStringRequest(
                mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ZHQuestion question = ParseUtils.parseHtmlToQuestion(response);
                        mBus.post(new FetchQuestionHRE(question));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

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
