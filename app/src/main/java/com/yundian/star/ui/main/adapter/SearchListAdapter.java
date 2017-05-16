package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SearchReturnbeen;
import com.yundian.star.base.SuperViewHolder;

/**
 * Created by Administrator on 2017/5/15.
 */

public class SearchListAdapter extends ListBaseAdapter<SearchReturnbeen.ListBean> {
    public SearchListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_search_list;
    }


    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        SearchReturnbeen.ListBean item = mDataList.get(position);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_code = holder.getView(R.id.tv_code);
        tv_name.setText(item.getName());
        tv_code.setText(item.getCode());
    }
}
