package com.yundian.star.ui.main.adapter;

import android.content.Context;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;

/**
 * Created by Administrator on 2017/5/24.
 */

public class IndentAdapter extends ListBaseAdapter {
    public IndentAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_indent;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {

    }

}
