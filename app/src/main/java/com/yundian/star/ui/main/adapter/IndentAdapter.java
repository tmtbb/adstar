package com.yundian.star.ui.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.ListBaseAdapter;
import com.yundian.star.base.SuperViewHolder;
import com.yundian.star.been.EntrustReturnBeen;
import com.yundian.star.greendao.GreenDaoManager;
import com.yundian.star.greendao.StarInfo;

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
        TextView tv_state = holder.getView(R.id.tv_state);
        List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(positionsListBean.getSymbol());
        if (starInfos.size()!=0){
            StarInfo starInfo = starInfos.get(0);
            tv_name.setText(starInfo.getName());        }
        tv_commission.setText(String.valueOf(positionsListBean.getAmount()));
        tv_code.setText(String.valueOf(positionsListBean.getSymbol()));
        tv_current_price.setText(String.valueOf(positionsListBean.getOpenPrice()));
        if (positionsListBean.getBuySell()==1){
            tv_state.setText("委托购买");
        }else {
            tv_state.setText("委托转让");
        }


    }

}
