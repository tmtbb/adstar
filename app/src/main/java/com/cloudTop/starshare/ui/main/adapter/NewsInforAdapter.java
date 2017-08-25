package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.ui.main.model.NewsInforModel;
import com.cloudTop.starshare.utils.ImageLoaderUtils;

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
        ImageView showpic_url = holder.getView(R.id.showpic_url);
        ImageLoaderUtils.display(mContext,showpic_url,item.getShowpic_url());
        titleText.setText(item.getSubject_name());
    }
}
