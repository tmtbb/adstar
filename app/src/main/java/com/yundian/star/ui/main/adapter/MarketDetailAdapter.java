package com.yundian.star.ui.main.adapter;

import android.content.Context;

import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.ui.main.model.TestModel;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MarketDetailAdapter extends ListBaseAdapter<TestModel> {
    public MarketDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {

    }
}
