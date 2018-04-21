package com.maoye.mlh_slotmachine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.GoodsItemBean;

/**
 * Created by Rs on 2018/4/21.
 */

public class OrderGoodsAdapter extends BaseRecyclerAdapter<GoodsItemBean> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_ordergoods,parent,false);
        return new OrderGoodsVH(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, GoodsItemBean data, int size) {

    }
    protected class OrderGoodsVH extends BaseVH{
        public OrderGoodsVH(View itemView) {
            super(itemView);
        }
    }
}
