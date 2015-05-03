package com.jiashu.zhihudemo.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.event.LoginEvent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/5/2.
 */
public class LoginDialogFragment extends DialogFragment {

    private static final String TAG = "LoginDiaogFragment";
    @InjectView(R.id.et_email)
    EditText mEmailEdit;

    @InjectView(R.id.et_password)
    EditText mPasswordEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Log.d(TAG, "onCreate");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_login_layout, null);
        ButterKnife.inject(this, view);

        mEmailEdit.clearFocus();
        mPasswordEdit.clearFocus();
        mEmailEdit.setText(getArguments().getString("email", ""));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.general_login);
        builder.setView(view);
        builder.setPositiveButton(R.string.general_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginEvent event = new LoginEvent();
                event.setIsLogined(true);
                event.setEmail(mEmailEdit.getText().toString());
                event.setPassword(mPasswordEdit.getText().toString());
                EventBus.getDefault().post(event);
            }
        });

        builder.setNegativeButton(R.string.general_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    public void onEvent(LoginEvent event) {
        //mEmailEdit.setText(event.getEmail());
        mEmailEdit.setText("777777@qq.com");
        Log.d(TAG, "Fragment - onEvent");
    }
}
