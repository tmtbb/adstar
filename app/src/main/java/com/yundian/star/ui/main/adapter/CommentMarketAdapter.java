package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.CommentMarketBeen;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.TimeUtil;

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
