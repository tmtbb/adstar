package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.been.SystemMessageBeen;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/6/15.
 */

public class SystemMessageAdapter extends RecyclerView.Adapter {
    private ArrayList<SystemMessageBeen> listData;
    private Context mContext ;

    public SystemMessageAdapter(Context context , ArrayList<SystemMessageBeen> list) {
        this.listData = list;
        this.mContext = context ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_system_message, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        //viewHolder.tv_time.setText(TimeUtil.formatData(TimeUtil.dateFormatYMDHM,listData.get(position).getPositionTime()));
        viewHolder.tv_content.setText(String.valueOf(listData.get(position).getPositionId()));
        viewHolder.tv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnImgClickLitener.onImgClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_check)
        TextView tv_check;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //回调接口
    public interface OnImgClickLitener {
        void onImgClick(View view ,int position);
    }

    private OnImgClickLitener mOnImgClickLitener;

    public void setOnImgClickLitener(OnImgClickLitener mOnImgClickLitener) {
        this.mOnImgClickLitener = mOnImgClickLitener;
    }
    public void addAll(Collection<SystemMessageBeen> list) {
        int lastIndex = this.listData.size();
        if (this.listData.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }
    public void clear() {
        listData.clear();
        notifyDataSetChanged();
    }
}
