package com.jiashu.zhihudemo.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.jiashu.zhihudemo.utils.LogUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by Jiashu on 2015/5/3.
 */
public class ZhiHuStringRequest extends StringRequest {

    public static final String UA = "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
    private final String TAG = getClass().getSimpleName();

    private final Response.Listener<String> mListener;

    public ZhiHuStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        mListener = listener;
    }

    public ZhiHuStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", UA);
        headers.put("Accept-Encoding", "gzip");
        return headers;
    }

    /**
     * 重写 parseNetworkResponse()
     * 当返回的 Response 的 Content-Encoding 为 gzip 时，利用 GZIPInputStream 进行 gzip 解码
     * @param response
     * @return
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        boolean isGZIP = checkGZIP(response.data);
        if (isGZIP) {
            String str = decodeGZIP(response.data);
            return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
        } else {
            return super.parseNetworkResponse(response);
        }
    }

    /**
     * 对 gzip 格式的 response 数据进行解码
     * @param data
     * @return
     */
    private String decodeGZIP(byte[] data) {
        InputStream is;
        StringBuilder sb = new StringBuilder();

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            is = new GZIPInputStream(bis);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is), 1000);
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 判断当前 response 是否为 gzip 格式的
     * @param data
     * @return
     */
    private boolean checkGZIP(byte[] data) {
        byte[] header = new byte[2];
        header[0] = data[0];
        header[1] = data[1];
        if (getShort(header) == 0x1f8b) {
            return true;
        }
        return false;
    }

    /**
     * 将 byte[] 转换成 字符
     * @param bytes
     * @return
     */
    private int getShort(byte[] bytes) {
        return (int) ((bytes[0] << 8) | bytes[1] & 0xFF);
    }
}
