package com.jiashu.zhihudemo.cmd;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.net.ZHStringRequest;
import com.jiashu.zhihudemo.utils.HttpUtils;
import com.jiashu.zhihudemo.utils.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Jiashu on 2015/5/3.
 * 获取 【知乎】的 _xrsf 参数，该参数用于模拟登录、加载数据等网络交互中。
 */
public class FetchXrsfCmd extends Command {

    private final String TAG = getClass().getSimpleName();

    public ZHStringRequest mRequest;


    @Override
    public void execute() {
        mRequest = new ZHStringRequest(
                HttpConstants.HOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document doc = Jsoup.parse(response);
                        mXSRF = doc.select("input[name=_xsrf]").attr("value");
                        LogUtils.d(TAG, "_xsrf = " + mXSRF);
                        HttpUtils.setXSRF(mXSRF);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtils.d(TAG, "fetch _xsrf fail : " + volleyError);
                    }
                }
        );
        mVolleyUtils.addRequest(mRequest);
    }

    @Override
    public void cancel() {
        mRequest.cancel();
    }
}
