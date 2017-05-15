package com.yundian.star.ui.main.fragment;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MarketDetailFragment extends BaseFragment {
    @Bind(R.id.ll_select_order)
    LinearLayout ll_select_order ;
    @Bind(R.id.iv_select)
    ImageView iv_select ;
    @Bind(R.id.lrv)
    LRecyclerView lrv ;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_market_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initAdpter();
    }

    private void initAdpter() {

    }
}
