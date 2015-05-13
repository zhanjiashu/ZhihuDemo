package com.jiashu.zhihudemo.vu.general;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.vu.Vu;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jiashu on 2015/4/25.
 */
public class EditVu extends Vu {

    View mView;

    @InjectView(R.id.et_hint)
    EditText mEditText;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.edit_layout, container, false);
        ButterKnife.inject(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    public void setEditHint(CharSequence msg) {
        mEditText.setHint(msg);
    }
}
