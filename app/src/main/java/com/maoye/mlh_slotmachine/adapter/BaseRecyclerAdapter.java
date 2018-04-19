package com.maoye.mlh_slotmachine.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.listener.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;

    public List<T> mDatas = new ArrayList<>();

    private View mHeaderView;
    private View mFooterView;

    private OnItemClickListener mListener;
    private OnItemLongClickListener longClickListener;

    public OnItemChildClickListener onItemChildClickListener;

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener){
        this.onItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }


    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public List<T> getData(){
        return mDatas;
    }


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View mFooterView) {
        this.mFooterView = mFooterView;
        notifyItemInserted(mDatas.size());
    }


    public View getFooterView() {
        return mFooterView;
    }

    public View getHeaderView() {
        return mHeaderView;
    }


    public void addDatas(List<T> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) return TYPE_HEADER;
        if (mFooterView != null && mHeaderView != null && position > mDatas.size() || (mFooterView != null && mHeaderView == null && position >= mDatas.size()))
            return TYPE_FOOTER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new BaseVH(mHeaderView);

        if (mFooterView != null && viewType == TYPE_FOOTER) return new BaseVH(mFooterView);
        return onCreate(parent, viewType);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
        if (pos < mDatas.size()) {
            final T data = mDatas.get(pos);
            onBind(viewHolder, pos, data , mDatas.size());


            if (mListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(v,pos, data);
                    }
                });
            }

            if (longClickListener != null) {
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        longClickListener.onItemLongClick(view, pos,null);
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }


    @Override
    public int getItemCount() {
        if (mDatas == null) return 0;
        if (mHeaderView != null && mFooterView == null) return mDatas.size() + 1;
        if (mHeaderView != null && mFooterView != null) return mDatas.size() + 2;
        if (mHeaderView == null && mFooterView != null) return mDatas.size() + 1;
        if (mHeaderView == null && mFooterView == null) return mDatas.size();
        return mDatas.size();
        //  return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }



    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);

    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data , int size);

    public class BaseVH extends RecyclerView.ViewHolder {
        public BaseVH(View itemView) {
            super(itemView);
        }
    }


}
