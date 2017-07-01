package com.yundian.star.ui.main.fragment;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.been.StarBuyActReferralInfo;
import com.yundian.star.been.StarExperienceBeen;
import com.yundian.star.been.StarStarAchBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.main.adapter.StarBuyAchAdapter;
import com.yundian.star.ui.main.adapter.StarBuyExcAdapter;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.ListViewUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.widget.MyListView;

/**
 * Created by Administrator on 2017/5/20.
 */

public class StarIntroFragment extends BaseFragment {
    private String code;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_4;
    private TextView tv_3;
    private TextView tv_5;
    private TextView tv_6;
    private TextView tv_mesure;
    private LinearLayout ll_new_buy_expeience;
    private LinearLayout ll_new_buy_achievement;
    private NestedScrollView scroll_view;
    private ImageView img_adv;


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
            code = getArguments().getString(AppConstant.STAR_CODE);
            LogUtils.loge("明星code"+code);
        }
        initFindById();
        gitData();
        getStarExperience();
        getStarAch();
    }

    private void initFindById() {
        tv_1 = (TextView) rootView.findViewById(R.id.tv_1);
        tv_2 = (TextView) rootView.findViewById(R.id.tv_2);
        tv_4 = (TextView) rootView.findViewById(R.id.tv_4);
        tv_3 = (TextView) rootView.findViewById(R.id.tv_3);
        tv_5 = (TextView) rootView.findViewById(R.id.tv_5);
        tv_6 = (TextView) rootView.findViewById(R.id.tv_6);
        tv_mesure = (TextView) rootView.findViewById(R.id.tv_mesure);
        ll_new_buy_expeience = (LinearLayout) rootView.findViewById(R.id.ll_new_buy_expeience);
        ll_new_buy_achievement = (LinearLayout) rootView.findViewById(R.id.ll_new_buy_achievement);
        scroll_view = (NestedScrollView) rootView.findViewById(R.id.scroll_view);
        img_adv = (ImageView) rootView.findViewById(R.id.img_adv);
    }


    private void gitData() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarBrief(code, new OnAPIListener<StarBuyActReferralInfo>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarBuyActReferralInfo info) {
                if (info==null){
                    return;
                }
                initData(info);
            }
        });
    }

    private void initData(StarBuyActReferralInfo info) {
        tv_1.setText(String.format(getActivity().getString(R.string.intro_nationality),info.getNationality()+""));
        tv_2.setText(String.format(getActivity().getString(R.string.intro_nation),info.getNation()+""));
        tv_3.setText(String.format(getActivity().getString(R.string.intro_work),info.getWork()+""));
        tv_4.setText(String.format(getActivity().getString(R.string.intro_constellation),info.getConstellaction()+""));
        tv_5.setText(String.format(getActivity().getString(R.string.intro_birth_day),info.getBirth()+""));
        tv_6.setText(String.format(getActivity().getString(R.string.intro_colleage),info.getColleage()+""));
        ImageLoaderUtils.displayWithDefaultImg(getActivity(),img_adv,info.getPic_url(),R.drawable.infos_news_defolat);
    }
    private void getStarExperience() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarExperience(code, new OnAPIListener<StarExperienceBeen>() {
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
        NetworkAPIFactoryImpl.getInformationAPI().getStarachive(code, new OnAPIListener<StarStarAchBeen>() {
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
        TextView textAch = (TextView)ll_new_buy_achievement.findViewById(R.id.tv_content);
        textAch.setText(getActivity().getString(R.string.oneself_intro_achievement));
        listExpView2.setAdapter(buyAchAdapter);
        int high = ListViewUtil.setListViewHeightBasedOnChildren(listExpView2);
        ViewGroup.LayoutParams layoutParams = tv_mesure.getLayoutParams();
        LogUtils.loge(high+"最后一个listview高度");
        layoutParams.height = high ;
        tv_mesure.setLayoutParams(layoutParams);
    }

    //生命周期控制
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
