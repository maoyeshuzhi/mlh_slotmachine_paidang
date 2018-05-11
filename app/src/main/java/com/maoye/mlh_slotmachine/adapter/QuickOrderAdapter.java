package com.maoye.mlh_slotmachine.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.QuickOrderBean;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.widget.NotScrollRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rs on 2018/5/8.
 */

public class QuickOrderAdapter extends BaseRecyclerAdapter<QuickOrderBean> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quick_order, parent, false);
        return new QuickOrderVH(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, final int RealPosition, final QuickOrderBean data, int size) {
        if (viewHolder instanceof QuickOrderVH) {
            QuickGoodsAdapter adapter = new QuickGoodsAdapter();
            ((QuickOrderVH) viewHolder).recycler.setLayoutManager(new LinearLayoutManager(mContext));
            ((QuickOrderVH) viewHolder).recycler.setAdapter(adapter);
            adapter.addDatas(data.getSaledList());
            if (data.isSelect()) {
                ((QuickOrderVH) viewHolder).orderNoTv.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.select), null, null, null);
            } else {
                ((QuickOrderVH) viewHolder).orderNoTv.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.unselect), null, null, null);
            }
            ((QuickOrderVH) viewHolder).orderNoTv.setText(data.getSaleNo() + "");
            //  ((QuickOrderVH) viewHolder).brandNameTv.setText(data.getB);
            double price = 0;
            for (QuickOrderBean.SaledListBean saledListBean : data.getSaledList()) {
                price = price+ (saledListBean.getSaleAmount()-saledListBean.getCouponAmount())*saledListBean.getSaleNum();
            }
            ((QuickOrderVH) viewHolder).priceTv.setText(String.format(Constant.PRICE_FORMAT,String.format("%.2f",price) ));

            ((QuickOrderVH) viewHolder).orderNoTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemChildClickListener.onChildItemClick(view, 0, RealPosition, data);
                }
            });
        }
    }

    protected class QuickOrderVH extends BaseVH {
        @BindView(R.id.order_no_tv)
        TextView orderNoTv;
        @BindView(R.id.brand_name_tv)
        TextView brandNameTv;
        @BindView(R.id.recycler)
        NotScrollRecyclerView recycler;
        @BindView(R.id.price_tv)
        TextView priceTv;

        public QuickOrderVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
