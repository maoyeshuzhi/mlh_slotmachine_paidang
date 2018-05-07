package com.maoye.mlh_slotmachine.view.homeactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.holder.Holder;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.HomeAdapter;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.view.goodsdetialsactivity.GoodsdetialsActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.DensityUtil;
import com.maoye.mlh_slotmachine.widget.DividerLine;

import java.util.List;

/**
 * Created by Rs on 2018/4/11.
 */

public class HomeGoodsVH extends Holder<Object> {
    private RecyclerView recycler;
    private HomeAdapter adapter;
    private Activity context;

    public HomeGoodsVH(View itemView,Activity context) {
        super(itemView);
        this.context = context;
    }

    @Override
    protected void initView(View itemView) {
        recycler = itemView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager( itemView.getContext(), 3));
        recycler.addItemDecoration(new DividerLine(itemView.getContext(), LinearLayout.VERTICAL, DensityUtil.dip2px( itemView.getContext(), 10),  itemView.getContext().getResources().getColor(R.color.white)));

    }

    @Override
    public void updateUI(Object data) {
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
