package com.yundian.star.ui.im.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.ui.main.model.NewsInforModel;

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
        titleText.setText(item.getUsername());
    }
}
