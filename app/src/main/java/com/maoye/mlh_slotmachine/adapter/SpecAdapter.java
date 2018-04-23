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
import com.maoye.mlh_slotmachine.widget.BGAFlowLayout;

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
            setLables((SpecVH) viewHolder, RealPosition, data);
        }
    }

    private void setLables(SpecVH viewHolder, int realPosition, SpecBean data) {
        List<SpecBean.SpecItemListBean> pecItemList = data.getPecItemList();
        viewHolder.flowlayout.removeAllViews();
        for (int i = 0; i < pecItemList.size(); i++) {
            viewHolder.flowlayout.addView(getLabe(pecItemList.get(i).getItemName(), i, pecItemList, realPosition));
        }
    }

    private View getLabe(String itemName, final int i, final List<SpecBean.SpecItemListBean> pecItemList, final int realPosition) {
        final SpecBean.SpecItemListBean itemListBean = pecItemList.get(i);
        final TextView labe = new TextView(mContext);
        labe.setGravity(Gravity.CENTER);
        labe.setSingleLine(true);
        labe.setTextSize(14);
        labe.setEllipsize(TextUtils.TruncateAt.END);
        int padding = BGAFlowLayout.dp2px(mContext, 10);
        int paddingRight = BGAFlowLayout.dp2px(mContext, 3);
        labe.setPadding(padding, paddingRight, padding, paddingRight);
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
        @BindView(R.id.flowlayout)
        BGAFlowLayout flowlayout;

        public SpecVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
