package com.yundian.star.ui.main.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.base.MarketTypeFragmentAdapter;
import com.yundian.star.been.MarketTypeBeen;
import com.yundian.star.been.TabEntity;
import com.yundian.star.ui.main.fragment.MarketDetailFragment;
import com.yundian.star.utils.DisplayUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.MyUtils;
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
    TabLayout tabs ;
    @Bind(R.id.view_pager)
    ViewPager viewPager ;
    @Bind(R.id.tab_bottom_layout)
    CommonTabLayout tabLayout ;
    @Bind(R.id.scroll_view)
    ScrollView scroll_view ;
    @Bind(R.id.ll_tab_fargment)
    LinearLayout ll_tab_fargment ;

    private String[] mTitles = {"求购", "转让","粉丝见面会","自选"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_home_normal,R.drawable.ic_home_normal,R.drawable.ic_home_normal,R.drawable.ic_home_normal,R.drawable.ic_home_normal};
    private int[] mIconSelectIds = {
            R.drawable.ic_home_selected,R.drawable.ic_home_selected, R.drawable.ic_home_selected,R.drawable.ic_home_selected,R.drawable.ic_home_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<MarketTypeBeen.ListBean> listType = new ArrayList<>();
    private MarketTypeFragmentAdapter fragmentAdapter;
    private int heightPixels;

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

    private void initType() {
        for (int i =0;i<4;i++){
            MarketTypeBeen.ListBean optional = new MarketTypeBeen.ListBean();
            optional.setName(i+"测试");
            optional.setType(i);
            listType.add(i,optional);
        }
        initFragmentHigh();
        initFragment();
        scroll_view.smoothScrollTo(0,0);
    }

    private void initFragmentHigh() {
        WindowManager wm = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        heightPixels = metrics.heightPixels;



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            Rect outRect = new Rect();
            getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
            ViewGroup.LayoutParams layoutParams = ll_tab_fargment.getLayoutParams();
            layoutParams.height = outRect.height()-DisplayUtil.dip2px(48);
            ll_tab_fargment.setLayoutParams(layoutParams);
            LogUtils.loge("第一个"+ heightPixels +"第二个"+outRect.height());
        }
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
        List<Fragment> mNewsFragmentList = new ArrayList<>();
        for (int i = 0; i < listType.size(); i++) {
            mNewsFragmentList.add(createListFragments(listType.get(i)));
        }
        if(fragmentAdapter==null) {
            fragmentAdapter = new MarketTypeFragmentAdapter(getSupportFragmentManager(), mNewsFragmentList, listType);
        }else{
            //刷新fragment
            fragmentAdapter.setFragments(getSupportFragmentManager(),mNewsFragmentList,listType);
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
