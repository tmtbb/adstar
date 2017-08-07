package com.cloudTop.starshare.ui.main.fragment;

import android.util.DisplayMetrics;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.cloudTop.starshare.base.BaseFragment;
import com.cloudTop.starshare.been.HomePageInfoBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.widget.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.utils.DisplayUtil;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.widget.infinitecycleviewpager.HorizontalPagerAdapter;

import java.util.List;


/**
 *#75
 *#76
 * 抢购明星
 */
public class HorizontalPagerFragment extends BaseFragment {

    private long userId;
    private String token;
    private int screenWidth;
    private FrameLayout fm_layout;
    private HorizontalPagerAdapter adapter;
    private List<HomePageInfoBean.SymbolInfoBean> symbol_info;
    private HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_horizontal;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        horizontalInfiniteCycleViewPager = (HorizontalInfiniteCycleViewPager) rootView.findViewById(R.id.hicvp);
        fm_layout = (FrameLayout) rootView.findViewById(R.id.fm_layout);
        userId = SharePrefUtil.getInstance().getUserId();
        token = SharePrefUtil.getInstance().getToken();
        initPagerData();
    }

    //获取明星信息
    private void initPagerData() {
        NetworkAPIFactoryImpl.getInformationAPI().getHomePage(userId, token, 4, new OnAPIListener<HomePageInfoBean>() {
            @Override
            public void onError(Throwable ex) {
                showErrorView(fm_layout, R.drawable.error_view_comment, "当前没有相关数据");
            }

            @Override
            public void onSuccess(HomePageInfoBean homePageInfoBean) {
                if (homePageInfoBean==null||homePageInfoBean.getSymbol_info() == null || homePageInfoBean.getSymbol_info().size() == 0) {
                    showErrorView(fm_layout, R.drawable.error_view_comment, "当前没有相关数据");
                } else {
                    symbol_info = homePageInfoBean.getSymbol_info();
                    closeErrorView();
                    HomePageInfoBean.SymbolInfoBean bean = new HomePageInfoBean.SymbolInfoBean();
                    bean.setPushlish_type(-1);
                    bean.setHome_pic(homePageInfoBean.getHome_last_pic());
                    symbol_info.add(bean);
                    adapter = new HorizontalPagerAdapter(getContext(), symbol_info);
                    if (!haveInitPager){
                        initPager();
                    }else {
                        horizontalInfiniteCycleViewPager.notifyDataSetChanged();
                    }
                }
            }
        });
    }
    private boolean haveInitPager = false;
    private void initPager() {
        haveInitPager = true ;
        horizontalInfiniteCycleViewPager.setAdapter(adapter);
        horizontalInfiniteCycleViewPager.setMaxPageScale(0.85F);
        horizontalInfiniteCycleViewPager.setMinPageScale(0.6F);
        horizontalInfiniteCycleViewPager.setMinPageScaleOffset(-(screenWidth * 0.6f) + DisplayUtil.dip2px(50));
        horizontalInfiniteCycleViewPager.setInterpolator(new OvershootInterpolator());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (isVisible()){
            LogUtils.loge("onHiddenChanged——————刷新首页"+isVisible());
            initPagerData();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroy() {
        if (adapter!=null){
            adapter=null;
        }
        super.onDestroy();
    }
}
