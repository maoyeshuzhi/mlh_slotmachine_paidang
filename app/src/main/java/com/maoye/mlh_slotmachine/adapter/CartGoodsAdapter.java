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

public class CartGoodsAdapter extends BaseRecyclerAdapter<GoodsBean> {
    public static final int SELECT_GOODS = 1;//点击选择商品
    public static final int ADD_GOODS = 2;//添加
    public static final int SUBTRICT_GOODS = 3;//减

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cartgoods, parent, false);
        return new CartGoodsVH(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, final int RealPosition, final GoodsBean data, int size) {
        if (viewHolder instanceof CartGoodsVH) {

            ImgGlideUtil.displayImage(data.getProduct_image(), ((CartGoodsVH) viewHolder).img, true);
            ((CartGoodsVH) viewHolder).nameTv.setText(data.getProduct_name() + "");
            ((CartGoodsVH) viewHolder).priceTv.setText(String.format(Constant.PRICE_FORMAT, data.getPrice() + ""));
            ((CartGoodsVH) viewHolder).specificationTv.setText(data.getSpec_vals() + "");
            ((CartGoodsVH) viewHolder).numTv.setText(data.getNum()+"");

            if(data.isSelect()){
                ((CartGoodsVH) viewHolder).selectImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.select));
            }else {
                ((CartGoodsVH) viewHolder).selectImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.unselect));
            }
            ((CartGoodsVH) viewHolder).selectImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.setSelect(!data.isSelect());
                    notifyItemChanged(RealPosition);
                    onItemChildClickListener.onChildItemClick(view, SELECT_GOODS, RealPosition, data);

                }
            });

            ((CartGoodsVH) viewHolder).addTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemChildClickListener.onChildItemClick(view, ADD_GOODS, RealPosition, data);

                }
            });
            ((CartGoodsVH) viewHolder).subtractTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemChildClickListener.onChildItemClick(view, SUBTRICT_GOODS, RealPosition, data);
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
