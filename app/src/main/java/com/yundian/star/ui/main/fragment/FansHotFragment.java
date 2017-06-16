package com.yundian.star.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.utils.LogUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/22.
 * 粉丝热度榜
 */

public class FansHotFragment extends BaseFragment {

    @Bind(R.id.fl_fans_hot_content)
    FrameLayout fl_fans_hot_content ;

    @Bind(R.id.rl_buy)
    RelativeLayout rl_buy ;

    @Bind(R.id.rl_transfer)
    RelativeLayout rl_transfer;

    private FansHotBuyFragment hotBuyFragment;
    private FansHotBuyFragment transferFragment;
    private String code;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_fans_hot;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            code = getArguments().getString(AppConstant.STAR_CODE);
            LogUtils.loge("粉丝热度榜code"+code);
        }
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (savedInstanceState==null){
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
        }else {
            hotBuyFragment = (FansHotBuyFragment) getChildFragmentManager().findFragmentByTag("FansTopListBeen");
            transferFragment = (FansHotBuyFragment) getChildFragmentManager().findFragmentByTag("FansHotTransferFragment");
        }
        transaction.commit();
        buyHotOnclick();
    }

    @OnClick(R.id.rl_buy)
    public void buyHotOnclick(){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.show(hotBuyFragment);
        transaction.hide(transferFragment);
        transaction.commitAllowingStateLoss();
    }

    @OnClick(R.id.rl_transfer)
    public void transferHotOnclick(){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.show(transferFragment);
        transaction.hide(hotBuyFragment);
        transaction.commitAllowingStateLoss();
    }


}
