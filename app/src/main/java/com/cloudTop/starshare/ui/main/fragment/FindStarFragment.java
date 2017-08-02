package com.cloudTop.starshare.ui.main.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cloudTop.starshare.base.BaseFragment;
import com.cloudTop.starshare.ui.main.activity.MainActivity;
import com.cloudTop.starshare.ui.main.activity.SearchActivity;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.been.TabEntity;
import com.cloudTop.starshare.utils.SharePrefUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/6.
 * 发现模块
 */

public class FindStarFragment extends BaseFragment {

    private CommonTabLayout tabLayout;
    private NormalTitleBar nt_title;
    private String[] mTitles = {"抢购明星", "明星互动"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private HorizontalPagerFragment horizontalPagerFragment;
    private StartInteractFragment startInteractFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_find_star;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
    }

    @Override
    protected void initView() {
        initFindById();
        initTab();
        initListener();
    }

    private void initListener() {
        switchTo(0);
        nt_title.getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchActivity.class);
            }
        });
    }

    private void initFindById() {
        tabLayout = (CommonTabLayout) rootView.findViewById(R.id.com_tab);
        nt_title = (NormalTitleBar) rootView.findViewById(R.id.nt_title);
        nt_title.setBackVisibility(false);
        nt_title.setTitleText(R.string.app_name);
        nt_title.setRightImagSrc(R.drawable.search);
        nt_title.setRightImagVisibility(true);
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    private void switchTo(int position) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                transaction.show(horizontalPagerFragment);
                transaction.hide(startInteractFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                if (SharePrefUtil.getInstance().getStatusNav_2()==0){
                    SharePrefUtil.getInstance().setStatusNav_2(1);
                    Message obtain = Message.obtain();
                    obtain.what=1;
                    obtain.obj=2;
                    ((MainActivity)getActivity()).handler.sendMessageDelayed(obtain,500);
                }
                transaction.show(startInteractFragment);
                transaction.hide(horizontalPagerFragment);
                transaction.commitAllowingStateLoss();
                break;
        }
    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            horizontalPagerFragment = new HorizontalPagerFragment();
            startInteractFragment = new StartInteractFragment();
            transaction.add(R.id.fm_layout, horizontalPagerFragment, "HorizontalPagerFragment");
            transaction.add(R.id.fm_layout, startInteractFragment, "StartInteractFragment");
        } else {
            horizontalPagerFragment = (HorizontalPagerFragment) getChildFragmentManager().findFragmentByTag("HorizontalPagerFragment");
            startInteractFragment = (StartInteractFragment) getChildFragmentManager().findFragmentByTag("StartInteractFragment");
        }
        transaction.commit();
    }
}
