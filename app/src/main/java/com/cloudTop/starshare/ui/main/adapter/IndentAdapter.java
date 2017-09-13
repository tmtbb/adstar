package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.cloudTop.starshare.base.SuperViewHolder;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.ListBaseAdapter;
import com.cloudTop.starshare.been.EntrustReturnBeen;
import com.cloudTop.starshare.greendao.GreenDaoManager;
import com.cloudTop.starshare.greendao.StarInfo;
import com.cloudTop.starshare.utils.BuyHandleStatuUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class IndentAdapter extends ListBaseAdapter<EntrustReturnBeen.PositionsListBean> {
    public IndentAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_indent;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        EntrustReturnBeen.PositionsListBean positionsListBean = mDataList.get(position);
        TextView tv_code = holder.getView(R.id.tv_code);
        TextView tv_commission = holder.getView(R.id.tv_commission);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_current_price = holder.getView(R.id.tv_current_price);
        TextView tv_state1 = holder.getView(R.id.tv_state1);
        TextView tv_state2 = holder.getView(R.id.tv_state2);
        List<StarInfo> starInfos = GreenDaoManager.getInstance().queryStarList(positionsListBean.getSymbol());
        if (starInfos.size() != 0) {
            StarInfo starInfo = starInfos.get(0);
            tv_name.setText(starInfo.getName());
        }
        tv_commission.setText(String.valueOf(positionsListBean.getAmount()));
        tv_code.setText(String.valueOf(positionsListBean.getSymbol()));
        tv_current_price.setText(String.valueOf(positionsListBean.getOpenPrice()));

        if (positionsListBean.getBuySell() == 1) {
            tv_state1.setText("求购");
        } else if (positionsListBean.getBuySell() == -1) {
            tv_state1.setText("转让");
        }
        tv_state2.setText(BuyHandleStatuUtils.getHandleStatu(positionsListBean.getHandle()));


    }

}
