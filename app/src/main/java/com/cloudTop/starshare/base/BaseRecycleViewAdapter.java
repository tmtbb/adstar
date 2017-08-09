package com.cloudTop.starshare.base;

import android.support.v7.widget.RecyclerView;

import com.cloudTop.starshare.listener.RecycleViewItemListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yiwei on 16/4/9.
 */
public abstract class BaseRecycleViewAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected RecycleViewItemListener itemListener;
    protected List<T> datas = new ArrayList<T>();

    public List<T> getDatas() {
        if (datas==null)
            datas = new ArrayList<T>();
        return datas;
    }

//    public void setDatas(List<T> datas) {
//        this.datas = datas;
//    }

    public void setItemListener(RecycleViewItemListener listener){
        this.itemListener = listener;
    }





    public void setDataList(Collection<T> list) {
        this.datas.clear();
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> list) {
        int lastIndex = this.datas.size();
        if (this.datas.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void remove(int position) {
        this.datas.remove(position);
        notifyItemRemoved(position);

        if(position != (getDatas().size())){ // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position,this.datas.size()-position);
        }
    }

    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }
}
