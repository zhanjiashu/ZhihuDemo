package com.jiashu.zhihudemo.task;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.events.FetchLoadingRE;

import com.jiashu.zhihudemo.net.ZHStringRequest;

import com.jiashu.zhihudemo.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jiashu on 2015/5/7.
 * 获取【知乎】首页下拉加载的数据
 */
public class FetchLoadingTask extends HttpTask {

    private final String TAG = getClass().getSimpleName();
    private static final int LOAD_SUCCESS = 0;

    private String mJSONParams;
    private String mMethod;
    private String mUrl;
    private ZHStringRequest mRequest;

    public FetchLoadingTask(String url, long blockId, int offset) {
        mUrl = url;

        mJSONParams = formatTheParams(blockId, offset);

        mMethod = "next";
    }

    public FetchLoadingTask(String url, String feedId, int offset) {
        mUrl = url;

        mJSONParams = formatTheParams(feedId, offset);

        mMethod = "next";
    }

    @Override
    public void execute() {
        LogUtils.d(TAG, "url = " + mUrl);
        LogUtils.d(TAG, "params = " + mJSONParams);
        mRequest = new ZHStringRequest(
                Request.Method.POST,
                mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            LogUtils.d(TAG, "r = " + obj.getInt("r"));
                            if (obj.getInt("r") == LOAD_SUCCESS) {
                                JSONArray array = obj.getJSONArray("msg");

                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < array.length(); i++) {
                                    sb.append(array.getString(i));
                                }

                                mBus.post(new FetchLoadingRE(sb.toString()));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtils.d(TAG, "load failed : " + volleyError);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("params", mJSONParams);
                params.put("method", mMethod);
                params.put("_xsrf", mXSRF);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                headers.put("Referer", "http://www.zhihu.com/");
                headers.put("Host", "www.zhihu.com");
                headers.put("Accept", "*/*");
                return super.getHeaders();
            }
        };
        mVolleyUtils.addRequest(mRequest);
    }

    @Override
    public void cancel() {

    }

    private String formatTheParams(String lastFeedID, int offset) {
        JSONStringer jsonStringer = new JSONStringer();

        try {
            jsonStringer.object();
            jsonStringer.key("offset");
            jsonStringer.value(offset);
            jsonStringer.key("start");
            jsonStringer.value(lastFeedID);
            jsonStringer.endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonStringer.toString();
    }

    private String formatTheParams(long lastFeedID, int offset) {
        JSONStringer jsonStringer = new JSONStringer();

        try {
            jsonStringer.object();
            jsonStringer.key("action");
            jsonStringer.value("next");
            jsonStringer.key("block_id");
            jsonStringer.value(lastFeedID);
            jsonStringer.key("offset");
            jsonStringer.value(offset);
            jsonStringer.endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonStringer.toString();
    }
}
