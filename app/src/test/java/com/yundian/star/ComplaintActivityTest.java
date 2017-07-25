package com.yundian.star;

import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.yundian.star.been.CircleFriendBean;
import com.yundian.star.been.HomePageInfoBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.utils.DisplayUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.yundian.star.widget.infinitecycleviewpager.HorizontalPagerAdapter;

import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class ComplaintActivityTest extends BaseRobolectricTestCase {
    @Test
    public void testMeth(){
    }

    @Test
    public void getData() {
        NetworkAPIFactoryImpl.getInformationAPI().getAllCircleInfo(0, 10, new OnAPIListener<CircleFriendBean>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(CircleFriendBean circleFriendBean) {
                LogUtils.loge("圈子反馈" + circleFriendBean.toString());
            }
        });

    }



        private int screenWidth;
        private HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;
        private long userId;
        private String token;
        private FrameLayout fm_layout;
        private HorizontalPagerAdapter adapter;
        private List<HomePageInfoBean.SymbolInfoBean> symbol_info;





    @Test
    public void initPagerData() {
            NetworkAPIFactoryImpl.getInformationAPI().getHomePage(0, "", 4, new OnAPIListener<HomePageInfoBean>() {
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
                        adapter = new HorizontalPagerAdapter(getAppApplication(), symbol_info);
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

    @Test
    public void initPageraData() {
        NetworkAPIFactoryImpl.getInformationAPI().getHomePage(0, "", 4, new OnAPIListener<HomePageInfoBean>() {
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
                    adapter = new HorizontalPagerAdapter(getAppApplication(), symbol_info);
                    if (!haveInitPager){
                        initPager();
                    }else {
                        horizontalInfiniteCycleViewPager.notifyDataSetChanged();
                    }
                }
            }
        });
    }






}
