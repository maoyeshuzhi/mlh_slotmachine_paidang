package com.maoye.mlh_slotmachine.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rs on 2018/5/4.
 * 放弃支付dialog
 */

public class GiveUpPayDialog extends AlertDialog {

    @BindView(R.id.confirm_tv)
    TextView confirmTv;
    @BindView(R.id.cancel_tv)
    TextView cancelTv;
    private OnItemClickListener onItemClickListener;
    public static final int CONFIRM = 1;
    public static final int CANCEL = 2;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public GiveUpPayDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_giveuppay);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.confirm_tv, R.id.cancel_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_tv:
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(null, CONFIRM, null);
                     dismiss();
                break;
            case R.id.cancel_tv:
                    dismiss();
                break;
        }
    }
}
