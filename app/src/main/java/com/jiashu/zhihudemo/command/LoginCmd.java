package com.jiashu.zhihudemo.command;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.events.LoginRE;
import com.jiashu.zhihudemo.events.OnLoginEvent;
import com.jiashu.zhihudemo.net.ZHStringRequest;
import com.jiashu.zhihudemo.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/5/3.
 * 模拟登录【知乎】
 */
public class LoginCmd extends Command {

    private final String TAG = getClass().getSimpleName();

    private static final String LOGIN_STATUS_KEY = "r";
    private static final String LOGIN_ERRCODE_KEY = "errcode";

    private static final String PARAM_XSRF = "_xsrf";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PSW = "password";
    private static final String PARAM_REMEMBER_ME = "rememberme";

    private ZHStringRequest mRequest;

    private String mEmail;
    private String mPwd;
    private String mCaptcha;

    public LoginCmd(OnLoginEvent event) {
        if (event != null) {
            mEmail = event.getEmail();
            mPwd = event.getPassword();
            mCaptcha = event.getCaptcha();
        } else {
            mEmail = "";
            mPwd = "";
            mCaptcha = "";
        }
    }

    @Override
    public void execute() {
        LogUtils.d(TAG, mCaptcha);

        mRequest = new ZHStringRequest(
                Request.Method.POST,
                HttpConstants.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtils.d(TAG, "Login fail : " + volleyError);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                LogUtils.d(TAG, "getParams()");
                LogUtils.d(TAG, Thread.currentThread().getName());
                Map<String, String> params = new HashMap<>();
                params.put(PARAM_XSRF, mXSRF);
                params.put(PARAM_EMAIL, mEmail);
                params.put(PARAM_PSW, mPwd);
                params.put(PARAM_REMEMBER_ME, "y");
                if (TextUtils.isEmpty(mCaptcha)) {
                    params.put("captcha", mCaptcha);
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LogUtils.d(TAG, "getHeaders()");
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                headers.put("Referer", "http://www.zhihu.com/#signin");
                headers.put("Host", "www.zhihu.com");
                headers.put("Accept", "*/*");
                return headers;
            }
        };
        try {
            LogUtils.d(TAG, mRequest.getHeaders().toString());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        mVolleyUtils.addRequest(mRequest);
    }

    private void handleResponse(String response) {
        LogUtils.d(TAG, "Login response" + response);
        try {
            JSONObject jsonObj = new JSONObject(response);
            int loginStatus = jsonObj.getInt(LOGIN_STATUS_KEY);
            switch (loginStatus) {
                case HttpConstants.LOGIN_SUCCESS:
                    EventBus.getDefault().post(new LoginRE(loginStatus));
                    break;
                case HttpConstants.LOGIN_FAIL:

                    JSONObject obj = new JSONObject(response);
                    int errcode = obj.getInt(LOGIN_ERRCODE_KEY);
                    EventBus.getDefault().post(new LoginRE(errcode));
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        mRequest.cancel();
    }


}
