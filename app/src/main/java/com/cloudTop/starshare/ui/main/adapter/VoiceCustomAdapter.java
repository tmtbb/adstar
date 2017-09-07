package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.StarQuestionBean;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.TimeUtil;

/**
 * Created by Administrator on 2017/8/18.
 */

public class VoiceCustomAdapter extends ListBaseAdapter<StarQuestionBean.CircleListBean> {
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
        StarQuestionBean.CircleListBean circleListBean = mDataList.get(position);
        TextView tv_num_time = holder.getView(R.id.tv_num_time);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_heard_num = holder.getView(R.id.tv_heard_num);
        TextView tv_tv_num_time_2 = holder.getView(R.id.tv_tv_num_time_2);
        TextView tv_ask_question = holder.getView(R.id.tv_ask_question);
        LinearLayout ll_listen = holder.getView(R.id.ll_listen);
        final ImageView imageVoiceView = holder.getView(R.id.img_voice_paly);
        ImageView img_head = holder.getView(R.id.img_head);
        if (circleListBean.getPurchased()==1){
            tv_num_time.setVisibility(View.GONE);
            tv_tv_num_time_2.setText("点击播放");
        }else {
            String expends = "";
            if (circleListBean.getC_type()==0){
                expends="花费15";
            }else if (circleListBean.getC_type()==1){
                expends="花费30";
            }else if (circleListBean.getC_type()==3){
                expends="花费60";
            }else {
                //防止传入的数据为未设置的
                expends="花费15";
            }
            tv_num_time.setVisibility(View.VISIBLE);
            SpannableStringBuilder spannable = new SpannableStringBuilder(expends);
            spannable.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_FB9938)), 2, tv_num_time.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_num_time.setText(spannable);
            tv_tv_num_time_2.setText("秒偷听");
        }
        tv_heard_num.setText(String.format(mContext.getString(R.string.heard_num),circleListBean.getS_total()));
        tv_time.setText(TimeUtil.getYMDTime(circleListBean.getAnswer_t()*1000));
        tv_name.setText(circleListBean.getNickName());
        tv_ask_question.setText(circleListBean.getUask());
        ImageLoaderUtils.displaySmallPhotoRound(mContext,img_head,circleListBean.getHeadUrl());
        ll_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenListener.onClickListen(view,position,imageVoiceView);
            }
        });
    }



    public interface OnClickListenListener{
        void onClickListen(View view,int position,ImageView imageVoiceView);
    }
}
