package com.maoye.mlh_slotmachine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.GoodsItemBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rs on 2018/4/21.
 */

public class CartGoodsAdapter extends BaseRecyclerAdapter<GoodsItemBean> {
public static final int SELECT_GOODS =1;//点击选择商品
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cartgoods, parent, false);
        return new CartGoodsVH(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, final int RealPosition, final GoodsItemBean data, int size) {
            if(viewHolder instanceof CartGoodsVH){
                ((CartGoodsVH) viewHolder).selectImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemChildClickListener.onChildItemClick(view,SELECT_GOODS,RealPosition,data);
                    }
                });

                ((CartGoodsVH) viewHolder).addTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                ((CartGoodsVH) viewHolder).subtractTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
    }

    protected class CartGoodsVH extends BaseVH {
        @BindView(R.id.select_img)
        ImageView selectImg;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.specification_tv)
        TextView specificationTv;
        @BindView(R.id.price_tv)
        TextView priceTv;
        @BindView(R.id.subtract_tv)
        TextView subtractTv;
        @BindView(R.id.num_tv)
        TextView numTv;
        @BindView(R.id.add_tv)
        TextView addTv;

        public CartGoodsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
