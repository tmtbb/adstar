package com.yundian.star.ui.main.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.ui.main.fragment.FansHotBuyFragment;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/21.
 * 委托排行榜
 */

public class AuctionRankingListActivity extends BaseActivity {
    private FansHotBuyFragment hotBuyFragment;
    private FansHotBuyFragment transferFragment;
    private String code;
    private FrameLayout fl_fans_hot_content;
    private FrameLayout rl_buy;
    private ImageView img_1;
    private ImageView img_2;
    private FrameLayout rl_transfer;
    private NormalTitleBar nt_title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_auction_ranking;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        code = getIntent().getStringExtra(AppConstant.STAR_CODE);
        initData();
        initFragment();
        initListener();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initListener() {
        buyHotOnclick();
    }

    private void initFindById() {

    }
    private void initData() {
        fl_fans_hot_content = (FrameLayout) findViewById(R.id.fl_fans_hot_content);
        rl_buy = (FrameLayout) findViewById(R.id.rl_buy);
        img_1 = (ImageView) findViewById(R.id.img_1);
        img_2 = (ImageView) findViewById(R.id.img_2);
        rl_transfer = (FrameLayout) findViewById(R.id.rl_transfer);
        nt_title = (NormalTitleBar) findViewById(R.id.nt_title);
        nt_title.setTitleText(getResources().getString(R.string.entrust_hot));
        nt_title.setBackVisibility(true);
    }
    private void initFragment() {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        Bundle bundle1 = new Bundle();
        bundle1.putString(AppConstant.STAR_CODE,code);
        bundle1.putInt(AppConstant.FANS_HOT_TYPE,1);
        hotBuyFragment = new FansHotBuyFragment();
        hotBuyFragment.setArguments(bundle1);
        Bundle bundle2= new Bundle();
        bundle2.putString(AppConstant.STAR_CODE,code);
        bundle2.putInt(AppConstant.FANS_HOT_TYPE,2);
        transferFragment = new FansHotBuyFragment();
        transferFragment.setArguments(bundle2);
        transaction.add(R.id.fl_fans_hot_content, hotBuyFragment,"FansTopListBeen");
        transaction.add(R.id.fl_fans_hot_content, transferFragment,"FansHotTransferFragment");
       /* if (savedInstanceState==null){

        }else {
            hotBuyFragment = (FansHotBuyFragment) getSupportFragmentManager().findFragmentByTag("FansTopListBeen");
            transferFragment = (FansHotBuyFragment) getSupportFragmentManager().findFragmentByTag("FansHotTransferFragment");
        }*/
        transaction.commit();
    }

    @OnClick(R.id.rl_buy)
    public void buyHotOnclick(){
        img_1.setVisibility(View.VISIBLE);
        img_2.setVisibility(View.GONE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(hotBuyFragment);
        transaction.hide(transferFragment);
        transaction.commitAllowingStateLoss();
    }

    @OnClick(R.id.rl_transfer)
    public void transferHotOnclick(){
        img_1.setVisibility(View.GONE);
        img_2.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(transferFragment);
        transaction.hide(hotBuyFragment);
        transaction.commitAllowingStateLoss();
    }
}
