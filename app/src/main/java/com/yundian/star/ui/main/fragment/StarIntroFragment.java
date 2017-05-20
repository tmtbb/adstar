package com.yundian.star.ui.main.fragment;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.StarBuyActReferralInfo;
import com.yundian.star.been.StarExperienceBeen;
import com.yundian.star.been.StarStarAchBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.activity.StarTimeShareActivity;
import com.yundian.star.ui.main.adapter.StarBuyAchAdapter;
import com.yundian.star.ui.main.adapter.StarBuyExcAdapter;
import com.yundian.star.utils.AdViewpagerUtil;
import com.yundian.star.utils.ListViewUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.MyListView;
import com.yundian.star.wxapi.MyScrollView;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/20.
 */

public class StarIntroFragment extends BaseFragment {
    private String code;
    @Bind(R.id.tv_1)
    TextView tv_1 ;
    @Bind(R.id.tv_2)
    TextView tv_2 ;
    @Bind(R.id.tv_3)
    TextView tv_3 ;
    @Bind(R.id.tv_4)
    TextView tv_4 ;
    @Bind(R.id.tv_5)
    TextView tv_5 ;
    @Bind(R.id.tv_6)
    TextView tv_6 ;
    @Bind(R.id.ll_new_buy_expeience)
    LinearLayout ll_new_buy_expeience ;
    @Bind(R.id.ll_new_buy_achievement)
    LinearLayout ll_new_buy_achievement ;
    @Bind(R.id.scroll_view)
    MyScrollView scroll_view ;
    @Bind(R.id.tv_mesure)
    TextView tv_mesure;

    private String[] adList;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_star_intro;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        init();
    }

    private void init() {
        if (getArguments()!=null){
            code = getArguments().getString(AppConstant.MARKET_DETAIL_IN_TYPE);
        }
        gitData();
        getStarExperience();
        getStarAch();
        initListener();
    }

    private void initListener() {
        scroll_view.setOnTouchListener(new TouchListenerImpl());
    }

    private void gitData() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarBrief("1001", new OnAPIListener<StarBuyActReferralInfo>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarBuyActReferralInfo info) {
                initData(info);
            }
        });
    }

    private void initData(StarBuyActReferralInfo info) {
        initPic(info);
        tv_1.setText(String.format(getString(R.string.intro_nationality),info.getNationality()));
        tv_2.setText(String.format(getString(R.string.intro_nation),info.getNation()));
        tv_3.setText(String.format(getString(R.string.intro_work),info.getWork()));
        tv_4.setText(String.format(getString(R.string.intro_constellation),info.getConstellation()));
        tv_5.setText(String.format(getString(R.string.intro_birth_day),info.getBirth()));
        tv_6.setText(String.format(getString(R.string.intro_colleage),info.getColleage()));

        RelativeLayout rl_adroot = (RelativeLayout) getActivity().findViewById(R.id.adv_root);
        ViewPager viewPager = (ViewPager)rl_adroot.findViewById(R.id.viewpager);
        LinearLayout page_indicator = (LinearLayout)rl_adroot.findViewById(R.id.ly_dots);
        AdViewpagerUtil adViewpagerUtil = new AdViewpagerUtil(getActivity(), viewPager, page_indicator, adList);
    }

    private void initPic(StarBuyActReferralInfo info) {
        adList = new String[5];
        if (!TextUtils.isEmpty(info.getPic1())){
            adList[0] = info.getPic1();
        }
        if (!TextUtils.isEmpty(info.getPic2())){
            adList[1] = info.getPic2();
        }
        if (!TextUtils.isEmpty(info.getPic3())){
            adList[2] = info.getPic3();
        }
        if (!TextUtils.isEmpty(info.getPic4())){
            adList[3] = info.getPic4();
        }
        if (!TextUtils.isEmpty(info.getPic5())){
            adList[4] = info.getPic5();
        }
    }
    private void getStarExperience() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarExperience("1001", new OnAPIListener<StarExperienceBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarExperienceBeen o) {
                if (o.getResult()==1&&o.getList()!=null){
                    initExp(o);
                }
            }
        });
    }

    private void initExp(StarExperienceBeen o) {
        StarBuyExcAdapter buyExcAndAchAdapter = new StarBuyExcAdapter(getActivity(), o.getList());
        ll_new_buy_expeience.setVisibility(View.VISIBLE);
        MyListView listExpView1 = (MyListView)ll_new_buy_expeience.findViewById(R.id.listview_buy);
        listExpView1.setAdapter(buyExcAndAchAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(listExpView1);
    }

    private void getStarAch() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarachive("1001", new OnAPIListener<StarStarAchBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarStarAchBeen o) {
                if (o.getResult()==1&&o.getList()!=null){
                    initAch(o);
                }
            }
        });
    }

    private void initAch(StarStarAchBeen o) {
        StarBuyAchAdapter buyAchAdapter = new StarBuyAchAdapter(getActivity(), o.getList());
        ll_new_buy_achievement.setVisibility(View.VISIBLE);
        MyListView listExpView2 = (MyListView)ll_new_buy_achievement.findViewById(R.id.listview_buy);
        listExpView2.setAdapter(buyAchAdapter);
        int high = ListViewUtil.setListViewHeightBasedOnChildren(listExpView2);
        ViewGroup.LayoutParams layoutParams = tv_mesure.getLayoutParams();
        LogUtils.loge(high+"最后一个listview高度");
        layoutParams.height = high ;
        tv_mesure.setLayoutParams(layoutParams);
    }

    private class TouchListenerImpl implements View.OnTouchListener{
        StarTimeShareActivity activity=(StarTimeShareActivity)getActivity() ;
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
                        activity.setFatherScrollCanScroll(true);
                    }else {
                        activity.setFatherScrollCanScroll(false);
                    }
                    if((scrollY+height)==scrollViewMeasuredHeight){
                        //scroll_view.onInterceptTouchEvent()
                        LogUtils.logd("滑动到了底部 scrollY="+scrollY);
                        LogUtils.logd("滑动到了底部 height="+height);
                        LogUtils.logd("滑动到了底部 scrollViewMeasuredHeight="+scrollViewMeasuredHeight);
                    }
                    break;

                default:
                    break;
            }
            return false;
        }

    };

}
