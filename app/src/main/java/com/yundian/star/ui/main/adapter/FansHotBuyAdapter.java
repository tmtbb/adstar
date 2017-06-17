package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.FansTopListBeen;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.TimeUtil;


/**
 * Created by Administrator on 2017/5/22.
 */

public class FansHotBuyAdapter extends ListBaseAdapter<FansTopListBeen.OrdersListBean> {
    private int hotTypes =0;
    public FansHotBuyAdapter(Context context,int hotType) {
        super(context);
        hotTypes = hotType;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_fans_hot_buy;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        FansTopListBeen.OrdersListBean ordersListBean = mDataList.get(position);
        ImageView iv_icon = holder.getView(R.id.iv_icon);
        TextView iv_tro = holder.getView(R.id.iv_tro);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_buy_price = holder.getView(R.id.tv_buy_price);
        if (hotTypes==1){
            ImageLoaderUtils.displayRound(mContext,iv_icon,ordersListBean.getBuy_user().getHeadUrl());
            tv_name.setText(ordersListBean.getBuy_user().getNickname());
        }else {
            ImageLoaderUtils.displayRound(mContext,iv_icon,ordersListBean.getSell_user().getHeadUrl());
            tv_name.setText(ordersListBean.getSell_user().getNickname());
        }
        if (position==0){
            iv_tro.setBackgroundResource(R.drawable.trophy_icon);
        }else {
            iv_tro.setText(String.format("%.2d",position+1));
        }
        String formatData = TimeUtil.formatData(TimeUtil.dateFormatYMDHMS, ordersListBean.getTrades().getOpenTime());
        tv_time.setText(formatData.substring(5));
        tv_buy_price.setText(String.format(mContext.getString(R.string.buy_price),ordersListBean.getTrades().getOpenPrice()));
    }

}
