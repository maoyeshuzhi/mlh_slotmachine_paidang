package com.maoye.mlh_slotmachine.view.homeactivity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.HomeAdapter;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.util.DensityUtil;
import com.maoye.mlh_slotmachine.widget.DividerLine;
import com.maoye.mlh_slotmachine.widget.NotScrollRecyclerView;
import com.maoye.mlh_slotmachine.widget.banner.loader.RecyclerViewLoader;

import java.util.List;

/**
 * Created by Rs on 2018/5/13.
 */

public class HomeBrandVH extends RecyclerViewLoader {

    @Override
    public RecyclerView createView(Context context) {
        NotScrollRecyclerView recyclerView = new NotScrollRecyclerView(context);

        return recyclerView;
    }

    @Override
    public void displayData(Context context, Object data, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
      //  recyclerView.addItemDecoration(new DividerLine(context, LinearLayout.VERTICAL, DensityUtil.dip2px(context, 10), context.getResources().getColor(R.color.white)));
        if (data == null) return;
        final List<HomeBean.ListBeanX> itemList = (List<HomeBean.ListBeanX>) data;
        HomeAdapter adapter = new HomeAdapter();
        recyclerView.setAdapter(adapter);
        adapter.addDatas(itemList);
    }
}
