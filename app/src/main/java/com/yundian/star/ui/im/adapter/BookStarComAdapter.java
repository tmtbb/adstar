package com.yundian.star.ui.im.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.StarMailListBeen;
import com.yundian.star.greendao.GreenDaoManager;
import com.yundian.star.greendao.StarInfo;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.LogUtils;

import java.util.List;

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
            TextView tv_star_name = holder.getView(R.id.tv_star_name);
            ImageView iv_star_head = holder.getView(R.id.iv_star_head);
            TextView tv_star_job = holder.getView(R.id.tv_star_job);
            StarMailListBeen.DepositsinfoBean listBean= mDataList.get(position);
            LogUtils.loge("code"+listBean.getStarcode());
            List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(listBean.getStarcode());
            if (starInfos!=null&&starInfos.size()!=0){
                StarInfo starInfo = starInfos.get(0);
                ImageLoaderUtils.display(mContext,iv_star_head,starInfo.getPic_url());
                tv_star_name.setText(starInfo.getName());
            }else {
                tv_star_name.setText(listBean.getStarname());
            }


    }


}