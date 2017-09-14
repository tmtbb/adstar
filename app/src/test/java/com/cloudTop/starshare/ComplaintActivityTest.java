package com.cloudTop.starshare;

import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.cloudTop.starshare.been.AssetDetailsBean;
import com.cloudTop.starshare.been.HomePageInfoBean;
import com.cloudTop.starshare.been.StarListReturnBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.widget.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.cloudTop.starshare.widget.infinitecycleviewpager.HorizontalPagerAdapter;

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
                    bean.setHome_pic_tail(homePageInfoBean.getHome_last_pic_tail());
                    symbol_info.add(bean);
                    adapter = new HorizontalPagerAdapter(getContext(), symbol_info,false);
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

            }
        });
    }

    @Test
    public void getData() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarList(241,
                "", 4, 0, 0, 10, new OnAPIListener<StarListReturnBean>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(StarListReturnBean starListReturnBean) {
                        LogUtils.loge("互动列表" + starListReturnBean.toString());

                    }
                });
    }

    @Test
    public void getData1() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarList(userId,
                token, 4, 1, 1, 10, new OnAPIListener<StarListReturnBean>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(StarListReturnBean starListReturnBean) {
                        LogUtils.loge("互动列表" + starListReturnBean.toString());
                    }
                });
    }

    @Test
    public void requestBalance() {
        NetworkAPIFactoryImpl.getDealAPI().balance(new OnAPIListener<AssetDetailsBean>() {
            @Override
            public void onSuccess(AssetDetailsBean bean) {



            }

            @Override
            public void onError(Throwable ex) {

            }
        });
    }


}
