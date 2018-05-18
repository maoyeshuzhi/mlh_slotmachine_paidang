package com.maoye.mlh_slotmachine.widget;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.util.CodeUtils;
import com.maoye.mlh_slotmachine.util.DensityUtil;


/**
 * Created by Rs on 2018/4/21.
 */

public class CodePop extends PopupWindow {
    private View view;
    private RelativeLayout v;
    private ImageView codeImg;
    private CountDownTimer timer;
    private TextView timeTv;
    public static final String TIME_HINT = "点击隐藏 %ss";

    public CodePop(Activity context, String linkUrl) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_code, null);
        v = view.findViewById(R.id.view);
        codeImg = view.findViewById(R.id.code_img);
        timeTv = view.findViewById(R.id.time_tv);
        codeImg.setImageBitmap(CodeUtils.createQRCode(linkUrl, DensityUtil.dip2px(context, 110),0));

        this.setContentView(view);
        this.setFocusable(true);
        view.setFocusableInTouchMode(true);

        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long l) {
                timeTv.setText(String.format(TIME_HINT, l / 1000));
            }

            @Override
            public void onFinish() {
                dismiss();
            }
        };
        timer.start();

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null) {
                    timer.cancel();
                    timer.onFinish();
                }
            }
        });
    }

}
