package com.yundian.star.ui.main.fragment;


import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.FansTopListBeen;
import com.yundian.star.been.TabEntity;
import com.yundian.star.ui.main.activity.SearchActivity;
import com.yundian.star.ui.main.activity.StarInfoActivity;
import com.yundian.star.ui.main.adapter.StarInteractionAdapter;
import com.yundian.star.widget.NormalTitleBar;
import com.yundian.star.widget.infinitecycleviewpager.MainPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/6.
 * 发现模块
 */

public class FindStarFragment extends BaseFragment {

    private CommonTabLayout tabLayout;
    private NormalTitleBar nt_title;
    private LRecyclerView lrv;
    private FrameLayout fm_layout;
    private String[] mTitles = {"抢购明星", "明星互动"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private ArrayList<FansTopListBeen.OrdersListBean> list = new ArrayList<>();
    private ArrayList<FansTopListBeen.OrdersListBean> loadList = new ArrayList<>();
    private static int mCurrentCounter = 1;
    private StarInteractionAdapter interactionAdapter;
    private static final int REQUEST_COUNT = 10;
    private String code;
    private ViewPager viewPager;

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
        initAdapter();
        initCardRecyclerView();
        getData(false,1,REQUEST_COUNT);
        //initBlurBackground();
    }

    private void initCardRecyclerView() {
        viewPager = (ViewPager) rootView.findViewById(R.id.vp_main);
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
        lrv = (LRecyclerView) rootView.findViewById(R.id.lrv);
        fm_layout = (FrameLayout) rootView.findViewById(R.id.fm_layout);
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
                switchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    private void switchTo(int position) {
        switch (position){
            case 0:
                closeErrorView();
                lrv.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                break;
            case 1:
                viewPager.setVisibility(View.GONE);
                lrv.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initAdapter() {
        interactionAdapter = new StarInteractionAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(interactionAdapter);
        lrv.setAdapter(lRecyclerViewAdapter);
        lrv.setLayoutManager(new LinearLayoutManager(getContext()));
        lrv.setLoadMoreEnabled(true);
        lrv.setNoMore(false);
        lrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(true,mCurrentCounter+1,mCurrentCounter+REQUEST_COUNT);
            }
        });
        lrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 1;
                lrv.setNoMore(false);
                getData(false,1,REQUEST_COUNT);
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),StarInfoActivity.class);
                intent.putExtra(AppConstant.STAR_CODE,"1001");
                startActivity(intent);
            }
        });
    }
    private void getData(final boolean isLoadMore,int start ,int end ) {
        ArrayList<FansTopListBeen.OrdersListBean> demoList = new ArrayList<>();
        for (int i = 0;i<10;i++){
            FansTopListBeen.OrdersListBean ordersListBean = new FansTopListBeen.OrdersListBean();
            FansTopListBeen.OrdersListBean.BuyUserBean buyUserBean= new FansTopListBeen.OrdersListBean.BuyUserBean();
            buyUserBean.setNickname("明星"+i);
            ordersListBean.setBuy_user(buyUserBean);
            demoList.add(ordersListBean);
        }
        if (demoList==null){
            lrv.setNoMore(true);
            if (!isLoadMore) {
                list.clear();
                interactionAdapter.clear();
                lrv.refreshComplete(REQUEST_COUNT);
                showErrorView(fm_layout, R.drawable.error_view_comment, "当前没有相关数据");
            }
            return;
        }
        if (isLoadMore){
            closeErrorView();
            loadList.clear();
            loadList = demoList;
            loadMoreData();
        }else {
            list.clear();
            list = demoList;
            showData();
        }
    }

    public void showData() {
        if (list.size() == 0){
            showErrorView(fm_layout, R.drawable.error_view_comment, getActivity().getResources().getString(R.string.empty_order_info));
            return;
        }else{
            closeErrorView();
        }
        interactionAdapter.clear();
        mCurrentCounter =list.size();
        lRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
        interactionAdapter.addAll(list);
        lrv.refreshComplete(REQUEST_COUNT);
    }

    private void loadMoreData() {
        if (loadList == null || list.size() == 0) {
            lrv.setNoMore(true);
        } else {
            list.addAll(loadList);
            interactionAdapter.addAll(loadList);
            mCurrentCounter += loadList.size();
            lrv.refreshComplete(REQUEST_COUNT);
        }
    }


}
