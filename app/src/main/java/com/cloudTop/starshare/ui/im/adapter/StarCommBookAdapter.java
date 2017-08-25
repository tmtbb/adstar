package com.cloudTop.starshare.ui.im.adapter;

import android.content.Context;
import android.widget.TextView;

import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.ui.main.model.NewsInforModel;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;

/**
 * Created by Administrator on 2017/5/10.
 */

public class StarCommBookAdapter extends ListBaseAdapter<NewsInforModel> {
    public StarCommBookAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_communication_book;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        NewsInforModel item = mDataList.get(position);
        TextView titleText = holder.getView(R.id.tv_name);
        //titleText.setText(item.getUsername());
    }
}
