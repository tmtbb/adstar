package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.StarMailListBeen;

/**
 * Created by Administrator on 2017/5/24.
 */

public class AlreadyBoughtAdapter extends ListBaseAdapter<StarMailListBeen.DepositsinfoBean>{
    public AlreadyBoughtAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_already_bought;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        StarMailListBeen.DepositsinfoBean ordersListBean = mDataList.get(position);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_code = holder.getView(R.id.tv_code);
        TextView tv_have_time = holder.getView(R.id.tv_have_time);
        tv_have_time.setText(String.valueOf(ordersListBean.getOwnseconds()));
        tv_name.setText(ordersListBean.getStarname());
        tv_code.setText(ordersListBean.getStarcode());
    }
}
