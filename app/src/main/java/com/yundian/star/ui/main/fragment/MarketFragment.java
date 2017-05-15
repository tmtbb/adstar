package com.yundian.star.ui.main.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.base.BaseFragmentAdapter;
import com.yundian.star.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MarketFragment extends BaseFragment {
    @Bind(R.id.tabs)
    TabLayout tabs ;
    @Bind(R.id.view_pager)
    ViewPager viewPager ;
    private BaseFragmentAdapter fragmentAdapter;
    //private String[] tabTitle = {"自选","艺人","体育明星","网红","知名海外人士"};

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_market;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initFragment();
    }

    private void initFragment() {
        List<String> tabTitle = new ArrayList<>();
        tabTitle.add("自选");
        tabTitle.add("艺人");
        tabTitle.add("体育明星");
        tabTitle.add("网红");
        tabTitle.add("知名海外人士");
        List<Fragment> mNewsFragmentList = new ArrayList<>();
        for (int i = 0; i < tabTitle.size(); i++) {
            mNewsFragmentList.add(createListFragments(i));
        }

        if(fragmentAdapter==null) {
            fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, tabTitle);
        }else{
            //刷新fragment
            fragmentAdapter.setFragments(getChildFragmentManager(),mNewsFragmentList,tabTitle);
        }
        viewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(viewPager);
        MyUtils.dynamicSetTabLayoutMode(tabs);
        setPageChangeListener();
    }


    private MarketDetailFragment createListFragments(int type) {
        MarketDetailFragment fragment = new MarketDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.MARKET_DETAIL_ID, String.valueOf(type));
        fragment.setArguments(bundle);
        return fragment;
    }

    private void setPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
