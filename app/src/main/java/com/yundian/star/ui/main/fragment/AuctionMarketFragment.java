package com.yundian.star.ui.main.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/22.
 */

public class AuctionMarketFragment extends BaseFragment {

    @Bind(R.id.iv_src)
    ImageView iv_src ;
    @Bind(R.id.tv_residue_time)
    TextView tv_residue_time ;
    @Bind(R.id.tv_have_name)
    TextView tv_have_name ;
    @Bind(R.id.tv_have_time)
    TextView tv_have_time ;



    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_auction_market;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }
}
