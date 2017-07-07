package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.WithDrawCashHistoryBean;
import com.yundian.star.utils.FormatUtil;

import butterknife.Bind;

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

        time.setText(item.getWithdrawTime());
        bankName.setText(String.format(mContext.getResources().getString(R.string.bank_end_number),
                item.getBank(), FormatUtil.getCardEnd(item.getCardNo())));
        cashMoney.setText("-" + item.getAmount());

    }
}