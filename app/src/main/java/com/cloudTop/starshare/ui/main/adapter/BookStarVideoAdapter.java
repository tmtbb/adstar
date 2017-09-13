package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.been.StarQuestionBean;
import com.cloudTop.starshare.greendao.GreenDaoManager;
import com.cloudTop.starshare.greendao.StarInfo;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.TimeUtil;

/**
 * Created by Administrator on 2017/8/16.
 * 历史问答
 */

public class BookStarVideoAdapter extends ListBaseAdapter<StarQuestionBean.CircleListBean> {
    public BookStarVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_book_star_video;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        StarQuestionBean.CircleListBean circleListBean = mDataList.get(position);
        ImageView iv_start_head = holder.getView(R.id.iv_start_head);
        TextView tv_view_name = holder.getView(R.id.tv_start_name);
        TextView tv_view = holder.getView(R.id.tv_view);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_view_mub = holder.getView(R.id.tv_view_mub);
        TextView tv_ask_question = holder.getView(R.id.tv_ask_question);
        if (!TextUtils.isEmpty(circleListBean.getSanswer())) {
            tv_view.setVisibility(View.VISIBLE);
        } else {
            tv_view.setVisibility(View.INVISIBLE);
        }
        String code = circleListBean.getStarcode();
        StarInfo starInfo = GreenDaoManager.getInstance().queryStar(code);
        if (starInfo != null) {
            LogUtils.logd(starInfo.toString());
            LogUtils.logd("头像地址" + starInfo.getPic_url_tail());
//            ImageLoaderUtils.display(mContext,iv_start_head,starInfo.getPic1());
            ImageLoaderUtils.displayWithDefaultImg(mContext, iv_start_head, starInfo.getPic_url_tail(), R.drawable.buying_star);
            tv_view_name.setText(starInfo.getName());
        }

        tv_view_mub.setText(String.format(mContext.getString(R.string.had_view), circleListBean.getS_total()));
        tv_time.setText(TimeUtil.getYMDTime(circleListBean.getAsk_t() * 1000));
        tv_ask_question.setText(circleListBean.getUask());

    }
}
