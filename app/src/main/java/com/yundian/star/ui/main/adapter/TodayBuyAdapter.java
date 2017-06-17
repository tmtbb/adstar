package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.TodayDealReturnBean;
import com.yundian.star.been.TodayEntrustReturnBean;
import com.yundian.star.greendao.GreenDaoManager;
import com.yundian.star.greendao.StarInfo;
import com.yundian.star.utils.TimeUtil;

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

        date.setText(TimeUtil.getDate(bean.getOpenTime() * 1000));
        tv_time.setText(TimeUtil.getHourMinuteSecond(bean.getOpenTime() * 1000));
        List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(bean.getSymbol());
        if (starInfos != null && starInfos.size() > 0) {
            name.setText(starInfos.get(0).getName());
        }
        price.setText(bean.getOpenPrice() + "");
        tv_code.setText(bean.getSymbol());
    }
}
