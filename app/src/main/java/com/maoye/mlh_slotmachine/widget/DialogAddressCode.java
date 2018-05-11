package com.maoye.mlh_slotmachine.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.WriterException;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.util.CodeUtils;
import com.maoye.mlh_slotmachine.util.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rs on 2018/4/21.
 */

public class DialogAddressCode extends AlertDialog {
    @BindView(R.id.code_img)
    ImageView codeImg;
    @BindView(R.id.cancel_tv)
    TextView cancelTv;
    private String linkUrl;
    private OnItemChildClickListener onItemChildClickListener;
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener){
        this.onItemChildClickListener  = onItemChildClickListener;
    }

    public DialogAddressCode(Context context, String linkUrl) {
        super(context);
        this.linkUrl = linkUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_addresscode);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        try {
            codeImg.setImageBitmap(CodeUtils.createQRCode(linkUrl, DensityUtil.dip2px(getContext(), 138)));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.cancel_tv)
    public void onClick() {
        if(onItemChildClickListener!=null){
            onItemChildClickListener.onChildItemClick(null,0,0,null);
        }
        dismiss();
    }
}
