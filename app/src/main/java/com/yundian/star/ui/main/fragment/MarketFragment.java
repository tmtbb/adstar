package com.yundian.star.ui.main.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.base.MarketTypeFragmentAdapter;
import com.yundian.star.been.MarketTypeBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
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
        nt_title.setRightImagSrc(R.drawable.ic_home_normal);
        nt_title.setRightImagVisibility(true);
        initType();
        initListener();
    }

    private void initType() {
        NetworkAPIFactoryImpl.getInformationAPI().getMarketKype("", new OnAPIListener<MarketTypeBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(MarketTypeBeen marketTypeBeen) {
                MarketTypeBeen.ListBean optional = new MarketTypeBeen.ListBean();
                optional.setName(getResources().getString(R.string.option));
                optional.setType(0);
                listType = marketTypeBeen.getList();
                listType.add(0,optional);
                initFragment();

            }
        });
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
        tabs.setupWithViewPager(viewPager);
        MyUtils.dynamicSetTabLayoutMode(tabs);
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
