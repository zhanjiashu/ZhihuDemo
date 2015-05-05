package com.jiashu.zhihudemo.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

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


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        boolean isGzipResponse = checkContentEncoding(response.data);
        if (isGzipResponse) {
            String str = decodeGzip(response.data);
            return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
        } else {
            return super.parseNetworkResponse(response);
        }
    }

    private String decodeGzip(byte[] data) {
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

    private boolean checkContentEncoding(byte[] data) {
        byte[] header = new byte[2];
        header[0] = data[0];
        header[1] = data[1];
        if (getShort(header) == 0x1f8b) {
            return true;
        }
        return false;
    }

    private int getShort(byte[] bytes) {
        return (int) ((bytes[0] << 8) | bytes[1] & 0xFF);
    }
}
