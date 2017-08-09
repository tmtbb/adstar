package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.CommentMarketBeen;
import com.cloudTop.starshare.utils.TimeUtil;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.utils.ImageLoaderUtils;

/**
 * Created by Administrator on 2017/5/22.
 */

public class CommentMarketAdapter extends ListBaseAdapter<CommentMarketBeen.CommentsinfoBean> {
    public CommentMarketAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_comment_market;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        CommentMarketBeen.CommentsinfoBean commentsinfoBean = mDataList.get(position);
        ImageView iv_src = holder.getView(R.id.iv_src);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_content = holder.getView(R.id.tv_content);
        ImageLoaderUtils.displaySmallPhoto(mContext,iv_src,commentsinfoBean.getHead_url());
        tv_name.setText(commentsinfoBean.getNick_name());
        tv_time.setText(TimeUtil.formatData(TimeUtil.dateFormatYMD,commentsinfoBean.getCms_time()));
        tv_content.setText(commentsinfoBean.getComments());
    }
}
