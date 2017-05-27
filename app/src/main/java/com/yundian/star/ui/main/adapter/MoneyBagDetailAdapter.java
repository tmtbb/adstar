package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.BookingStarListBean;

/**
 * Created by sll on 2017/5/25.
 */

public class MoneyBagDetailAdapter extends ListBaseAdapter<BookingStarListBean> {
    public MoneyBagDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_money_bag_detail;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        BookingStarListBean item = mDataList.get(position);
        TextView titleText = holder.getView(R.id.tv_money_detail_week);

    }
}