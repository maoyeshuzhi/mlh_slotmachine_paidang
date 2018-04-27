package com.maoye.mlh_slotmachine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rs on 2018/4/21.
 */

public class OrderGoodsAdapter extends BaseRecyclerAdapter<GoodsBean> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_ordergoods, parent, false);
        return new OrderGoodsVH(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, GoodsBean data, int size) {
        if(viewHolder instanceof OrderGoodsVH){
            ImgGlideUtil.displayImage(data.getProduct_image(),((OrderGoodsVH) viewHolder).goodsImg,true);
            ((OrderGoodsVH) viewHolder).nameTv.setText(data.getProduct_name()+"");
            ((OrderGoodsVH) viewHolder).specificationTv.setText(data.getSpec_vals()+"");
            ((OrderGoodsVH) viewHolder).priceTv.setText(String.format(Constant.PRICE_FORMAT,data.getPrice()+""));
            ((OrderGoodsVH) viewHolder).numTv.setText(String.format("x%d",data.getNum()));
        }

    }

    protected class OrderGoodsVH extends BaseVH {
        @BindView(R.id.goods_img)
        ImageView goodsImg;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.specification_tv)
        TextView specificationTv;
        @BindView(R.id.price_tv)
        TextView priceTv;
        @BindView(R.id.num_tv)
        TextView numTv;
        public OrderGoodsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
