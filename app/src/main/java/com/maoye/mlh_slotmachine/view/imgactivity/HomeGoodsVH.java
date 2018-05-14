package com.maoye.mlh_slotmachine.view.imgactivity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.HomeAdapter;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.util.DensityUtil;
import com.maoye.mlh_slotmachine.widget.DividerLine;
import com.maoye.mlh_slotmachine.widget.banner.loader.RecyclerViewLoader;

import java.util.List;

/**
 * Created by Rs on 2018/5/13.
 */

public class HomeGoodsVH extends RecyclerViewLoader {

    @Override
    public RecyclerView createView(Context context) {
        RecyclerView recyclerView = new RecyclerView(context);
        return recyclerView;
    }

    @Override
    public void displayData(Context context, Object data, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.addItemDecoration(new DividerLine(context, LinearLayout.VERTICAL, DensityUtil.dip2px(context, 10), context.getResources().getColor(R.color.white)));
        if (data == null) return;
        final List<HomeBean.ListBeanX> itemList = (List<HomeBean.ListBeanX>) data;
        HomeAdapter adapter = new HomeAdapter();
        recyclerView.setAdapter(adapter);
        adapter.addDatas(itemList);
    }
}
