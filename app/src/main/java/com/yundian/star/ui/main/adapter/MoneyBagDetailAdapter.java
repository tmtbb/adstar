package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.MoneyDetailListBean;
import com.yundian.star.greendao.GreenDaoManager;
import com.yundian.star.greendao.StarInfo;
import com.yundian.star.utils.BuyHandleStatuUtils;
import com.yundian.star.utils.TimeUtil;

import java.util.Date;
import java.util.List;


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
        TextView name = holder.getView(R.id.tv_star_name);
        TextView time = holder.getView(R.id.tv_money_detail_time);
        TextView status = holder.getView(R.id.tv_trust_status);
        TextView money = holder.getView(R.id.tv_deal_money);
        TextView date = holder.getView(R.id.tv_money_detail_date);
        TextView week = holder.getView(R.id.tv_money_detail_week);

        String plus_minus;
        if (item.getRecharge_type() == 0) {  //充值记录
            plus_minus = "+";
            name.setText(BuyHandleStatuUtils.getRechargeType(item.getDepositType()));
            status.setText(BuyHandleStatuUtils.getRechargeStatus(item.getStatus()));
        } else {
            plus_minus = "-";
            List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(item.getTransaction_id());
            if (starInfos.size() != 0) {
                StarInfo starInfo = starInfos.get(0);
                name.setText(String.format(mContext.getString(R.string.name_code), starInfo.getName(), item.getTransaction_id()));
            }

            if (item.getRecharge_type() == 1) {  //约见记录
                status.setText("约见");
            } else if (item.getRecharge_type() == 2) {  //聊天记录
                status.setText("星聊");
            }
        }

        week.setText(TimeUtil.getWeekNumber(item.getDepositTime(), TimeUtil.dateFormatYMDHMS));
        Date stringByFormat = TimeUtil.getDateByFormat(item.getDepositTime(), TimeUtil.dateFormatYMDHMS);
        time.setText(TimeUtil.getStringByFormat(stringByFormat, TimeUtil.dateFormatHMS));
        money.setText(plus_minus + item.getAmount() + "");
        date.setText(TimeUtil.getStringByFormat(stringByFormat, TimeUtil.dateFormatM_D));
    }
}