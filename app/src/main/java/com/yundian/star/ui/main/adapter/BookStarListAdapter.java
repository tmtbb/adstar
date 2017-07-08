package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.MeetStarInfoBean;
import com.yundian.star.utils.ImageLoaderUtils;


/**
 * Created by sll on 2017/5/24.
 */

public class BookStarListAdapter extends ListBaseAdapter<MeetStarInfoBean> {
    public BookStarListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_booking_status_list;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final MeetStarInfoBean item = mDataList.get(position);
        TextView nameText = holder.getView(R.id.tv_star_name);
        TextView bookinged = holder.getView(R.id.tv_booking);
        ImageView iv_star_head = holder.getView(R.id.iv_star_head);
        TextView tv_booking_time = holder.getView(R.id.tv_booking_time);

        //struct	约见详情列表, 其中meet_type 1-待确认 2-已拒绝 3-已完成 4-已同意；

        nameText.setText(item.getStar_name());
        if (item.getMeet_type() == 0) {
            bookinged.setVisibility(View.GONE);
        } else if (item.getMeet_type() == 1) {  //0-没有约见，不显示 1-待确认 2-已拒绝 3-已完成 4-已同意；
            bookinged.setVisibility(View.VISIBLE);
            bookinged.setText("待确认");
            bookinged.setBackground(mContext.getResources().getDrawable(R.drawable.booking_star_status_refuse));
        } else if (item.getMeet_type() == 2) {
            bookinged.setVisibility(View.VISIBLE);
            bookinged.setText("已拒绝");
            bookinged.setBackground(mContext.getResources().getDrawable(R.drawable.booking_star_status_refuse));
        } else if (item.getMeet_type() == 3) {
            bookinged.setVisibility(View.VISIBLE);
            bookinged.setText("已完成");
            bookinged.setBackground(mContext.getResources().getDrawable(R.drawable.booking_star_status_refuse));
        } else if (item.getMeet_type() == 4) {
            bookinged.setVisibility(View.VISIBLE);
            bookinged.setText("已同意");
            bookinged.setBackground(mContext.getResources().getDrawable(R.drawable.booking_star_status));
        }
        tv_booking_time.setText(String.format(mContext.getResources().getString(R.string.booking_time_status), item.getMeet_time()));
        ImageLoaderUtils.display(mContext, iv_star_head, item.getStar_pic());

    }
}