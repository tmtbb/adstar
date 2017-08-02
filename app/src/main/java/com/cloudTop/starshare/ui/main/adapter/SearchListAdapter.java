package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SearchReturnbeen;

/**
 * Created by Administrator on 2017/5/15.
 */

public class SearchListAdapter extends ListBaseAdapter<SearchReturnbeen.StarsinfoBean> {
    public SearchListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_search_list;
    }


    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        SearchReturnbeen.StarsinfoBean item = mDataList.get(position);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_code = holder.getView(R.id.tv_code);
        tv_name.setText(item.getName());
        tv_code.setText(item.getSymbol());
    }
}
