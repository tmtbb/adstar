package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.utils.ImageLoaderUtils;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MarketDetailAdapter extends ListBaseAdapter<StarListReturnBean.SymbolInfoBean> {
    private int[] random_bg = {
            R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5
            , R.drawable.bg_6, R.drawable.bg_7, R.drawable.bg_8, R.drawable.bg_9, R.drawable.bg_10
            , R.drawable.bg_11
    };
    private String starTypeInfo[] = {"网红", "娱乐明星", "体育明星", "艺人", "海外知名人士", "测试"};
    public MarketDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_market_detail;
    }


    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        StarListReturnBean.SymbolInfoBean item = mDataList.get(position);
        ImageView imageView = holder.getView(R.id.img_head);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_info = holder.getView(R.id.tv_info);
        TextView tv_price = holder.getView(R.id.tv_preice);
        ImageLoaderUtils.displaySmallPhoto(mContext,imageView,item.getPic());
        tv_name.setText(item.getName());
        tv_info.setText(starTypeInfo[item.getStar_type()]);
        tv_price.setText(String.format("%.2f",item.getCurrentPrice()));
        RelativeLayout rl_bg = holder.getView(R.id.rl_bg);
        int i = position % 11;
        rl_bg.setBackgroundResource(random_bg[i]);
    }
}
