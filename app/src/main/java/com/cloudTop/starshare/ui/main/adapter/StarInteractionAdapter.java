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
 * Created by Administrator on 2017/7/7.
 */

public class StarInteractionAdapter extends ListBaseAdapter<StarListReturnBean.SymbolInfoBean> {
    private int[] random_bg = {
            R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5
            , R.drawable.bg_6, R.drawable.bg_7, R.drawable.bg_8, R.drawable.bg_9, R.drawable.bg_10
            , R.drawable.bg_11
    };
    private String starTypeInfo[] = {"网红", "娱乐明星", "体育明星", "艺人", "海外知名人士", "测试"};

    public StarInteractionAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_star_interaction;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        StarListReturnBean.SymbolInfoBean ordersListBean = mDataList.get(position);
        String nickname = ordersListBean.getName();
        ImageView img_head = holder.getView(R.id.img_head);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_info = holder.getView(R.id.tv_info);
        ImageLoaderUtils.displaySmallPhoto(mContext,img_head,ordersListBean.getPic());
        tv_name.setText(nickname);
        tv_info.setText(starTypeInfo[ordersListBean.getStar_type()]);
        RelativeLayout rl_bg = holder.getView(R.id.rl_bg);
        int i = position % 11;
        rl_bg.setBackgroundResource(random_bg[i]);
    }
}
