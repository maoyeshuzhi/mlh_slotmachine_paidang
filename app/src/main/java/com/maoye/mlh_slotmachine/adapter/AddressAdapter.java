package com.maoye.mlh_slotmachine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.AddressBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rs on 2018/4/21.
 */

public class AddressAdapter extends BaseRecyclerAdapter<AddressBean.ListBean> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_address, parent, false);
        return new AddressVH(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, AddressBean.ListBean data, int size) {
        if (viewHolder instanceof AddressVH) {
            if (data.getDefaultX() == 1) {
                ((AddressVH) viewHolder).itemLl.setBackgroundResource(R.mipmap.address_select);
            }else {
                ((AddressVH) viewHolder).itemLl.setBackgroundResource(R.mipmap.address_unseoect);
            }
            ((AddressVH) viewHolder).addressTv.setText(data.getProvince_name()+data.getCity_name()+"");
            ((AddressVH) viewHolder).nameTv.setText(String.format("(%s收)",data.getName()+""));
            ((AddressVH) viewHolder).addressAddressTv.setText(data.getArea_name()+data.getStreet()+data.getPhone()+"");
        }
    }

    protected class AddressVH extends BaseVH {
        @BindView(R.id.address_tv)
        TextView addressTv;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.detial_address_tv)
        TextView addressAddressTv;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;
        public AddressVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
