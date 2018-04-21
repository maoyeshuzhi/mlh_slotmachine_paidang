package com.maoye.mlh_slotmachine.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Rs on 2018/4/21.
 * 选择添加地址
 */

public class DialogIsAddAdress extends AlertDialog implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.localadd_rb)
    RadioButton localaddRb;
    @BindView(R.id.phoneadd_rb)
    RadioButton phoneaddRb;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.cancel_tv)
    TextView cancelTv;
    @BindView(R.id.confirm_tv)
    TextView confirmTv;
    private int type;//添加地址类型
    private OnItemChildClickListener onItemChildClickListener;

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }

    public DialogIsAddAdress(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_isaddaddress);
        setCanceledOnTouchOutside(false);
        rg.setOnCheckedChangeListener(this);
    }


    @OnClick({R.id.cancel_tv, R.id.confirm_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                dismiss();
                break;
            case R.id.confirm_tv:
                if(onItemChildClickListener!=null)
                    onItemChildClickListener.onChildItemClick(null,type,0,null);
                dismiss();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.localadd_rb:
                type = 0;
                break;
            case R.id.phoneadd_rb:
                type = 1;
                break;
        }
    }
}
