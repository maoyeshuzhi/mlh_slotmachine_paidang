package com.maoye.mlh_slotmachine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.AddressBean;

/**
 * Created by Rs on 2018/4/21.
 */

public class AddressAdapter extends BaseRecyclerAdapter<AddressBean> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.item_address,parent,false);
        return new AddressVH(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, AddressBean data, int size) {
        if (viewHolder instanceof AddressVH) {

        }
    }

    protected class AddressVH extends BaseVH {
        public AddressVH(View itemView) {
            super(itemView);
        }
    }
}
