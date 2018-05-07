package com.maoye.mlh_slotmachine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.GoodsDetialsBean;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rs on 2018/4/17.
 */

public class GoodsDetialsGoodsAdapter extends BaseRecyclerAdapter<GoodsDetialsBean.ImageListBean> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_pic, parent, false);
        return new GoodPicVH(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, GoodsDetialsBean.ImageListBean data, int size) {
        if (viewHolder instanceof GoodPicVH) {
            ImgGlideUtil.displayImage(data.getImage_url(), ((GoodPicVH) viewHolder).img, true);
            if(data.getIs_default()==1){
                ((GoodPicVH) viewHolder).backgroundLl.setBackgroundColor(mContext.getResources().getColor(R.color.color_dd2450));
            }else {
                ((GoodPicVH) viewHolder).backgroundLl.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        }
    }

    protected class GoodPicVH extends BaseVH {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.background_ll)
        LinearLayout backgroundLl;

        public GoodPicVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
