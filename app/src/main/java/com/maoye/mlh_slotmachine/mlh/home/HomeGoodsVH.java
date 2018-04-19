package com.maoye.mlh_slotmachine.mlh.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.HomeAdapter;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.mlh.goodsdetials.GoodsdetialsActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.DensityUtil;
import com.maoye.mlh_slotmachine.widget.DividerLine;
import com.maoye.mlh_slotmachine.widget.banner.holder.Holder;

import java.util.List;

/**
 * Created by Rs on 2018/4/11.
 */

public class HomeGoodsVH implements Holder<Object> {
    private RecyclerView recycler;
    private HomeAdapter adapter;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_noviewstub, null);
        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(context, 3));
        recycler.addItemDecoration(new DividerLine(context, LinearLayout.VERTICAL, DensityUtil.dip2px(context, 10), context.getResources().getColor(R.color.white)));
        return view;
    }

    @Override
    public void UpdateUI(final Context context, int position, Object data) {
        if (data == null) return;
        final List<HomeBean.ListBeanX> itemList = (List<HomeBean.ListBeanX>) data;
        adapter = new HomeAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                Intent intent = new Intent(context, GoodsdetialsActivity.class);
                intent.putExtra(Constant.GOODS_ID,itemList.get(position).getProduct_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        recycler.setAdapter(adapter);
        adapter.addDatas(itemList);
    }
}
