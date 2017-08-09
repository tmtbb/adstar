package com.cloudTop.starshare.ui.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cloudTop.starshare.base.BaseFragment;
import com.cloudTop.starshare.ui.main.activity.SearchActivity;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.flyco.tablayout.SlidingTabLayout;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.base.MarketTypeFragmentAdapter;
import com.cloudTop.starshare.been.MarketTypeBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/15.
 * 行情明星热度
 */

public class MarketFragment extends BaseFragment {
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.tabs)
    SlidingTabLayout tabs;
    @Bind(R.id.view_pager)
    ViewPager viewPager ;
    private MarketTypeFragmentAdapter fragmentAdapter;
    private List<MarketTypeBeen.ListBean> listType = new ArrayList<>();

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
        nt_title.setRightImagSrc(R.drawable.search);
        nt_title.setRightImagVisibility(true);
        initType();
        initListener();
    }

    private void initType() {
        MarketTypeBeen.ListBean optional1 = new MarketTypeBeen.ListBean();
        optional1.setName(getResources().getString(R.string.option));
        optional1.setType(0);
        MarketTypeBeen.ListBean optional2 = new MarketTypeBeen.ListBean();
        optional2.setName(getResources().getString(R.string.ming_xing));
        optional2.setType(1);
        listType.add(optional1);
        listType.add(optional2);
        initFragment();
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
        List<Fragment> mNewsFragmentList = new ArrayList<>();
        for (int i = 0; i < listType.size(); i++) {
            mNewsFragmentList.add(createListFragments(listType.get(i)));
        }
        if(fragmentAdapter==null) {
            fragmentAdapter = new MarketTypeFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, listType);
        }else{
            //刷新fragment
            fragmentAdapter.setFragments(getChildFragmentManager(),mNewsFragmentList,listType);
        }
        viewPager.setAdapter(fragmentAdapter);
        tabs.setViewPager(viewPager);
        setPageChangeListener();
    }


    private MarketDetailFragment createListFragments(MarketTypeBeen.ListBean bean) {
        MarketDetailFragment fragment = new MarketDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.MARKET_DETAIL_NAME,bean.getName());
        bundle.putInt(AppConstant.MARKET_DETAIL_TYPE,bean.getType());
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
