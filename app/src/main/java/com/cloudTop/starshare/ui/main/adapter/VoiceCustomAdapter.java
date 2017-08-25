package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.StarListReturnBean;

/**
 * Created by Administrator on 2017/8/18.
 */

public class VoiceCustomAdapter extends ListBaseAdapter<StarListReturnBean.SymbolInfoBean> {

    public VoiceCustomAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_voice_custome;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TextView tv_num_time = holder.getView(R.id.tv_num_time);
        SpannableStringBuilder spannable = new SpannableStringBuilder(tv_num_time.getText().toString());
        spannable.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_FB9938)), 2, tv_num_time.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_num_time.setText(spannable);
    }
}
