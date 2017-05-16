package com.yundian.star.ui.main.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.base.BaseFragmentAdapter;
import com.yundian.star.ui.main.activity.SearchActivity;
import com.yundian.star.utils.MyUtils;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MarketFragment extends BaseFragment {
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.tabs)
    TabLayout tabs ;
    @Bind(R.id.view_pager)
    ViewPager viewPager ;
    private BaseFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_market;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        nt_title.setBackVisibility(false);
        nt_title.setTitleText(R.string.star_hot);
        nt_title.setRightImagSrc(R.drawable.ic_home_normal);
        nt_title.setRightImagVisibility(true);
        initFragment();
        initListener();
    }

    private void initListener() {
        nt_title.getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchActivity.class);
            }
        });
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
