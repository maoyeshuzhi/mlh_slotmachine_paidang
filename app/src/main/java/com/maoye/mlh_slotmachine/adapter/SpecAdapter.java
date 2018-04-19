package com.maoye.mlh_slotmachine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.SpecBean;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.util.DensityUtil;
import com.maoye.mlh_slotmachine.widget.FlowLayoutManager;
import com.maoye.mlh_slotmachine.widget.SpaceItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rs on 2018/4/18.
 */

public class SpecAdapter extends BaseRecyclerAdapter<SpecBean> {
    private Context mContext;
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spec, parent, false);
        return new SpecVH(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, final int RealPosition, SpecBean data, int size) {
        if (viewHolder instanceof SpecVH) {
            SpecItemAdapter adapter = new SpecItemAdapter();
            ((SpecVH) viewHolder).recycler.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(mContext,10)));
            ((SpecVH) viewHolder).recycler.setLayoutManager(new FlowLayoutManager());
            ((SpecVH) viewHolder).recycler.setAdapter(adapter);
            ((SpecVH) viewHolder).specnameTv.setText(data.getSpecName());
             adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, Object data) {
                      onItemChildClickListener.onChildItemClick(view,RealPosition,position,data);
                }
            });
        }
    }

    protected class SpecVH extends RecyclerView.ViewHolder {
        @BindView(R.id.specname_tv)
        TextView specnameTv;
        @BindView(R.id.recycler)
        RecyclerView recycler;

        public SpecVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
