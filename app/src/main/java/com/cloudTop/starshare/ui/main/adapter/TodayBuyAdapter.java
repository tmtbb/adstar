package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.TodayDealReturnBean;
import com.cloudTop.starshare.greendao.GreenDaoManager;
import com.cloudTop.starshare.greendao.StarInfo;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.TimeUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class TodayBuyAdapter extends ListBaseAdapter<TodayDealReturnBean> {
    public TodayBuyAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_today_buy;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TodayDealReturnBean bean = mDataList.get(position);
        TextView name = holder.getView(R.id.tv_name);
        TextView price = holder.getView(R.id.tv_price);
        TextView tv_code = holder.getView(R.id.tv_code);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView date = holder.getView(R.id.tv_date);
        TextView number = holder.getView(R.id.tv_num);
        TextView tv_buy_text = holder.getView(R.id.tv_buy_text);
        TextView dealMoney = holder.getView(R.id.tv_volume_transaction);

        date.setText(TimeUtil.getDate(bean.getOpenTime() * 1000));
        tv_time.setText(TimeUtil.getHourMinuteSecond(bean.getOpenTime() * 1000));
        List<StarInfo> starInfos = GreenDaoManager.getInstance().queryStarList(bean.getSymbol());
        if (starInfos != null && starInfos.size() > 0) {
            name.setText(starInfos.get(0).getName());
        }
        price.setText(String.format("%.2f",bean.getOpenPrice()));
        tv_code.setText(bean.getSymbol());
        number.setText(String.valueOf(bean.getAmount()));

        int userId = SharePrefUtil.getInstance().getUserId();
        if (bean.getBuyUid() == userId){
            tv_buy_text.setText("求购");
        }else{
            tv_buy_text.setText("转让");
        }
        dealMoney.setText(String.format("%.2f",bean.getAmount() * bean.getOpenPrice()));
    }
}
