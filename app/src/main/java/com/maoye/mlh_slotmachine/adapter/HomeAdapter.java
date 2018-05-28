package com.maoye.mlh_slotmachine.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.maoye.mlh_slotmachine.view.goodsactivity.GoodsActivity;
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
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false);
        return new HomeVH(view);
    }

    @Override
    public void onBind(final RecyclerView.ViewHolder viewHolder, int RealPosition, final HomeBean.ListBeanX data, int size) {
        if (viewHolder instanceof HomeVH) {
            ImgGlideUtil.displayImage(data.getSpread_image(), ((HomeVH) viewHolder).brandImg, true);
          ((HomeVH) viewHolder).brandImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, GoodsActivity.class);
                    intent.putExtra(Constant.BRAND_ID, data.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
            });
        }
    }




    protected class HomeVH extends BaseVH {
        @BindView(R.id.brand_img)
        ImageView brandImg;
        public HomeVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
