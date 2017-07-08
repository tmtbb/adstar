package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.FansTopListBeen;

/**
 * Created by Administrator on 2017/7/7.
 */

public class StarInteractionAdapter extends ListBaseAdapter<FansTopListBeen.OrdersListBean> {
    private int[] random_bg = {
            R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5
            , R.drawable.bg_6, R.drawable.bg_7, R.drawable.bg_8, R.drawable.bg_9, R.drawable.bg_10
            , R.drawable.bg_11
    };

    public StarInteractionAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_star_interaction;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        FansTopListBeen.OrdersListBean ordersListBean = mDataList.get(position);
        String nickname = ordersListBean.getBuy_user().getNickname();
        ImageView img_head = holder.getView(R.id.img_head);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_info = holder.getView(R.id.tv_info);
        tv_name.setText(nickname);
        RelativeLayout rl_bg = holder.getView(R.id.rl_bg);
        int i = position % 11;
        rl_bg.setBackgroundResource(random_bg[i]);
    }
}
