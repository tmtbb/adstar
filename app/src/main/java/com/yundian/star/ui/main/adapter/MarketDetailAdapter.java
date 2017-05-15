package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.ui.main.model.TestModel;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MarketDetailAdapter extends ListBaseAdapter<TestModel> {
    public MarketDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_market_detail;
    }


    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TestModel item = mDataList.get(position);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_price = holder.getView(R.id.tv_price);
        tv_name.setText(item.getUsername());
    }
}
