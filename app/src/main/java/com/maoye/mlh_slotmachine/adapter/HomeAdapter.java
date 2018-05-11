package com.maoye.mlh_slotmachine.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.view.goodsdetialsactivity.GoodsdetialsActivity;
import com.maoye.mlh_slotmachine.util.AnimUtil;
import com.maoye.mlh_slotmachine.util.CodeUtils;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.TextUtil;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rs on 2018/4/11.
 */

public class HomeAdapter extends BaseRecyclerAdapter<HomeBean.ListBeanX> {
    private Context mContext;
    private static final int DELAY_TIME = 15 * 1000;//延时旋转回来的时间
    private static final int ROTATE_TIEM = 300;//旋转时间

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false);
        return new HomeVH(view);
    }

    @Override
    public void onBind(final RecyclerView.ViewHolder viewHolder, int RealPosition, HomeBean.ListBeanX data, int size) {
        if (viewHolder instanceof HomeVH) {
            ((HomeVH) viewHolder).priceTv.setText(TextUtil.setPriceText(data.getPrice()+""));
            ((HomeVH) viewHolder).discriptionTv.setText(data.getName() + "");
            ImgGlideUtil.displayImage(data.getImage(), ((HomeVH) viewHolder).goodsImg, true);
            try {
                ((HomeVH) viewHolder).codeImg.setImageBitmap(CodeUtils.createQRCode(data.getProduct_link(), 300));
            } catch (WriterException e) {
                e.printStackTrace();
            }
            switchStatus((HomeVH) viewHolder, RealPosition,data);
        }
    }

    /**
     * 动画以及分割线状态显示
     *
     * @param viewHolder
     * @param RealPosition
     */
    private void switchStatus(final HomeVH viewHolder, int RealPosition, final HomeBean.ListBeanX data) {
        final boolean[] isShowCode = new boolean[1];
        viewHolder.buyPhoneLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAnim(isShowCode, viewHolder);
            }
        });


        viewHolder.goodsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GoodsdetialsActivity.class);
                intent.putExtra(Constant.GOODS_ID,data.getProduct_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
            }
        });

        viewHolder.codeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowCode[0]){
                    (viewHolder).codeImg.setVisibility(View.INVISIBLE);
                isShowCode[0] = !isShowCode[0];
                AnimUtil.FlipAnimatorXViewShow(viewHolder.codeImg, viewHolder.goodsImg, ROTATE_TIEM);
              }else {
                    Intent intent = new Intent(mContext, GoodsdetialsActivity.class);
                    intent.putExtra(Constant.GOODS_ID,data.getProduct_id());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
            }
        });

        if (RealPosition == 1 || RealPosition == 4) {
           // viewHolder.leftLineV.setVisibility(View.VISIBLE);
            //viewHolder.rightLineV.setVisibility(View.VISIBLE);
        } else {
            //viewHolder.leftLineV.setVisibility(View.GONE);
            //viewHolder.rightLineV.setVisibility(View.GONE);
        }
    }

    /**
     * 翻转动画
     *
     * @param isShowCode
     * @param viewHolder
     */
    private void viewAnim(final boolean[] isShowCode, final HomeVH viewHolder) {
        isShowCode[0] = !isShowCode[0];
        if (isShowCode[0]) {
            ( viewHolder).codeImg.setVisibility(View.VISIBLE);
            AnimUtil.FlipAnimatorXViewShow(viewHolder.goodsImg, viewHolder.codeImg, ROTATE_TIEM);
        } else {
            ( viewHolder).codeImg.setVisibility(View.INVISIBLE);
            AnimUtil.FlipAnimatorXViewShow(viewHolder.codeImg, viewHolder.goodsImg, ROTATE_TIEM);
        }
        if (isShowCode[0]) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isShowCode[0])
                        AnimUtil.FlipAnimatorXViewShow(viewHolder.codeImg, viewHolder.goodsImg, ROTATE_TIEM);
                }
            }, DELAY_TIME);
        }
    }

    protected class HomeVH extends BaseVH {

        @BindView(R.id.code_img)
        ImageView codeImg;
        @BindView(R.id.goods_img)
        ImageView goodsImg;
        @BindView(R.id.price_tv)
        TextView priceTv;
        @BindView(R.id.buy_phone_ll)
        LinearLayout buyPhoneLl;
        @BindView(R.id.right_line_v)
        View rightLineV;
        @BindView(R.id.discription_tv)
        TextView discriptionTv;

        public HomeVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
