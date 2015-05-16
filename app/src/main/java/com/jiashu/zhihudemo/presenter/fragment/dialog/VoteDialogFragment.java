package com.jiashu.zhihudemo.presenter.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.events.VoteEvent;
import com.jiashu.zhihudemo.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/5/15.
 */
public class VoteDialogFragment extends DialogFragment {

    View mView;

    private EventBus mBus;

    @InjectView(R.id.tbtn_vote_up)
    ToggleButton mVoteUpBtn;

    @InjectView(R.id.tbtn_vote_down)
    ToggleButton mVoteDownBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.dialog_voteups_layout, null);
        ButterKnife.inject(this, mView);

        mBus = EventBus.getDefault();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("为答案投票");
        builder.setView(mView);
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle args = getArguments();
        mVoteUpBtn.setChecked(args.getBoolean("isVoteUpChecked", false));
        mVoteDownBtn.setChecked(args.getBoolean("isVoteDownChecked", false));

        mVoteUpBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                VoteEvent event = new VoteEvent();
                event.setIsVoteDown(false);

                if (mVoteDownBtn.isChecked()) {
                    event.setIsVoteUp(true);
                } else {
                    event.setIsVoteUp(isChecked);
                }

                mBus.post(event);

                getDialog().dismiss();
            }
        });

        mVoteDownBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                VoteEvent event = new VoteEvent();
                event.setIsVoteDown(isChecked);
                event.setIsVoteUp(false);
                mBus.post(event);

                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
