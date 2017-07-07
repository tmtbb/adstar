package com.yundian.star.ui.main.fragment;


import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.TabEntity;
import com.yundian.star.ui.main.activity.SearchActivity;
import com.yundian.star.widget.NormalTitleBar;
import com.yundian.star.widget.infinitecycleviewpager.MainPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/6.
 */

public class FindStarFragment extends BaseFragment {

    private CommonTabLayout tabLayout;
    private NormalTitleBar nt_title;
    private String[] mTitles = {"抢购明星", "明星互动"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_find_star;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initFindById();
        initTab();
        initListener();
        initCardRecyclerView();
        //initBlurBackground();
    }

    private void initCardRecyclerView() {
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.vp_main);
        viewPager.setAdapter(new MainPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
    }

    private void initListener() {
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
            mTabEntities.add(new TabEntity(mTitles[i],0,0));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

}
