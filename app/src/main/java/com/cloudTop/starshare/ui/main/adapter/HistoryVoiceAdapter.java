package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.StarListReturnBean;

/**
 * Created by Administrator on 2017/8/18.
 */

public class HistoryVoiceAdapter extends ListBaseAdapter<StarListReturnBean.SymbolInfoBean> {

    public HistoryVoiceAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_history_voice;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
    }
}
