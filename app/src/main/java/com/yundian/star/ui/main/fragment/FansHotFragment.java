package com.yundian.star.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

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



    private FansHotBuyFragment hotBuyFragment;
    private FansHotBuyFragment transferFragment;
    private String code;
    private FrameLayout fl_fans_hot_content;
    private FrameLayout rl_buy;
    private ImageView img_1;
    private ImageView img_2;
    private FrameLayout rl_transfer;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_fans_hot;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initData();
        initListener();
    }

    private void initData() {
        fl_fans_hot_content = (FrameLayout) rootView.findViewById(R.id.fl_fans_hot_content);
        rl_buy = (FrameLayout) rootView.findViewById(R.id.rl_buy);
        img_1 = (ImageView) rootView.findViewById(R.id.img_1);
        img_2 = (ImageView) rootView.findViewById(R.id.img_2);
        rl_transfer = (FrameLayout) rootView.findViewById(R.id.rl_transfer);

    }

    private void initListener() {
        buyHotOnclick();
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
    }

    @OnClick(R.id.rl_buy)
    public void buyHotOnclick(){
        img_1.setVisibility(View.VISIBLE);
        img_2.setVisibility(View.GONE);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.show(hotBuyFragment);
        transaction.hide(transferFragment);
        transaction.commitAllowingStateLoss();
    }

    @OnClick(R.id.rl_transfer)
    public void transferHotOnclick(){
        img_1.setVisibility(View.GONE);
        img_2.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.show(transferFragment);
        transaction.hide(hotBuyFragment);
        transaction.commitAllowingStateLoss();
    }


}
