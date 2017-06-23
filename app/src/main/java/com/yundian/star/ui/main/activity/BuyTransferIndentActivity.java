package com.yundian.star.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.ui.main.adapter.BuyTransferIndentAdapter;
import com.yundian.star.ui.main.fragment.AlreadyBoughtFragment;
import com.yundian.star.ui.main.fragment.AskToBuyMarketFragment;
import com.yundian.star.ui.main.fragment.DetailFragment;
import com.yundian.star.ui.main.fragment.IndentFragment;
import com.yundian.star.ui.main.fragment.TransferMarketFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/23.
 * 求购主页面
 */

public class BuyTransferIndentActivity extends BaseActivity {

    @Bind(R.id.tabs)
    SlidingTabLayout tabs ;
    @Bind(R.id.view_pager)
    ViewPager view_pager ;
    @Bind(R.id.back)
    ImageView back ;
    private String code;
    private String name;
    private String wid;
    private String head_url;

    private BuyTransferIndentAdapter fragmentAdapter;

    private List<String> listType = new ArrayList<>();
    private List<Fragment> mBuyTransferFragmentList;
    private int type ;
    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_transfer_indent;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        type = intent.getIntExtra(AppConstant.BUY_TRANSFER_INTENT_TYPE, 0);
        wid = intent.getStringExtra(AppConstant.STAR_WID);
        code = intent.getStringExtra(AppConstant.STAR_CODE);
        head_url = intent.getStringExtra(AppConstant.STAR_HEAD_URL);
        name = intent.getStringExtra(AppConstant.STAR_NAME);
        initType();
        initListener();
    }

    private void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //内部fragment的tab头部
    private void initType() {
        listType.add(getString(R.string.ask_to_buy));
        listType.add(getString(R.string.transfer));
        listType.add(getString(R.string.bought_have));
        listType.add(getString(R.string.indent));
        listType.add(getString(R.string.detail));
        initFragment();
    }


    private void initFragment() {
        mBuyTransferFragmentList = new ArrayList<>();
        createListFragments();
        if(fragmentAdapter==null) {
            fragmentAdapter = new BuyTransferIndentAdapter(getSupportFragmentManager(), mBuyTransferFragmentList, listType);
        }else {
            //刷新fragment
            fragmentAdapter.setFragments(getSupportFragmentManager(),mBuyTransferFragmentList,listType);
        }
        view_pager.setAdapter(fragmentAdapter);
        tabs.setViewPager(view_pager);
        setPageChangeListener();
        view_pager.setCurrentItem(type);

    }

    private void createListFragments() {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.STAR_CODE,code);
        bundle.putString(AppConstant.STAR_HEAD_URL,head_url);
        bundle.putString(AppConstant.STAR_WID,wid);
        bundle.putString(AppConstant.STAR_NAME,name);
        AskToBuyMarketFragment askToBuyMarketFragment = new AskToBuyMarketFragment();
        askToBuyMarketFragment.setArguments(bundle);

        TransferMarketFragment transferMarketFragment = new TransferMarketFragment();
        transferMarketFragment.setArguments(bundle);

        AlreadyBoughtFragment alreadyBoughtFragment =new AlreadyBoughtFragment();


        IndentFragment indentFragment = new IndentFragment();


        DetailFragment detailFragment = new DetailFragment();

        mBuyTransferFragmentList.add(askToBuyMarketFragment);
        mBuyTransferFragmentList.add(transferMarketFragment);
        mBuyTransferFragmentList.add(alreadyBoughtFragment);
        mBuyTransferFragmentList.add(indentFragment);
        mBuyTransferFragmentList.add(detailFragment);


    }

    private void setPageChangeListener() {
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
