package com.maoye.mlh_slotmachine.mvp;

/**
 * Created by Rs on 2018/4/11.
 */

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 基础的ViewHolder
 */
public abstract class BaseHolder<M> extends RecyclerView.ViewHolder {

    public BaseHolder(View view) {
        super(view);
    }

    /**
     * 获取布局中的View
     * @param viewId view的Id
     * @param <T> View的类型
     * @return view
     */
    protected <T extends View>T getView(@IdRes int viewId){
        return (T) (itemView.findViewById(viewId));
    }

    /**
     * 获取Context实例
     * @return context
     */
    protected Context getContext() {
        return itemView.getContext();
    }
}