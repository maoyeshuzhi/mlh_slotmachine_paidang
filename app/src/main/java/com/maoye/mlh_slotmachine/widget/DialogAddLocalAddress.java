package com.maoye.mlh_slotmachine.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Rs on 2018/4/21.
 */

public class DialogAddLocalAddress extends AlertDialog {
    @BindView(R.id.province_et)
    EditText provinceEt;
    @BindView(R.id.detialaddress_et)
    EditText detialaddressEt;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.close_tv)
    TextView closeTv;
    @BindView(R.id.save_tv)
    TextView saveTv;



    public DialogAddLocalAddress(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_addlocaladdress);
        setCanceledOnTouchOutside(false);
    }

    @OnClick({R.id.close_tv, R.id.save_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_tv:
                dismiss();
                break;
            case R.id.save_tv:
                break;
        }
    }
}
