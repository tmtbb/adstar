package com.yundian.star.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.ui.main.model.NewsInforModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/8.
 */

public class NewsInfoAdapter extends RecyclerView.Adapter {
    ArrayList<NewsInforModel> datas ;
    public NewsInfoAdapter(ArrayList<NewsInforModel> data) {
        this.datas = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_news_list, parent, false);
        return new NewsInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsInfoHolder handlerHolder = (NewsInfoHolder) holder;
        handlerHolder.tv_name.setText(datas.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class NewsInfoHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        public NewsInfoHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
