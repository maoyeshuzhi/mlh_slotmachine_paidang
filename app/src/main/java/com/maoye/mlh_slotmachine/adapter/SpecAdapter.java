package com.maoye.mlh_slotmachine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.SpecBean;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.util.DensityUtil;
import com.maoye.mlh_slotmachine.widget.BGAFlowLayout;
import com.maoye.mlh_slotmachine.widget.SpaceItemDecoration;
import com.maoye.mlh_slotmachine.widget.flow.FlowLayoutManager;
import com.maoye.mlh_slotmachine.widget.flow.NestedRecyclerView;

import java.util.List;

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
            ((SpecVH) viewHolder).specnameTv.setText(data.getSpecName());
            ((SpecVH) viewHolder).recycler.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(mContext, 2)));
            final FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
            ((SpecVH) viewHolder).recycler.setLayoutManager(flowLayoutManager);
            FlowAdapter adapter = new FlowAdapter();
            ((SpecVH) viewHolder).recycler.setAdapter(adapter);
            adapter.addDatas(data.getPecItemList());
            adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onChildItemClick(View view, int type, int position, Object data) {
                      onItemChildClickListener.onChildItemClick(view ,RealPosition,position,data);
                }
            });


            // setLables((SpecVH) viewHolder, RealPosition, data);
        }
    }

/*    private void setLables(SpecVH viewHolder, int realPosition, SpecBean data) {
        List<SpecBean.SpecItemListBean> pecItemList = data.getPecItemList();
        viewHolder.flowlayout.removeAllViews();
        for (int i = 0; i < pecItemList.size(); i++) {
            viewHolder.flowlayout.addView(getLabe(pecItemList.get(i).getItemName(), i, pecItemList, realPosition));
        }
    }*/

    private View getLabe(String itemName, final int i, final List<SpecBean.SpecItemListBean> pecItemList, final int realPosition) {
        final SpecBean.SpecItemListBean itemListBean = pecItemList.get(i);
        final TextView labe = new TextView(mContext);
        labe.setGravity(Gravity.CENTER);
        labe.setSingleLine(true);
        labe.setTextSize(8);
        labe.setEllipsize(TextUtils.TruncateAt.END);
        int padding = BGAFlowLayout.dp2px(mContext, 4);
        labe.setPadding(padding, 4, padding, 4);
        labe.setText(itemName);
        labe.setClickable(true);
        if (itemListBean.isSelect()) {
            labe.setTextColor(mContext.getResources().getColor(R.color.white));
            labe.setBackgroundResource(R.color.black);
        } else {
            labe.setTextColor(mContext.getResources().getColor(R.color.color_323232));
            labe.setBackgroundResource(R.drawable.color646464_stroke);
        }

        labe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListBean.setSelect(!itemListBean.isSelect());
                for (int k = 0; k < pecItemList.size(); k++) {
                    if (k != i) {
                        pecItemList.get(k).setSelect(false);
                    }
                }
                notifyDataSetChanged();
                onItemChildClickListener.onChildItemClick(null, realPosition, i, itemListBean);
            }
        });
        return labe;
    }

    protected class SpecVH extends RecyclerView.ViewHolder {
        @BindView(R.id.specname_tv)
        TextView specnameTv;
        /*  @BindView(R.id.flowlayout)
          BGAFlowLayout flowlayout;*/
        @BindView(R.id.recycler)
        NestedRecyclerView recycler;

        public SpecVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
