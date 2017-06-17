package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.FansEntrustReturnBean;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.TimeUtil;


/**
 * Created by Administrator on 2017/5/22.
 */

public class AutionTopAdapter extends ListBaseAdapter<FansEntrustReturnBean.PositionsListBean> {
    public AutionTopAdapter(Context context) {
        super(context);
    }


    @Override
    public int getLayoutId() {
        return R.layout.adapter_auction_top;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        FansEntrustReturnBean.PositionsListBean bean = mDataList.get(position);
        ImageView iv_icon = holder.getView(R.id.iv_icon);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_buy_price = holder.getView(R.id.tv_buy_price);
        ImageLoaderUtils.displayRound(mContext, iv_icon, bean.getUser().getHeadUrl());
        tv_name.setText(bean.getUser().getNickname());
        tv_time.setText(TimeUtil.getDateAndTime(bean.getTrades().getPositionTime() * 1000));

//        tv_buy_price.setText(String.format(mContext.getString(R.string.buy_price), bean.getTrades().getOpenPrice()));
        tv_buy_price.setText(bean.getUser().getUid()+"");
    }

}
