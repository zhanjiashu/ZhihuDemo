package com.jiashu.zhihudemo.presenter.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.presenter.activity.LoginActivity;
import com.jiashu.zhihudemo.events.FetchCaptchaRE;
import com.jiashu.zhihudemo.events.OnLoginEvent;
import com.jiashu.zhihudemo.utils.LogUtils;
import com.jiashu.zhihudemo.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/5/2.
 * 【登录】 对话框
 */
public class LoginDialogFragment extends DialogFragment implements TextWatcher {

    private static final String TAG = "LoginDiaogFragment";
    @InjectView(R.id.et_email)
    EditText mEmailEdit;

    @InjectView(R.id.et_password)
    EditText mPasswordEdit;

    @InjectView(R.id.et_code)
    EditText mCodeEdit;

    @InjectView(R.id.iv_captcha)
    ImageView mCaptchaView;

    @InjectView(R.id.ll_bottom)
    LinearLayout mLayout;

    Button mPositiveBtn;

    EventBus mBus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        mBus = EventBus.getDefault();
        mBus.register(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_login_layout, null);
        ButterKnife.inject(this, view);

        Bundle args = getArguments();
        if (args != null) {
            mEmailEdit.setText(args.getString(LoginActivity.PREF_KEY_EMAIL));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.general_login);
        builder.setView(view);
        builder.setPositiveButton(R.string.general_confirm, null);

        builder.setNegativeButton(R.string.general_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLayout.setVisibility(View.GONE);  // 隐藏 验证码 区域
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        mPositiveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (TextUtils.isEmpty(mEmailEdit.getText().toString()) ||
                TextUtils.isEmpty(mPasswordEdit.getText().toString())) {
            mPositiveBtn.setEnabled(false);
        }
        mEmailEdit.addTextChangedListener(this);
        mPasswordEdit.addTextChangedListener(this);
        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEdit.getText().toString();
                String pwd = mPasswordEdit.getText().toString();
                String code = mCodeEdit.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)) {
                    OnLoginEvent event = new OnLoginEvent();
                    event.setEmail(email);
                    event.setPassword(pwd);
                    event.setCaptcha(code);
                    EventBus.getDefault().post(event);
                } else {
                    ToastUtils.show("邮箱或密码不能为空");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        mBus.unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "DialogFragment is destroy");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (!TextUtils.isEmpty(mEmailEdit.getText().toString()) &&
                !TextUtils.isEmpty(mPasswordEdit.getText().toString())) {
            mPositiveBtn.setEnabled(true);
        } else {
            mPositiveBtn.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        LogUtils.d(TAG, s.toString());
    }

    public void onEvent(FetchCaptchaRE event) {
        mCaptchaView.setImageBitmap(event.mCaptcha);
    }
}
