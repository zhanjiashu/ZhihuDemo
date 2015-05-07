package com.jiashu.zhihudemo.cmd;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.event.LoginEvent;
import com.jiashu.zhihudemo.event.UserBean;
import com.jiashu.zhihudemo.net.ZhiHuStringRequest;
import com.jiashu.zhihudemo.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/5/3.
 */
public class LoginNetCmd extends NetCmd {

    private final String TAG = getClass().getSimpleName();

    private static final String LOGIN_STATUS_KEY = "r";
    private static final String LOGIN_ERRCODE_KEY = "errcode";

    private static final String PARAM_XSRF = "_xsrf";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PSW = "password";
    private static final String PARAM_REMEMBER_ME = "rememberme";

    private ZhiHuStringRequest mRequest;

    private String mEmail;
    private String mPwd;
    private String mCode;

    public LoginNetCmd(UserBean event) {
        if (event != null) {
            mEmail = event.getEmail();
            mPwd = event.getPassword();
            mCode = event.getCode();
        } else {
            mEmail = "";
            mPwd = "";
            mCode = "";
        }
    }

    @Override
    public void execute() {
        LogUtil.d(TAG, "execute");
        LogUtil.d(TAG, mCode);

        mRequest = new ZhiHuStringRequest(
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
                        LogUtil.d(TAG, "Login fail : " + volleyError);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                LogUtil.d(TAG, "getParams()");
                LogUtil.d(TAG, Thread.currentThread().getName());
                Map<String, String> params = new HashMap<>();
                params.put(PARAM_XSRF, mXSRF);
                params.put(PARAM_EMAIL, mEmail);
                params.put(PARAM_PSW, mPwd);
                params.put(PARAM_REMEMBER_ME, "y");
                if (TextUtils.isEmpty(mCode)) {
                    params.put("captcha", mCode);
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LogUtil.d(TAG, "getHeaders()");
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                headers.put("Referer", "http://www.zhihu.com/#signin");
                headers.put("Host", "www.zhihu.com");
                headers.put("Accept", "*/*");
                return headers;
            }
        };
        try {
            LogUtil.d(TAG, mRequest.getHeaders().toString());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        mVolleyUtil.addRequest(mRequest);
    }

    private void handleResponse(String response) {
        LogUtil.d(TAG, "Login response" + response);
        try {
            JSONObject jsonObj = new JSONObject(response);
            int loginStatus = jsonObj.getInt(LOGIN_STATUS_KEY);
            switch (loginStatus) {
                case HttpConstants.LOGIN_SUCCESS:
                    EventBus.getDefault().post(new LoginEvent(loginStatus));
                    break;
                case HttpConstants.LOGIN_FAIL:

                    JSONObject obj = new JSONObject(response);
                    int errcode = obj.getInt(LOGIN_ERRCODE_KEY);
                    EventBus.getDefault().post(new LoginEvent(errcode));
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

    @Override
    public <T extends NetCmdCallback> void setOnNetCmdCallback(T callback) {

    }

}
