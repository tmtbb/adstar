package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.StarQuestionBean;
import com.cloudTop.starshare.utils.TimeUtil;

/**
 * Created by Administrator on 2017/8/18.
 */

public class HistoryVoiceAdapter extends ListBaseAdapter<StarQuestionBean.CircleListBean> {
    OnClickListenListener onClickListenListener;
    public HistoryVoiceAdapter(Context context,OnClickListenListener onClickListenListener) {
        super(context);
        this.onClickListenListener=onClickListenListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_history_voice;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        StarQuestionBean.CircleListBean circleListBean = mDataList.get(position);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_num_time = holder.getView(R.id.tv_num_time);
        TextView tv_num_time_2 = holder.getView(R.id.tv_num_time_2);
        TextView tv_heard_num = holder.getView(R.id.tv_heard_num);
        TextView tv_ask_question = holder.getView(R.id.tv_ask_question);
        final ImageView img_voice_paly = holder.getView(R.id.img_voice_paly);
        LinearLayout ll_listen = holder.getView(R.id.ll_listen);
        tv_time.setText(TimeUtil.getYMDTime(circleListBean.getAsk_t()*1000));
        tv_heard_num.setText(String.format(mContext.getString(R.string.heard_num),circleListBean.getS_total()));
        tv_ask_question.setText(circleListBean.getUask());
        String expends = "";
        if (circleListBean.getC_type()==0){
            expends="15";
        }else if (circleListBean.getC_type()==1){
            expends="30";
        }else if (circleListBean.getC_type()==3){
            expends="60";
        }
        if (circleListBean.getAnswer_t()==0){
            tv_num_time_2.setText("s定制语音(未回复)");
        }else {
            tv_num_time_2.setText("s定制语音");
        }
        tv_num_time.setText(expends);
        ll_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenListener.onClickListen(view,position,img_voice_paly);
            }
        });
    }
    public interface OnClickListenListener{
        void onClickListen(View view, int position, ImageView imageVoiceView);
    }
}
