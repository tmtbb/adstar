package com.yundian.star;

import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.yundian.star.been.HomePageInfoBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.widget.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.yundian.star.widget.infinitecycleviewpager.HorizontalPagerAdapter;

import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class ComplaintActivityTest extends BaseRobolectricTestCase {
    private int screenWidth;
    private HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;
    private long userId;
    private String token;
    private FrameLayout fm_layout;
    private HorizontalPagerAdapter adapter;
    private List<HomePageInfoBean.SymbolInfoBean> symbol_info;



    @Test
    public void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getAppApplication().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        initPagerData();
    }
    @Test
    public void initPagerData1() {
        NetworkAPIFactoryImpl.getInformationAPI().getHomePage(userId, token, 4, new OnAPIListener<HomePageInfoBean>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(HomePageInfoBean homePageInfoBean) {
                if (homePageInfoBean==null||homePageInfoBean.getSymbol_info() == null || homePageInfoBean.getSymbol_info().size() == 0) {

                } else {
                    symbol_info = homePageInfoBean.getSymbol_info();

                    HomePageInfoBean.SymbolInfoBean bean = new HomePageInfoBean.SymbolInfoBean();
                    bean.setPushlish_type(-1);
                    bean.setHome_pic(homePageInfoBean.getHome_last_pic());
                    symbol_info.add(bean);
                    adapter = new HorizontalPagerAdapter(getContext(), symbol_info);
                        horizontalInfiniteCycleViewPager.notifyDataSetChanged();

                }
            }
        });
    }
    @Test
    public void initPagerData() {
        NetworkAPIFactoryImpl.getInformationAPI().getHomePage(userId, token, 4, new OnAPIListener<HomePageInfoBean>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(HomePageInfoBean homePageInfoBean) {
                if (homePageInfoBean==null||homePageInfoBean.getSymbol_info() == null || homePageInfoBean.getSymbol_info().size() == 0) {

                } else {
                    symbol_info = homePageInfoBean.getSymbol_info();

                    HomePageInfoBean.SymbolInfoBean bean = new HomePageInfoBean.SymbolInfoBean();
                    bean.setPushlish_type(-1);
                    bean.setHome_pic(homePageInfoBean.getHome_last_pic());
                    symbol_info.add(bean);
                    adapter = new HorizontalPagerAdapter(getContext(), symbol_info);
                    horizontalInfiniteCycleViewPager.notifyDataSetChanged();

                }
            }
        });
    }



}
