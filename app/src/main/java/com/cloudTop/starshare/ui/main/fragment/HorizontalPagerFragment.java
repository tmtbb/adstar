package com.cloudTop.starshare.ui.main.fragment;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.BaseFragment;
import com.cloudTop.starshare.been.HomePageInfoBean;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.utils.DisplayUtil;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.widget.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.cloudTop.starshare.widget.infinitecycleviewpager.HorizontalPagerAdapter;

import java.lang.reflect.Method;
import java.util.List;


/**
 *#75
 *#76
 * 抢购网红
 */
public class HorizontalPagerFragment extends BaseFragment {

    private long userId;
    private String token;
    private int screenWidth;
    private FrameLayout fm_layout;
    private HorizontalPagerAdapter adapter;
    private List<HomePageInfoBean.SymbolInfoBean> symbol_info;
    private HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;
    private int myAllHeight;
    private int myPartHeight;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_horizontal;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        getHasVirtualKey();
        getNoHasVirtualKey();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        horizontalInfiniteCycleViewPager = (HorizontalInfiniteCycleViewPager) rootView.findViewById(R.id.hicvp);
        fm_layout = (FrameLayout) rootView.findViewById(R.id.fm_layout);
        userId = SharePrefUtil.getInstance().getUserId();
        token = SharePrefUtil.getInstance().getToken();
        initPagerData();
    }

    //获取网红信息
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
                    boolean haveVirtualKey = false ;
                    if (myAllHeight-myPartHeight>20){
                        haveVirtualKey = true ;
                    }else {
                        haveVirtualKey = false ;
                    }
                    symbol_info = homePageInfoBean.getSymbol_info();
                    closeErrorView();
                    HomePageInfoBean.SymbolInfoBean bean = new HomePageInfoBean.SymbolInfoBean();
                    bean.setPushlish_type(-1);
                    bean.setHome_pic_tail(homePageInfoBean.getHome_last_pic_tail());
                    symbol_info.add(bean);
                    adapter = new HorizontalPagerAdapter(getContext(), symbol_info,haveVirtualKey);
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
        float PageScaleOffset = 0;
        LogUtils.loge("myHight"+myAllHeight+"..."+myPartHeight+"..."+(myAllHeight-myPartHeight));
        if ((myAllHeight-myPartHeight)>20){
            PageScaleOffset = -(screenWidth * 0.6f) + DisplayUtil.dip2px(72);
        }else {
            PageScaleOffset = -(screenWidth * 0.6f) + DisplayUtil.dip2px(50);
        }
        haveInitPager = true ;
        horizontalInfiniteCycleViewPager.setAdapter(adapter);
        horizontalInfiniteCycleViewPager.setMaxPageScale(0.85F);
        horizontalInfiniteCycleViewPager.setMinPageScale(0.6F);
        horizontalInfiniteCycleViewPager.setMinPageScaleOffset(PageScaleOffset);
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
    /**
     * 通过反射，获取包含虚拟键的整体屏幕高度
     *
     * @return
     */
    private void getHasVirtualKey() {
        int dpi = 0;
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        myAllHeight = dpi ;
    }

    /**
     * 获取屏幕尺寸，但是不包括虚拟功能高度
     *
     * @return
     */
    public void getNoHasVirtualKey() {
        /*WindowManager.LayoutParams p = getActivity().getWindow().getAttributes();
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        myPartHeight = p.height;*/
        int height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        myPartHeight = height;
    }

}
