package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.FansHotBuyReturnBeen;
import com.yundian.star.utils.ImageLoaderUtils;


/**
 * Created by Administrator on 2017/5/22.
 */

public class AutionTopAdapter extends ListBaseAdapter<FansHotBuyReturnBeen.ListBean> {
    public AutionTopAdapter(Context context) {
        super(context);
    }



    @Override
    public int getLayoutId() {
        return R.layout.adapter_auction_top;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        FansHotBuyReturnBeen.ListBean bean = mDataList.get(position);
        ImageView iv_icon = holder.getView(R.id.iv_icon);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_buy_price = holder.getView(R.id.tv_buy_price);
        ImageLoaderUtils.display(mContext,iv_icon,bean.getHead());
        tv_name.setText(bean.getName());
        if (!TextUtils.isEmpty(bean.getTime())){
            String time = bean.getTime().substring(5);
            tv_time.setText(time);
        }
        tv_buy_price.setText(String.format(mContext.getString(R.string.buy_price),bean.getPrice()));
    }

}
