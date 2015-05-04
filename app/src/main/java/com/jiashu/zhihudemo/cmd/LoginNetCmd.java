package com.jiashu.zhihudemo.cmd;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jiashu.zhihudemo.data.NetConstants;
import com.jiashu.zhihudemo.event.LoginEvent;
import com.jiashu.zhihudemo.net.ZhiHuStringRequest;
import com.jiashu.zhihudemo.utils.LogUtil;
import com.jiashu.zhihudemo.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public static final int LOGIN_SUCCESS = 0;

    private CallbackListener mListener;

    private ZhiHuStringRequest mRequest;

    private String mEmail;
    private String mPwd;


    public LoginNetCmd(LoginEvent event) {
        if (event != null) {
            mEmail = event.getEmail();
            mPwd = event.getPassword();
        } else {
            mEmail = "";
            mPwd = "";
        }
    }

    @Override
    public void execute() {
        LogUtil.d(TAG, "execute");
        mRequest = new ZhiHuStringRequest(
                Request.Method.POST,
                NetConstants.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtil.d(TAG, "Login response" + response);
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            int loginStatus = jsonObj.getInt(LOGIN_STATUS_KEY);
                            switch (loginStatus) {
                                case NetConstants.LOGIN_SUCCESS:
                                    mListener.callback(loginStatus);
                                    break;
                                case NetConstants.LOGIN_FAIL:
                                    handleFail(response);
                                    break;
                                default:
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

                Map<String, String> params = new HashMap<>();
                params.put(PARAM_XSRF, mXSRF);
                params.put(PARAM_EMAIL, mEmail);
                params.put(PARAM_PSW, mPwd);
                params.put(PARAM_REMEMBER_ME, "y");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                headers.put("Referer", "http://www.zhihu.com/#signin");
                headers.put("Host", "www.zhihu.com");
                headers.put("Accept", "*/*");
                return headers;
            }
        };
        mVolleyUtil.addRequest(mRequest);
    }

    private void handleFail(String response) {
        try {
            JSONObject jsonObj = new JSONObject(response);
            int errcode = jsonObj.getInt(LOGIN_ERRCODE_KEY);
            switch (errcode) {
                case NetConstants.ERRCODE_PWD_EMAIL_ERROR:
                    ToastUtils.show(NetConstants.ERRMSG_PWD_EMAIL_ERROR);
                    break;
                case NetConstants.ERRCODE_EMAIL_FORMAT_ERROR:
                    ToastUtils.show(NetConstants.ERRMSG_EMAIL_FORMAT_ERROR);
                    break;
                case NetConstants.ERRCODE_PWD_LENGTH_ERROR:
                    ToastUtils.show(NetConstants.ERRMSG_PWD_LENGTH_ERROR);
                    break;
                case NetConstants.ERRCODE_INPUT_CAPTCHA:
                    ToastUtils.show(NetConstants.ERRMSG__INPUT_CAPTCHA);
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
        mListener = (CallbackListener) callback;
    }

    public interface CallbackListener extends NetCmdCallback {
        void callback(int status);
    }
}
