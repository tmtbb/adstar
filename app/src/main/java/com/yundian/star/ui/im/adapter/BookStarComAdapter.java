package com.yundian.star.ui.im.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.StarMailListBeen;

/**
 * Created by sll on 2017/5/24.
 */

public class BookStarComAdapter extends ListBaseAdapter<StarMailListBeen.DepositsinfoBean> {
    public BookStarComAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_book_star_com;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
            StarMailListBeen.DepositsinfoBean listBean= mDataList.get(position);
            TextView titleText = holder.getView(R.id.tv_star_name);
            //ImageView iv_star_head = holder.getView(R.id.iv_star_head);
            titleText.setText(listBean.getFaccid());
    }


}