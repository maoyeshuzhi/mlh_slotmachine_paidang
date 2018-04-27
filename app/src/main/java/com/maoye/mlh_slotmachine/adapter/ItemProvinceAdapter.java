package com.maoye.mlh_slotmachine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.ProvinceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rs on 2018/4/23.
 */

public class ItemProvinceAdapter extends BaseAdapter {
    private List<ProvinceBean> list = new ArrayList<>();
    private LayoutInflater mInflater;
    private  Context mContext;
    private ProvinceBean provinceBean;
    public  ItemProvinceAdapter(Context context,List<ProvinceBean> list,ProvinceBean provinceBean){
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.list = list;
        this.provinceBean = provinceBean;
    }

    public  void  setData(List<ProvinceBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(0);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder = null;
        if(view ==null){
            view = mInflater.inflate(R.layout.item_province,null);
            mViewHolder = new ViewHolder(view);
        }else {
            mViewHolder = (ViewHolder) view.getTag();
        }

        mViewHolder.nameTv.setText(list.get(i).getRegion_name()+"");
        if(list.get(i)!=null&&provinceBean!=null &&provinceBean.getRegion_name().equals(list.get(i).getRegion_name())){
            mViewHolder.nameTv.setTextColor(mContext.getResources().getColor(R.color.color_dd2450));
        }else {
            mViewHolder.nameTv.setTextColor(mContext.getResources().getColor(R.color.color_1e1e1e));
        }
        return view;
    }

    public class ViewHolder {

        private TextView nameTv;
        private ImageView isClick;

        public ViewHolder(View v) {
            nameTv = (TextView) v.findViewById(R.id.name_tv);
            v.setTag(this);
        }
    }
}
