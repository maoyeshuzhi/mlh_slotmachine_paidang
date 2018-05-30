package com.maoye.mlh_slotmachine.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.BrandGoodsListBean;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;
import com.maoye.mlh_slotmachine.view.goodsdetialsactivity.GoodsdetialsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rs on 2018/5/28.
 */

public class BrandGoodsGuidesAdapter extends BaseRecyclerAdapter<BrandGoodsListBean.ListBean> {
    private Activity context;

    public void setContext(Activity context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brandgoods, parent, false);
        return new BrandGoodsVH(view);
    }

    @Override
    public void onBind(final RecyclerView.ViewHolder viewHolder, int RealPosition, final BrandGoodsListBean.ListBean data, int size) {
        if (viewHolder instanceof BrandGoodsVH) {
            if (RealPosition % 2 == 0) {
                ((BrandGoodsVH) viewHolder).rightSpace.setVisibility(View.VISIBLE);
                ((BrandGoodsVH) viewHolder).leftSpace.setVisibility(View.GONE);
            } else {
                ((BrandGoodsVH) viewHolder).rightSpace.setVisibility(View.GONE);
                ((BrandGoodsVH) viewHolder).leftSpace.setVisibility(View.VISIBLE);
            }
            ((BrandGoodsVH) viewHolder).itemLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GoodsdetialsActivity.class);
                    intent.putExtra(Constant.GOODS_ID, data.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation( context, ((BrandGoodsVH) viewHolder).goodsImg, "img").toBundle());
                    } else {
                        context.startActivity(intent);
                    }
                }
            });
            ImgGlideUtil.displayImage(data.getImage(), ((BrandGoodsVH) viewHolder).goodsImg, true);
            ((BrandGoodsVH) viewHolder).nameTv.setText(data.getName() + "");
            ((BrandGoodsVH) viewHolder).priceTv.setText(String.format(Constant.PRICE_FORMAT, data.getPrice() + ""));
        }
    }

    protected class BrandGoodsVH extends BaseVH {
        @BindView(R.id.goods_img)
        ImageView goodsImg;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.price_tv)
        TextView priceTv;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;
        @BindView(R.id.right_space)
        View rightSpace;
        @BindView(R.id.left_space)
        View leftSpace;

        public BrandGoodsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
