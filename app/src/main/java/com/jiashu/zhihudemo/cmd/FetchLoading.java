package com.jiashu.zhihudemo.cmd;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.event.LoadingEvent;

import com.jiashu.zhihudemo.net.ZhiHuStringRequest;

import com.jiashu.zhihudemo.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jiashu on 2015/5/7.
 */
public class FetchLoading extends NetCmd {

    private final String TAG = getClass().getSimpleName();
    private static final int LOAD_SUCCESS = 0;

    private String mJSONParams;
    private String mMethod;
    private ZhiHuStringRequest mRequest;

    public FetchLoading(String lastFeedID) {

        mJSONParams = formatTheParams(lastFeedID);
        mMethod = "next";
    }

    @Override
    public void execute() {
        mRequest = new ZhiHuStringRequest(
                Request.Method.POST,
                HttpConstants.LOADING_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            LogUtil.d(TAG, "r = " + obj.getInt("r"));
                            if (obj.getInt("r") == LOAD_SUCCESS) {
                                JSONArray array = obj.getJSONArray("msg");

                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < array.length(); i++) {
                                    sb.append(array.getString(i));
                                }

                                mBus.post(new LoadingEvent(sb.toString()));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtil.d(TAG, "load failed : " + volleyError);
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
        mVolleyUtil.addRequest(mRequest);
    }

    @Override
    public void cancel() {

    }

    @Override
    public <T extends NetCmdCallback> void setOnNetCmdCallback(T callback) {

    }

    private String formatTheParams(String lastFeedID) {
        JSONStringer jsonStringer = new JSONStringer();

        try {
            jsonStringer.object();
            jsonStringer.key("offset");
            jsonStringer.value(21);
            jsonStringer.key("start");
            jsonStringer.value(lastFeedID);
            jsonStringer.endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonStringer.toString();
    }
}
