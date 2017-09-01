package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.StarQuestionBean;
import com.cloudTop.starshare.utils.TimeUtil;

/**
 * Created by Administrator on 2017/8/16.
 * 历史问答
 */

public class HistoryAnswersAdapter extends ListBaseAdapter<StarQuestionBean.CircleListBean> {
    public HistoryAnswersAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_history_answer;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        StarQuestionBean.CircleListBean circleListBean = mDataList.get(position);
        TextView tv_view = holder.getView(R.id.tv_view);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_view_mub = holder.getView(R.id.tv_view_mub);
        TextView tv_ask_question = holder.getView(R.id.tv_ask_question);
        if (!TextUtils.isEmpty(circleListBean.getSanswer())){
            tv_view.setVisibility(View.VISIBLE);
        }else {
            tv_view.setVisibility(View.INVISIBLE);
        }
        tv_view_mub.setText(String.format(mContext.getString(R.string.had_view),circleListBean.getS_total()));
        tv_time.setText(TimeUtil.getYMDTime(circleListBean.getAsk_t()*1000));
        tv_ask_question.setText(circleListBean.getUask());

    }
}
