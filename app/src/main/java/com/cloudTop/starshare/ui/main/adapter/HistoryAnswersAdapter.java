package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.StarListReturnBean;

/**
 * Created by Administrator on 2017/8/16.
 * 历史问答
 */

public class HistoryAnswersAdapter extends ListBaseAdapter<StarListReturnBean.SymbolInfoBean> {
    public HistoryAnswersAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_history_answer;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
    }
}
