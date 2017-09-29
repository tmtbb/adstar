package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.WithDrawCashHistoryBean;
import com.cloudTop.starshare.utils.FormatUtil;

/**
 * Created by sll on 2017/5/25.
 */

public class CashHistoryAdapter extends ListBaseAdapter<WithDrawCashHistoryBean> {

    public CashHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_cash_history_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        WithDrawCashHistoryBean item = mDataList.get(position);

        TextView time = holder.getView(R.id.tv_cash_time);
        TextView bankName = holder.getView(R.id.tv_bank_name);
        TextView cashMoney = holder.getView(R.id.tv_cash_money);
        TextView cashState = holder.getView(R.id.tv_cash_state);

        time.setText(item.getWithdrawTime());
        bankName.setText(String.format(mContext.getResources().getString(R.string.bank_end_number),
                item.getBank(), FormatUtil.getCardEnd(item.getCardNo())));
        cashMoney.setText("-" + item.getAmount());
        int state = item.getStatus();//1或0进行中，2成功，3失败
        switch (state) {
            case 1:
            case 0:
                cashState.setText("进行中");
                break;
            case 2:
                cashState.setText("提现成功");
                break;
            case 3:
                cashState.setText("提现失败");
                break;
        }

    }
}