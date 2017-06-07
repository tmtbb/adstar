package com.yundian.star.ui.main.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
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
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/18.
 * 广告点击明星求购页面
 * 资讯购买页
 */

public class NewsStarBuyActivity extends BaseActivity {

    private String code;
    private String name;
    @Bind(R.id.nl_title)
    NormalTitleBar nl_title;
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
    ScrollView scroll_view ;
    @Bind(R.id.tv_mesure)
    TextView tv_mesure;
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
        nl_title.setRightImagSrc(R.drawable.share);
        Intent intent = getIntent();
        code = intent.getStringExtra(AppConstant.STAR_CODE);
        LogUtils.loge("明星求购页面code"+code);
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
        nl_title.setOnRightImagListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    private void share() {
        UMImage thumb =  new UMImage(this, R.drawable.welcome_bg);
        //检测安装微信没
        new ShareAction(NewsStarBuyActivity.this).withText(getString(R.string.app_name))
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QQ,SHARE_MEDIA.SINA)
                .setCallback(umShareListener)
                .withMedia(thumb)
                .open();

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
        StarBuyAchAdapter buyAchAdapter = new StarBuyAchAdapter(NewsStarBuyActivity.this, o.getList());
        ll_new_buy_achievement.setVisibility(View.VISIBLE);
        MyListView listExpView2 = (MyListView)ll_new_buy_achievement.findViewById(R.id.listview_buy);
        TextView textAch = (TextView)ll_new_buy_achievement.findViewById(R.id.tv_content);
        textAch.setText(getString(R.string.oneself_intro_achievement));
        listExpView2.setAdapter(buyAchAdapter);
        int high = ListViewUtil.setListViewHeightBasedOnChildren(listExpView2);
        ViewGroup.LayoutParams layoutParams = tv_mesure.getLayoutParams();
        LogUtils.loge(high+"最后一个listview高度");
        layoutParams.height = high ;
        tv_mesure.setLayoutParams(layoutParams);
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
        StarBuyExcAdapter buyExcAndAchAdapter = new StarBuyExcAdapter(NewsStarBuyActivity.this, o.getList());
        ll_new_buy_expeience.setVisibility(View.VISIBLE);
        MyListView listExpView1 = (MyListView)ll_new_buy_expeience.findViewById(R.id.listview_buy);
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
    }

    private void initData(StarBuyActReferralInfo info) {
        weibo_index_id = info.getWeibo_index_id();
        head_url = info.getHead_url();
        name = info.getName();
        nl_title.setTitleText(info.getName());

        tv_1.setText(String.format(getString(R.string.intro_nationality),info.getNationality()));
        tv_2.setText(String.format(getString(R.string.intro_nation),info.getNation()));
        tv_3.setText(String.format(getString(R.string.intro_work),info.getWork()));
        tv_4.setText(String.format(getString(R.string.intro_constellation),info.getConstellaction()));
        tv_5.setText(String.format(getString(R.string.intro_birth_day),info.getBirth()));
        tv_6.setText(String.format(getString(R.string.intro_colleage),info.getColleage()));
        ImageLoaderUtils.display(this,img_adv,info.getPic_url());
       /* RelativeLayout rl_adroot = (RelativeLayout)findViewById(R.id.adv_root);
        ViewPager viewPager = (ViewPager)rl_adroot.findViewById(R.id.viewpager);
        LinearLayout page_indicator = (LinearLayout)rl_adroot.findViewById(R.id.ly_dots);
        adViewpagerUtil = new AdViewpagerUtil(this, viewPager, page_indicator, adList);*/
    }


    @OnClick(R.id.tv_to_buy)
    public void toBuy(){
        Intent intent = new Intent(this,StarTimeShareActivity.class);
        intent.putExtra(AppConstant.STAR_CODE,code);
        intent.putExtra(AppConstant.STAR_NAME,name);
        intent.putExtra(AppConstant.STAR_WID,weibo_index_id);
        intent.putExtra(AppConstant.STAR_HEAD_URL,head_url);
        startActivity(intent);
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
