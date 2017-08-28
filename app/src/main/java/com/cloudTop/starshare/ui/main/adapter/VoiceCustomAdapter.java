package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.StarListReturnBean;

/**
 * Created by Administrator on 2017/8/18.
 */

public class VoiceCustomAdapter extends ListBaseAdapter<StarListReturnBean.SymbolInfoBean> {
    OnClickListenListener onClickListenListener;
    public VoiceCustomAdapter(Context context,OnClickListenListener onClickListenListener) {
        super(context);
        this.onClickListenListener=onClickListenListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_voice_custome;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        TextView tv_num_time = holder.getView(R.id.tv_num_time);
        LinearLayout ll_listen = holder.getView(R.id.ll_listen);
        SpannableStringBuilder spannable = new SpannableStringBuilder(tv_num_time.getText().toString());
        spannable.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_FB9938)), 2, tv_num_time.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_num_time.setText(spannable);
        ll_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenListener.onClickListen(view,position);
            }
        });
    }



    public interface OnClickListenListener{
        void onClickListen(View view,int position);
    }
}
