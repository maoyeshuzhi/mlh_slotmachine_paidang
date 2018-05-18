package com.maoye.mlh_slotmachine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.QuickOrderBean;
import com.maoye.mlh_slotmachine.bean.QuickOrderDetialsBean;
import com.maoye.mlh_slotmachine.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rs on 2018/5/9.
 */

public class QuickGoodsDetialAdapter extends BaseRecyclerAdapter<QuickOrderDetialsBean.SaledListBean> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quickgoods, parent, false);
        return new QuickGoodsVH(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, QuickOrderDetialsBean.SaledListBean data, int size) {
        if (viewHolder instanceof QuickGoodsVH) {
            ((QuickGoodsVH) viewHolder).goodsNameTv.setText(data.getGoodsName() + "");
            ((QuickGoodsVH) viewHolder).goodsNumTv.setText(String.format(Constant.GOODS_NUM_FORMAT, data.getSaleNum()));
            ((QuickGoodsVH) viewHolder).codeTv.setText(data.getBarcode()+"");
            ((QuickGoodsVH) viewHolder).priceTv.setText(String.format(Constant.PRICE_FORMAT,data.getSaleAmount()+""));
        }
    }

    protected class QuickGoodsVH extends BaseVH {
        @BindView(R.id.goods_name_tv)
        TextView goodsNameTv;
        @BindView(R.id.price_tv)
        TextView priceTv;
        @BindView(R.id.head_layout)
        LinearLayout headLayout;
        @BindView(R.id.code_tv)
        TextView codeTv;
        @BindView(R.id.goods_num_tv)
        TextView goodsNumTv;

        public QuickGoodsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
