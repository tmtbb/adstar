package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.TodayEntrustReturnBean;

/**
 * Created by Administrator on 2017/5/25.
 */

public class TodayEntrustAdapter extends ListBaseAdapter<TodayEntrustReturnBean> {
    public TodayEntrustAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_today_entrust;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TodayEntrustReturnBean bean = mDataList.get(position);
        TextView name = holder.getView(R.id.tv_name);
        TextView price = holder.getView(R.id.tv_price);
        TextView tv_entrust_num = holder.getView(R.id.tv_entrust_num);
        TextView tv_content_ing = holder.getView(R.id.tv_content_ing);
        TextView tv_code = holder.getView(R.id.tv_code);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_bargain_num = holder.getView(R.id.tv_bargain_num);
        TextView tv_content_ed = holder.getView(R.id.tv_content_ed);

        name.setText(bean.getSymbol());
        price.setText(bean.getOpenPrice()+"");
//        tv_entrust_num.setText();
        tv_code.setText(bean.getPositionId()+"");
        tv_time.setText(bean.getPositionTime()+"");


    }

}
