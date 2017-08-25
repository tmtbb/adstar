package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.TodayDealReturnBean;
import com.cloudTop.starshare.utils.TimeUtil;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.greendao.GreenDaoManager;
import com.cloudTop.starshare.greendao.StarInfo;
import com.cloudTop.starshare.utils.SharePrefUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HistoryBuyAdapter extends ListBaseAdapter<TodayDealReturnBean> {
    public HistoryBuyAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_history_buy;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TodayDealReturnBean bean = mDataList.get(position);
        TextView name = holder.getView(R.id.tv_name);
        TextView price = holder.getView(R.id.tv_price);
        TextView tv_code = holder.getView(R.id.tv_code);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView date = holder.getView(R.id.tv_date);
        TextView tv_content_state = holder.getView(R.id.tv_content_state);
        TextView tv_buy_price = holder.getView(R.id.tv_buy_price);
        TextView tv_buy_num = holder.getView(R.id.tv_buy_num);
        tv_buy_price.setText(String.format("%.2f",bean.getOpenPrice()));
        tv_buy_num.setText(bean.getAmount() + "");

        date.setText(TimeUtil.getDate(bean.getOpenTime() * 1000));
        tv_time.setText(TimeUtil.getHourMinuteSecond(bean.getOpenTime() * 1000));
        List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(bean.getSymbol());
        if (starInfos != null && starInfos.size() > 0) {
            name.setText(starInfos.get(0).getName());
        }
        price.setText(String.format("%.2f",bean.getOpenPrice() * bean.getAmount()));

        int userId = SharePrefUtil.getInstance().getUserId();
        if (bean.getBuyUid() == userId) {
            tv_content_state.setText("求购");
        } else {
            tv_content_state.setText("转让");
        }
        tv_code.setText(bean.getSymbol());
    }

}
