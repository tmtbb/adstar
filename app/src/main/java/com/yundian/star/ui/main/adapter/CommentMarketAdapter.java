package com.yundian.star.ui.main.adapter;

import android.content.Context;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.CommentMarketBeen;

/**
 * Created by Administrator on 2017/5/22.
 */

public class CommentMarketAdapter extends ListBaseAdapter<CommentMarketBeen> {
    public CommentMarketAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_comment_market;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {

    }
}
