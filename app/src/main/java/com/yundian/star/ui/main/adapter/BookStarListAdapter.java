package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.session.SessionCustomization;
import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.BookingStarListBean;
import com.yundian.star.ui.im.activity.StarCommunicationBookActivity;
import com.yundian.star.ui.wangyi.session.activity.P2PMessageActivity;
import com.yundian.star.utils.ToastUtils;

import static android.R.id.list;

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
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final BookingStarListBean item = mDataList.get(position);
        TextView nameText = holder.getView(R.id.tv_star_name);
        TextView bookinged = holder.getView(R.id.tv_booking);
        TextView refuse = holder.getView(R.id.tv_booking_refuse);
        TextView talk = holder.getView(R.id.have_talk);
        nameText.setText(item.getStarname());
        if (item.getStatus() == 1) {
            bookinged.setVisibility(View.VISIBLE);
            refuse.setVisibility(View.GONE);
        } else {
            bookinged.setVisibility(View.GONE);
            refuse.setVisibility(View.VISIBLE);
        }
        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("聊一聊");
                SessionCustomization customization = NimUIKit.getCommonP2PSessionCustomization();
                P2PMessageActivity.start(mContext, item.getFaccid(),item.getStarcode(), customization, null);
            }
        });

    }
}