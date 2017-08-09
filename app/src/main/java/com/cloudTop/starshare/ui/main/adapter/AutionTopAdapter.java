package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.been.FansEntrustReturnBean;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.TimeUtil;


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
        ImageLoaderUtils.displaySmallPhotoRound(mContext, iv_icon, bean.getUser().getHeadUrl());
        tv_name.setText(bean.getUser().getNickname());
        tv_time.setText(TimeUtil.getDateAndTime(bean.getTrades().getPositionTime() * 1000));

        tv_buy_price.setText(String.format(mContext.getString(R.string.buy_price), bean.getTrades().getOpenPrice()));
        //tv_buy_price.setText(bean.getUser().getUid()+"");
    }

}
