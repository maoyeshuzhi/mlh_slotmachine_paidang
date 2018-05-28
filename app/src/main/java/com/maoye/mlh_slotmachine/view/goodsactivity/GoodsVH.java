package com.maoye.mlh_slotmachine.view.goodsactivity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.maoye.mlh_slotmachine.adapter.GoodsAdapter;
import com.maoye.mlh_slotmachine.adapter.HomeAdapter;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.widget.NotScrollRecyclerView;
import com.maoye.mlh_slotmachine.widget.banner.loader.RecyclerViewLoader;

import java.util.List;

/**
 * Created by Rs on 2018/5/13.
 */

public class GoodsVH extends RecyclerViewLoader {

    @Override
    public RecyclerView createView(Context context) {
        NotScrollRecyclerView recyclerView = new NotScrollRecyclerView(context);
        return recyclerView;
    }

    @Override
    public void displayData(Context context, Object data, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
       if (data == null) return;
        final List<GoodsBean> itemList = (List<GoodsBean>) data;
        GoodsAdapter adapter = new GoodsAdapter();
        recyclerView.setAdapter(adapter);
        adapter.addDatas(itemList);
    }
}
