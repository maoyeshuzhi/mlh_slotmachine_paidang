package com.maoye.mlh_slotmachine.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.ItemProvinceAdapter;
import com.maoye.mlh_slotmachine.bean.ProvinceBean;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rs on 2018/4/23.
 */

public class ProvincePopWindow extends PopupWindow {
    private View view,v;
    private ListView listView;
    private ItemProvinceAdapter provinceAdapter;
    private List<ProvinceBean> provinceList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    public ProvincePopWindow (Context context, final List<ProvinceBean> list, ProvinceBean provinceBean, final int type){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.pop_province,null);

        v = view.findViewById(R.id.view);
        listView = view.findViewById(R.id.listview);
        this.provinceList = list;
        provinceAdapter = new ItemProvinceAdapter(context,provinceList,provinceBean);
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        listView.setAdapter(provinceAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dismiss();
                onItemClickListener.onItemClick(adapterView,type,list.get(i));

            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
