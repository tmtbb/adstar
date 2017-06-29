package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.MoneyDetailListBean;
import com.yundian.star.utils.BuyHandleStatuUtils;
import com.yundian.star.utils.TimeUtil;

import java.util.Date;

import static com.yundian.star.R.id.tv_star_name;

/**
 * Created by sll on 2017/5/25.
 */

public class MoneyBagDetailAdapter extends ListBaseAdapter<MoneyDetailListBean> {
    public MoneyBagDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_money_bag_detail;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        MoneyDetailListBean item = mDataList.get(position);
        TextView name = holder.getView(tv_star_name);
        TextView time = holder.getView(R.id.tv_money_detail_time);
        TextView status = holder.getView(R.id.tv_trust_status);
        TextView money = holder.getView(R.id.tv_deal_money);
        TextView date = holder.getView(R.id.tv_money_detail_date);
        TextView week = holder.getView(R.id.tv_money_detail_week);

        String plus_minus = "+";
        if (item.getRecharge_type() == 0) {  //充值记录
            plus_minus = "+";
          name.setText(BuyHandleStatuUtils.getRechargeType(item.getDepositType()));
        }else if (item.getRecharge_type() == 1){  //约见记录
            plus_minus = "-";
        }else if (item.getRecharge_type() == 2){  //聊天记录

        }

        name.setText(item.getDepositName());
        week.setText(TimeUtil.getWeekNumber(item.getDepositTime(), TimeUtil.dateFormatYMDHMS));
        Date stringByFormat = TimeUtil.getDateByFormat(item.getDepositTime(), TimeUtil.dateFormatYMDHMS);
        time.setText(TimeUtil.getStringByFormat(stringByFormat, TimeUtil.dateFormatHMS));
        money.setText(item.getAmount() + "");
        date.setText(TimeUtil.getStringByFormat(stringByFormat, TimeUtil.dateFormatM_D));
        String statusType = "";
        switch (item.getStatus()) {
            case 1:
                statusType = "处理中";
                break;
            case 2:
                statusType = "成功";
                break;
            case 3:
                statusType = "失败";
                break;
            case 4:
                statusType = "充值取消";
                break;

        }
        status.setText(statusType);

    }
}