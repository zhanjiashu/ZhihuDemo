package com.jiashu.zhihudemo.net;

import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.jiashu.zhihudemo.data.HttpConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jiashu on 2015/5/6.
 * 定制 Volley 的 ImageRequest
 */
public class ZHImageRequest extends ImageRequest {

    public ZHImageRequest(String url, Response.Listener<Bitmap> listener, int maxWidth, int maxHeight, Bitmap.Config decodeConfig, Response.ErrorListener errorListener) {
        super(url, listener, maxWidth, maxHeight, decodeConfig, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> header = new HashMap<String, String>();
        header.put("User-Agent", HttpConstants.UA);
        header.put("Accept", "image/webp,*/*;q=0.8");
        return header;
    }
}
