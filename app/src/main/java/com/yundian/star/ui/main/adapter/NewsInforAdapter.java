package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.ui.main.model.NewsInforModel;

/**
 * Created by Administrator on 2017/5/8.
 */

public class NewsInforAdapter extends ListBaseAdapter<NewsInforModel.ListBean> {
    public NewsInforAdapter(Context context) {
        super(context);
    }
    @Override
    public int getLayoutId() {
        return R.layout.adapter_news_list;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        NewsInforModel.ListBean item = mDataList.get(position);
        TextView titleText = holder.getView(R.id.tv_name);
        titleText.setText(item.getId()+"");
    }
}
