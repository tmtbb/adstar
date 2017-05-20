package com.yundian.star.ui.main.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.TabEntity;
import com.yundian.star.ui.main.adapter.StartTimeShareAdpter;
import com.yundian.star.ui.main.fragment.StarIntroFragment;
import com.yundian.star.ui.main.fragment.TestFragment;
import com.yundian.star.utils.DisplayUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.NormalTitleBar;
import com.yundian.star.wxapi.MyScrollView;

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
    @Bind(R.id.scroll_view)
    MyScrollView scroll_view ;
    @Bind(R.id.ll_tab_fargment)
    LinearLayout ll_tab_fargment ;

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
        initListener();
    }

    private void initListener() {
        scroll_view.setOnTouchListener(new TouchListenerImpl());
    }

    //内部fragment的tab头部
    private void initType() {
        listType.add(getString(R.string.star_time_intro));
        listType.add(getString(R.string.star_time_fans));
        listType.add(getString(R.string.star_time_auction));
        listType.add(getString(R.string.star_time_comment));
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
            layoutParams.height = outRect.height()- DisplayUtil.dip2px(48);
            //layoutParams.height = outRect.height();
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
        StarIntroFragment starIntroFragment = new StarIntroFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.MARKET_DETAIL_IN_TYPE,"1001");
        starIntroFragment.setArguments(bundle);
        mNewsFragmentList.add(starIntroFragment);

        TestFragment testFragment1 = new TestFragment();
        TestFragment testFragment2= new TestFragment();
        TestFragment testFragment3 = new TestFragment();


        mNewsFragmentList.add(testFragment1);
        mNewsFragmentList.add(testFragment2);
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


    private class TouchListenerImpl implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_MOVE:
                    int scrollY=view.getScrollY();
                    int height=view.getHeight();
                    int scrollViewMeasuredHeight=scroll_view.getChildAt(0).getMeasuredHeight();
                    if(scrollY==0){
                        LogUtils.logd("滑动到了顶端 view.getScrollY()="+scrollY);
                    }
                    if((scrollY+height)==scrollViewMeasuredHeight){
                        scroll_view.setScanScroll(false);
                        //scroll_view.onInterceptTouchEvent()
                        LogUtils.logd("滑动到了底部 scrollY="+scrollY);
                        LogUtils.logd("滑动到了底部 height="+height);
                        LogUtils.logd("滑动到了底部 scrollViewMeasuredHeight="+scrollViewMeasuredHeight);
                    }else {
                        scroll_view.setScanScroll(true);
                    }
                    break;

                default:
                    break;
            }
            return false;
        }

    };
    public void setFatherScrollCanScroll(boolean isCan){
        if (isCan){
            scroll_view.setScanScroll(true);
        }else {
            scroll_view.setScanScroll(false);
        }
    }
}
