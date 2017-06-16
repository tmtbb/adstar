package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.OrderReturnBeen;

/**
 * Created by Administrator on 2017/5/24.
 */

public class AlreadyBoughtAdapter extends ListBaseAdapter<OrderReturnBeen.OrdersListBean>{
    public AlreadyBoughtAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_already_bought;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        OrderReturnBeen.OrdersListBean ordersListBean = mDataList.get(position);

        TextView iv_src = holder.getView(R.id.tv_name);
        TextView tv_market_value = holder.getView(R.id.tv_market_value);
    }
}
