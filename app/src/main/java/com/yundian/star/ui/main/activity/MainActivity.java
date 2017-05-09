package com.yundian.star.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.TabEntity;
import com.yundian.star.ui.main.fragment.NewsInfoFragment;
import com.yundian.star.ui.main.fragment.TestFragment;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;

import java.util.ArrayList;

import butterknife.Bind;


public class MainActivity extends BaseActivity {
    @Bind(R.id.tab_bottom_layout)
    CommonTabLayout tabLayout ;
    private String[] mTitles = {"资讯", "行情","买卖","分答","我的"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_home_normal,R.drawable.ic_home_normal,R.drawable.ic_home_normal,R.drawable.ic_home_normal,R.drawable.ic_home_normal};
    private int[] mIconSelectIds = {
            R.drawable.ic_home_selected,R.drawable.ic_home_selected, R.drawable.ic_home_selected,R.drawable.ic_home_selected,R.drawable.ic_home_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private NewsInfoFragment newsInfoFragment;
    private TestFragment testFragment2;
    private TestFragment testFragment3;
    private TestFragment testFragment4;
    private TestFragment testFragment5;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        initTab();
        checkIsLogin();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            newsInfoFragment = (NewsInfoFragment) getSupportFragmentManager().findFragmentByTag("NewsInfoFragment");
            testFragment2 = (TestFragment) getSupportFragmentManager().findFragmentByTag("TestFragment2");
            testFragment3 = (TestFragment) getSupportFragmentManager().findFragmentByTag("TestFragment3");
            testFragment4 = (TestFragment) getSupportFragmentManager().findFragmentByTag("TestFragment4");
            testFragment5 = (TestFragment) getSupportFragmentManager().findFragmentByTag("TestFragment5");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            newsInfoFragment = new NewsInfoFragment();
            testFragment2 = new TestFragment();
            testFragment3 = new TestFragment();
            testFragment4 = new TestFragment();
            testFragment5 = new TestFragment();
            transaction.add(R.id.fl_main, newsInfoFragment, "newsInfoFragment");
            transaction.add(R.id.fl_main, testFragment2, "TestFragment2");
            transaction.add(R.id.fl_main, testFragment3, "TestFragment3");
            transaction.add(R.id.fl_main, testFragment4, "TestFragment4");
            transaction.add(R.id.fl_main, testFragment5, "TestFragment5");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 入口
     * @param activity
     */
    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        LogUtils.loge("onSaveInstanceState进来了1");
        if (tabLayout != null) {
            LogUtils.loge("onSaveInstanceState进来了2");
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }
    /**
     * 切换
     */
    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                transaction.hide(testFragment2);
                transaction.hide(testFragment3);
                transaction.hide(testFragment4);
                transaction.hide(testFragment5);
                transaction.show(newsInfoFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                transaction.hide(newsInfoFragment);
                transaction.hide(testFragment3);
                transaction.hide(testFragment4);
                transaction.hide(testFragment5);
                transaction.show(testFragment2);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.hide(testFragment2);
                transaction.hide(newsInfoFragment);
                transaction.hide(testFragment4);
                transaction.hide(testFragment5);
                transaction.show(testFragment3);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                transaction.hide(testFragment2);
                transaction.hide(testFragment3);
                transaction.hide(newsInfoFragment);
                transaction.hide(testFragment5);
                transaction.show(testFragment4);
                transaction.commitAllowingStateLoss();
                break;
            case 4:
                transaction.hide(testFragment2);
                transaction.hide(testFragment3);
                transaction.hide(newsInfoFragment);
                transaction.hide(testFragment4);
                transaction.show(testFragment5);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
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
    private void checkIsLogin() {
        int firstlogin = SharePrefUtil.getInstance().getFirstlogin();
        String phoneNum = SharePrefUtil.getInstance().getPhoneNum();
        LogUtils.loge(phoneNum);
        //if (TextUtils.isEmpty(phoneNum)) { // 第一次登录, 需要走登录流程
            startActivity(new Intent(this,LoginActivity.class));
            overridePendingTransition(R.anim.activity_open_down_in,0);
        //}
    }

}
