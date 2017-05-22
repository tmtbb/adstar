package com.yundian.star.ui.main.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.TabEntity;
import com.yundian.star.ui.main.adapter.StartTimeShareAdpter;
import com.yundian.star.ui.main.fragment.AuctionMarketFragment;
import com.yundian.star.ui.main.fragment.FansHotFragment;
import com.yundian.star.ui.main.fragment.StarIntroFragment;
import com.yundian.star.ui.main.fragment.TestFragment;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by Administrator on 2017/5/20.
 */

public class StarTimeShareActivity extends BaseActivity {

    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.tabs)
    SlidingTabLayout tabs ;
    @Bind(R.id.view_pager)
    ViewPager viewPager ;
    @Bind(R.id.tab_bottom_layout)
    CommonTabLayout tabLayout ;

    private String[] mTitles = {"求购", "转让","粉丝见面会","自选"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_home_normal,R.drawable.ic_home_normal,R.drawable.ic_home_normal,R.drawable.ic_home_normal,R.drawable.ic_home_normal};
    private int[] mIconSelectIds = {
            R.drawable.ic_home_selected,R.drawable.ic_home_selected, R.drawable.ic_home_selected,R.drawable.ic_home_selected,R.drawable.ic_home_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<String> listType = new ArrayList<>();
    private StartTimeShareAdpter fragmentAdapter;
    private int heightPixels;
    private List<Fragment> mNewsFragmentList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_time_share;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nt_title.setBackVisibility(true);
        nt_title.setTitleText("柳岩");
        initType();
        initTab();

    }


    //内部fragment的tab头部
    private void initType() {
        listType.add(getString(R.string.star_time_intro));
        listType.add(getString(R.string.star_time_fans));
        listType.add(getString(R.string.star_time_auction));
        listType.add(getString(R.string.star_time_comment));
        initFragmentHigh();
        initFragment();
    }

    private void initFragmentHigh() {
        WindowManager wm = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        heightPixels = metrics.heightPixels;



    }


    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        switch (position) {
            case 0:
                startActivity(AskToBuyActivity.class);
                break;
            case 1:
                startActivity(AskToBuyActivity.class);
                break;
            case 2:
                startActivity(AskToBuyActivity.class);
                break;
            case 3:
                startActivity(AskToBuyActivity.class);
                break;
            case 4:
                startActivity(AskToBuyActivity.class);
                break;
            default:
                break;
        }
    }

    private void initFragment() {
        mNewsFragmentList = new ArrayList<>();
        createListFragments();
        if(fragmentAdapter==null) {
            fragmentAdapter = new StartTimeShareAdpter(getSupportFragmentManager(), mNewsFragmentList, listType);
        }else{
            //刷新fragment
            fragmentAdapter.setFragments(getSupportFragmentManager(), mNewsFragmentList,listType);
        }
        viewPager.setAdapter(fragmentAdapter);
        tabs.setViewPager(viewPager);
        //MyTabLayoutUtils.dynamicSetTabLayoutMode(tabs);
        setPageChangeListener();
    }


    private void createListFragments() {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.MARKET_DETAIL_IN_TYPE,"1001");
        StarIntroFragment starIntroFragment = new StarIntroFragment();
        starIntroFragment.setArguments(bundle);
        mNewsFragmentList.add(starIntroFragment);
        FansHotFragment fansHotFragment = new FansHotFragment();
        fansHotFragment.setArguments(bundle);

        AuctionMarketFragment auctionMarketFragment =new AuctionMarketFragment();
        auctionMarketFragment.setArguments(bundle);
        TestFragment testFragment3 = new TestFragment();


        mNewsFragmentList.add(fansHotFragment);
        mNewsFragmentList.add(auctionMarketFragment);
        mNewsFragmentList.add(testFragment3);


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
