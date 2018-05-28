package com.maoye.mlh_slotmachine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.SpecBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rs on 2018/5/8.
 */

public class FlowAdapter extends BaseRecyclerAdapter<SpecBean.SpecItemListBean> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flow, parent, false);
        return new FlowVH(view);
    }

    @Override
    public void onBind(final RecyclerView.ViewHolder viewHolder, final int RealPosition, final SpecBean.SpecItemListBean data, int size) {
        if (viewHolder instanceof FlowVH) {
            if (data.isSelect()) {
                ((FlowVH) viewHolder).nameTv.setTextColor(mContext.getResources().getColor(R.color.white));
                ((FlowVH) viewHolder).nameTv.setBackgroundResource(R.color.black);
            } else {
                ((FlowVH) viewHolder).nameTv.setTextColor(mContext.getResources().getColor(R.color.color_323232));
                ((FlowVH) viewHolder).nameTv.setBackgroundResource(R.drawable.color646464_stroke);
            }
            ((FlowVH) viewHolder).nameTv.setText(data.getItemName());
            ((FlowVH) viewHolder).nameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((FlowVH) viewHolder).nameTv.setTextColor(mContext.getResources().getColor(R.color.white));
                    ((FlowVH) viewHolder).nameTv.setBackgroundResource(R.color.black);
                    for (int i = 0; i < getData().size(); i++) {
                        if(i == RealPosition){
                            getData().get(i).setSelect(true);
                        }else {
                            getData().get(i).setSelect(false);
                        }
                    }
                    notifyDataSetChanged();
                    onItemChildClickListener.onChildItemClick(view,0,RealPosition,data);
                }
            });

        }
    }

    protected class FlowVH extends BaseVH {
        @BindView(R.id.name_tv)
        TextView nameTv;

        public FlowVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
