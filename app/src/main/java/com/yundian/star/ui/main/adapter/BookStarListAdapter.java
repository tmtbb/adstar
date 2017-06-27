package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.BookingStarListBean;
import com.yundian.star.greendao.GreenDaoManager;
import com.yundian.star.greendao.StarInfo;
import com.yundian.star.utils.ImageLoaderUtils;

import java.util.List;

import static com.yundian.star.R.id.tv_star_name;

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
        TextView nameText = holder.getView(tv_star_name);
        TextView bookinged = holder.getView(R.id.tv_booking);
        TextView refuse = holder.getView(R.id.tv_booking_refuse);
        TextView talk = holder.getView(R.id.have_talk);
        ImageView iv_star_head = holder.getView(R.id.iv_star_head);
        nameText.setText(item.getStarname());
        if (item.getAppoint() == 1) {  //1-已约见,2,已拒绝,3-已完成
            bookinged.setVisibility(View.VISIBLE);
            refuse.setVisibility(View.GONE);
        } else if (item.getAppoint() == 2){
            bookinged.setVisibility(View.GONE);
            refuse.setVisibility(View.VISIBLE);
            refuse.setText("已拒绝");
        }else if (item.getAppoint() == 3){
            bookinged.setVisibility(View.GONE);
            refuse.setVisibility(View.VISIBLE);
            refuse.setText("已完成");
        }
        List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(item.getStarcode());
        if (starInfos!=null&&starInfos.size()!=0){
            StarInfo starInfo = starInfos.get(0);
            ImageLoaderUtils.display(mContext,iv_star_head,starInfo.getPic_url());
        }

    }
}