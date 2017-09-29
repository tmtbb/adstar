package com.cloudTop.starshare.ui.main.activity;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.ui.view.ShareControlerView;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.been.StarBuyActReferralInfo;
import com.cloudTop.starshare.been.StarExperienceBeen;
import com.cloudTop.starshare.been.StarStarAchBeen;
import com.cloudTop.starshare.been.StartShellTimeBeen;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.StarBuyAchAdapter;
import com.cloudTop.starshare.ui.main.adapter.StarBuyExcAdapter;
import com.cloudTop.starshare.utils.AdViewpagerUtil;
import com.cloudTop.starshare.utils.CheckLoginUtil;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.ListViewUtil;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.widget.MyListView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/18.
 * 广告点击网红求购页面
 * 资讯购买页
 */

public class NewsStarBuyActivity extends BaseActivity {

    private String code;
    private String name;
    @Bind(R.id.nl_title)
    NormalTitleBar nl_title;
    @Bind(R.id.tv_1)
    TextView tv_1;
    @Bind(R.id.tv_2)
    TextView tv_2;
    @Bind(R.id.tv_3)
    TextView tv_3;
    @Bind(R.id.tv_4)
    TextView tv_4;
    @Bind(R.id.tv_5)
    TextView tv_5;
    @Bind(R.id.tv_6)
    TextView tv_6;
    @Bind(R.id.ll_new_buy_expeience)
    LinearLayout ll_new_buy_expeience;
    @Bind(R.id.ll_new_buy_achievement)
    LinearLayout ll_new_buy_achievement;
    @Bind(R.id.scroll_view)
    NestedScrollView scroll_view;
    @Bind(R.id.tv_mesure)
    TextView tv_mesure;
    @Bind(R.id.tv_shell_time)
    TextView tv_shell_time;
    @Bind(R.id.root_view)
    FrameLayout rootView;

    private String[] adList;
    private AdViewpagerUtil adViewpagerUtil;
    @Bind(R.id.img_adv)
    ImageView img_adv;
    private String weibo_index_id;
    private String head_url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_newstar_buy;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nl_title.setBackVisibility(true);
        nl_title.setRightImagVisibility(true);
//        nl_title.setRightImagSrc(R.drawable.share);
        Intent intent = getIntent();
        code = intent.getStringExtra(AppConstant.STAR_CODE);
        name = intent.getStringExtra(AppConstant.STAR_NAME);
        nl_title.setTitleText(String.format(getString(R.string.name_code),name,code));
        LogUtils.loge("网红求购页面code" + code);
        gitData();
        getStarExperience();
        getStarAch();
        initlistener();
    }

    private void initlistener() {
        nl_title.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        nl_title.setOnRightImagListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                share();
//            }
//        });
    }

    private void share() {
        ShareControlerView controlerView = new ShareControlerView(this, mContext, umShareListener);
        String webUrl = "https://mobile.umeng.com/";
        String title = "This is web title";
        String describe = "描述描述";
        String text = "文本";
        controlerView.setText(text);
        controlerView.setWebUrl(webUrl);
        controlerView.setDescribe(describe);
        controlerView.setTitle(title);
        controlerView.showShareView(rootView);
    }

    private void getStarAch() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarachive(code, new OnAPIListener<StarStarAchBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarStarAchBeen o) {
                if (o.getResult() == 1 && o.getList() != null) {
                    initAch(o);
                }
            }
        });
    }

    private void initAch(StarStarAchBeen o) {
        StarBuyAchAdapter buyAchAdapter = new StarBuyAchAdapter(NewsStarBuyActivity.this, o.getList());
        ll_new_buy_achievement.setVisibility(View.VISIBLE);
        MyListView listExpView2 = (MyListView) ll_new_buy_achievement.findViewById(R.id.listview_buy);
        TextView textAch = (TextView) ll_new_buy_achievement.findViewById(R.id.tv_content);
        textAch.setText(getString(R.string.oneself_intro_achievement));
        listExpView2.setAdapter(buyAchAdapter);
        int high = ListViewUtil.setListViewHeightBasedOnChildren(listExpView2);
        ViewGroup.LayoutParams layoutParams = tv_mesure.getLayoutParams();
        LogUtils.loge(high + "最后一个listview高度");
        layoutParams.height = high;
        tv_mesure.setLayoutParams(layoutParams);
    }

    private void getStarExperience() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarExperience(code, new OnAPIListener<StarExperienceBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarExperienceBeen o) {
                if (o.getResult() == 1 && o.getList() != null) {
                    initExp(o);
                }
            }
        });
    }

    private void initExp(StarExperienceBeen o) {
        StarBuyExcAdapter buyExcAndAchAdapter = new StarBuyExcAdapter(NewsStarBuyActivity.this, o.getList());
        ll_new_buy_expeience.setVisibility(View.VISIBLE);
        MyListView listExpView1 = (MyListView) ll_new_buy_expeience.findViewById(R.id.listview_buy);
        listExpView1.setAdapter(buyExcAndAchAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(listExpView1);
    }


    private void gitData() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarBrief(code, new OnAPIListener<StarBuyActReferralInfo>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarBuyActReferralInfo info) {
                initData(info);
            }
        });
        NetworkAPIFactoryImpl.getInformationAPI().getStarShellTime(code, new OnAPIListener<StartShellTimeBeen>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("网红总时间"+ex.toString());
            }

            @Override
            public void onSuccess(StartShellTimeBeen startShellTimeBeen) {
                LogUtils.loge("网红总时间"+startShellTimeBeen.toString());
                tv_shell_time.setText(String.valueOf(startShellTimeBeen.getStar_time())+"秒");
            }
        });
    }

    private void initData(StarBuyActReferralInfo info) {
        weibo_index_id = info.getWeibo_index_id();
        head_url = info.getHead_url_tail();
        tv_1.setText(String.format(getString(R.string.intro_nationality),info.getNationality()+""));
        tv_2.setText(String.format(getString(R.string.intro_nation),info.getNation()+""));
        tv_3.setText(String.format(getString(R.string.intro_work),info.getWork()+""));
        tv_4.setText(String.format(getString(R.string.intro_constellation),info.getConstellaction()+""));
        tv_5.setText(String.format(getString(R.string.intro_birth_day),info.getBirth()+""));
        tv_6.setText(String.format(getString(R.string.intro_colleage),info.getColleage()+""));
        ImageLoaderUtils.display(this,img_adv,info.getPic_url_tail());
       /* RelativeLayout rl_adroot = (RelativeLayout)findViewById(R.id.adv_root);
        ViewPager viewPager = (ViewPager)rl_adroot.findViewById(R.id.viewpager);
        LinearLayout page_indicator = (LinearLayout)rl_adroot.findViewById(R.id.ly_dots);
        adViewpagerUtil = new AdViewpagerUtil(this, viewPager, page_indicator, adList);*/
    }


    @OnClick(R.id.tv_to_buy)
    public void toBuy(){
        if (CheckLoginUtil.checkLogin(this)){
            Intent intent = new Intent(this,StarTimeShareActivity.class);
            intent.putExtra(AppConstant.STAR_CODE,code);
            intent.putExtra(AppConstant.STAR_NAME,name);
            intent.putExtra(AppConstant.STAR_WID,weibo_index_id);
            intent.putExtra(AppConstant.STAR_HEAD_URL,head_url);
            startActivity(intent);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(NewsStarBuyActivity.this,"分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(NewsStarBuyActivity.this,"分享失败了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(NewsStarBuyActivity.this,"分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
