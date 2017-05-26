package com.yundian.star.ui.main.adapter;

import android.content.Context;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;

/**
 * Created by Administrator on 2017/5/25.
 */

public class TodayEntrustAdapter extends ListBaseAdapter {
    public TodayEntrustAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_today_entrust;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {

    }

}
