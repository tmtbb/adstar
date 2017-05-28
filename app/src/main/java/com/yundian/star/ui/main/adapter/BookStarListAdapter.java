package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.BookingStarListBean;

/**
 * Created by sll on 2017/5/24.
 */

public class BookStarListAdapter extends ListBaseAdapter<BookingStarListBean> {
    public BookStarListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_book_star_list;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        BookingStarListBean item = mDataList.get(position);
        TextView nameText = holder.getView(R.id.tv_star_name);
        nameText.setText(item.getStarname());

    }
}