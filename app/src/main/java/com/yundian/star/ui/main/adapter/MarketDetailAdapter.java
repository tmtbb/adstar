package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.OptionsStarListBeen;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.LogUtils;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MarketDetailAdapter extends ListBaseAdapter<OptionsStarListBeen.ListBean> {
    public MarketDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_market_detail;
    }


    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        OptionsStarListBeen.ListBean item = mDataList.get(position);
        ImageView imageView = holder.getView(R.id.image_star);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_code = holder.getView(R.id.tv_code);
        TextView tv_price = holder.getView(R.id.tv_price);
        TextView tv_updown = holder.getView(R.id.tv_updown);
        ImageLoaderUtils.display(mContext,imageView,item.getHead());
        tv_name.setText(item.getName());
        tv_code.setText(item.getStarcode());
        tv_price.setText(String.valueOf(item.getPrice()));
        LogUtils.loge("指数"+item.getUpdown());
        if (item.getUpdown()>=0){
            tv_updown.setBackgroundColor(mContext.getResources().getColor(R.color.color_CB4232));
        }else {
            tv_updown.setBackgroundColor(mContext.getResources().getColor(R.color.color_18B03F));
        }
        tv_updown.setText(String.valueOf(item.getUpdown()));
    }
}
