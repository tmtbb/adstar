package com.cloudTop.starshare.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.HaveStarTimeBeen;
import com.cloudTop.starshare.been.StarDetailInfoBean;
import com.cloudTop.starshare.been.StarExperienceBeen;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.adapter.HorizontalRcvAdapter;
import com.cloudTop.starshare.ui.main.adapter.StarBuyExcAdapter;
import com.cloudTop.starshare.ui.view.ShareControlerView;
import com.cloudTop.starshare.ui.wangyi.session.activity.P2PMessageActivity;
import com.cloudTop.starshare.utils.HorizontalItemDecorator;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.JudgeIdentityUtils;
import com.cloudTop.starshare.utils.ListViewUtil;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.widget.MyListView;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.session.SessionCustomization;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by Administrator on 2017/7/8.
 * 互动详情
 * 明星介绍
 */

public class StarInfoActivity extends BaseActivity implements View.OnClickListener {
    private String code;
    private TextView back;
    private TextView tv_meet_starts;
    private TextView tv_buy_time;
    private TextView tv_name;
    private TextView star_work;
    private TextView tv_exp_show;
    private LinearLayout ll_answers;
    private LinearLayout ll_voice_made;
    private LinearLayout ll_star_state;
    private ImageView imag_meesage;
    private ImageView imageView_head;
    private ImageView iv_star_bg;
    private ImageView share_button;
    private StarDetailInfoBean.ResultvalueBean resultvalue = new StarDetailInfoBean.ResultvalueBean();
    private String head_url;
    private String back_pic;
    private String describe="";
    private MyListView listExpView1;
    private StarBuyExcAdapter buyExcAndAchAdapter;
    private ShareControlerView controlerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        code = getIntent().getStringExtra(AppConstant.STAR_CODE);
        back = (TextView) findViewById(R.id.tv_back);
        tv_meet_starts = (TextView) findViewById(R.id.tv_meet_starts);
        tv_buy_time = (TextView) findViewById(R.id.tv_buy_time);
        tv_name = (TextView) findViewById(R.id.textView6);
        star_work = (TextView) findViewById(R.id.star_work);
        tv_exp_show = (TextView) findViewById(R.id.tv_exp_show);
        imag_meesage = (ImageView) findViewById(R.id.imag_meesage);
        imageView_head = (ImageView) findViewById(R.id.imageView3);
        iv_star_bg = (ImageView) findViewById(R.id.iv_star_bg);
        share_button = (ImageView) findViewById(R.id.share_button);
        ll_answers = (LinearLayout) findViewById(R.id.ll_answers);
        ll_voice_made = (LinearLayout) findViewById(R.id.ll_voice_made);
        ll_star_state = (LinearLayout) findViewById(R.id.ll_star_state);
        getHaveCodeTime();
        getStarDetailInfo();
        initListener();
        getStarExperience();
    }

    private void initListener() {
        back.setOnClickListener(this);
        tv_meet_starts.setOnClickListener(this);
        tv_buy_time.setOnClickListener(this);
        imag_meesage.setOnClickListener(this);
        //tv_right_share.setOnClickListener(this);
        share_button.setOnClickListener(this);
        tv_exp_show.setOnClickListener(this);
        ll_answers.setOnClickListener(this);
        ll_voice_made.setOnClickListener(this);
        ll_star_state.setOnClickListener(this);
    }

    private void initHorizontalRecview(StarDetailInfoBean infoBean) {
        final ArrayList<String> arrayList = new ArrayList();
        if (!TextUtils.isEmpty(infoBean.getResultvalue().getPortray1())) {
            arrayList.add(infoBean.getResultvalue().getPortray1());
        }
        if (!TextUtils.isEmpty(infoBean.getResultvalue().getPortray2())) {
            arrayList.add(infoBean.getResultvalue().getPortray2());
        }
        if (!TextUtils.isEmpty(infoBean.getResultvalue().getPortray3())) {
            arrayList.add(infoBean.getResultvalue().getPortray3());
        }
        if (!TextUtils.isEmpty(infoBean.getResultvalue().getPortray4())) {
            arrayList.add(infoBean.getResultvalue().getPortray4());
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rlv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        HorizontalRcvAdapter adapter = new HorizontalRcvAdapter(mContext, arrayList);
        recyclerView.addItemDecoration(new HorizontalItemDecorator((int) mContext.getResources().getDimension(R.dimen.dp_10)));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter.setOnImageClick(new HorizontalRcvAdapter.OnImageClick() {
            @Override
            public void onClik(int position) {
                showPopupWindow(arrayList.get(position));
            }
        });
    }


    public void getStarExperience() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarExperience(code, new OnAPIListener<StarExperienceBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarExperienceBeen o) {
                if (o.getResult() == 1 && o.getList() != null) {
                    describe = o.getList().get(0).getExperience().toString();
                    initExp(o);
                }
            }
        });
        if (SharePrefUtil.getInstance().getStatusNav_6()==0){
            SharePrefUtil.getInstance().setStatusNav_6(1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showPopupWindow();
                }
            },500);
        }
    }

    private void getStarDetailInfo() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarDetailInfo(code, new OnAPIListener<StarDetailInfoBean>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarDetailInfoBean infoBean) {
                LogUtils.loge("明星个人详情" + infoBean.toString());

                resultvalue = infoBean.getResultvalue();
                if (resultvalue==null){
                    return;
                }
                if (TextUtils.isEmpty(resultvalue.getHead_url())){
                    head_url="";
                }else {
                    head_url = resultvalue.getHead_url();
                }
                if (TextUtils.isEmpty(resultvalue.getBack_pic())){
                    back_pic="";
                }else {
                    back_pic = resultvalue.getBack_pic();
                }
                ImageLoaderUtils.displaySmallPhoto(StarInfoActivity.this, imageView_head, head_url);
                tv_name.setText(resultvalue.getStar_name());
                star_work.setText(resultvalue.getWork());
                ImageLoaderUtils.displayWithDefaultImg(StarInfoActivity.this, iv_star_bg, back_pic, R.drawable.rec_bg);
                initHorizontalRecview(infoBean);
            }
        });
    }

    private void initExp(StarExperienceBeen o) {
        buyExcAndAchAdapter = new StarBuyExcAdapter(this, o.getList());
        listExpView1 = (MyListView) findViewById(R.id.listview_exp);
        listExpView1.setVerticalScrollBarEnabled(true);
        listExpView1.setVisibility(View.VISIBLE);
        listExpView1.setAdapter(buyExcAndAchAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(listExpView1);
        buyExcAndAchAdapter.setShareAll(isAllExp);
        isAllExp = !isAllExp ;
    }

    private void showPopupWindow(String prc_url) {
        View popView = LayoutInflater.from(this).inflate(R.layout.popwindow_imegview, null);
        PhotoView photoView = (PhotoView) popView.findViewById(R.id.iv_photo);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(popView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        //ImageLoaderUtils.displayWithDefaultImg(this, photoView, prc_url, R.drawable.rec_bg);
        ImageLoaderUtils.displayWithPreviewImg(this, photoView, prc_url, R.drawable.rec_bg);
        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }
private boolean isAllExp = false ;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_meet_starts:
                if (JudgeIdentityUtils.isIdentityed(StarInfoActivity.this)) {
                    Intent intent3 = new Intent(StarInfoActivity.this, MeetStarActivity.class);
                    intent3.putExtra(AppConstant.STAR_HEAD_URL, head_url);
                    intent3.putExtra(AppConstant.STAR_NAME, resultvalue.getStar_name()+"");
                    intent3.putExtra(AppConstant.STAR_BACKGROUND_URL, back_pic);
                    intent3.putExtra(AppConstant.BUY_TRANSFER_INTENT_TYPE, resultvalue.getStar_tpye());
                    intent3.putExtra(AppConstant.STAR_CODE, code);
                    startActivity(intent3);
                }
                break;
            case R.id.tv_buy_time:
                Intent intent = new Intent(this, BuyTransferIndentActivity.class);
                intent.putExtra(AppConstant.BUY_TRANSFER_INTENT_TYPE, 0);
                intent.putExtra(AppConstant.STAR_HEAD_URL, head_url);
                intent.putExtra(AppConstant.STAR_NAME, resultvalue.getStar_name()+"");
                intent.putExtra(AppConstant.STAR_CODE, code);
                startActivity(intent);
                break;
            case R.id.imag_meesage:
                if (haveStarTime > 0) {
                    if (JudgeIdentityUtils.isIdentityed(this)) {
                        SessionCustomization customization = NimUIKit.getCommonP2PSessionCustomization();
                        P2PMessageActivity.start(StarInfoActivity.this, String.valueOf(resultvalue.getAcc_id()), code, resultvalue.getStar_name(), customization, null);
                    }
                } else {
                    ToastUtils.showShort("您未持有该明星时间，请购买");
                }
                break;
            case R.id.share_button:
                share();
                break;
            case R.id.tv_exp_show:
                if (!TextUtils.isEmpty(describe)){
                    buyExcAndAchAdapter.setShareAll(isAllExp);
                }
                if (isAllExp){
                    tv_exp_show.setText("收起更多");
                }else {
                    tv_exp_show.setText("显示更多");
                }
                isAllExp = !isAllExp;
                break;
            case R.id.ll_answers:
                Intent intent1 = new Intent(this,AnswersActivity.class);
                intent1.putExtra("star_code",code);
                intent1.putExtra("star_name",resultvalue.getStar_name());
                startActivity(intent1);
                break;
            case R.id.ll_voice_made:
                Intent intent2 = new Intent(this,VoiceCustomActivity.class);
                intent2.putExtra("star_code",code);
                intent2.putExtra("star_name",resultvalue.getStar_name());
                startActivity(intent2);
                break;
            case R.id.ll_star_state:

                break;

        }
    }

    private int haveStarTime;

    private void getHaveCodeTime() {
        NetworkAPIFactoryImpl.getInformationAPI().getHaveStarTime(SharePrefUtil.getInstance().getUserId(),
                code, new OnAPIListener<HaveStarTimeBeen>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(HaveStarTimeBeen haveStarTimeBeen) {
                        haveStarTime = haveStarTimeBeen.getStar_time();
                        LogUtils.loge("持有时间" + haveStarTimeBeen.toString());
                    }
                });
    }
    private void share() {
        controlerView = new ShareControlerView(this, mContext, umShareListener);
        String webUrl = "http://www.zhongyuliying.com/"+"?uid="+ SharePrefUtil.getInstance().getUserId()
                +"&star_code="+code;
        String title = resultvalue.getStar_name()+" 正在星享时光出售TA的时间";
        String text = "文本";
        controlerView.setText(text);
        controlerView.setStarName(resultvalue.getStar_name());
        controlerView.setStarWork(resultvalue.getWork());
        controlerView.setWebUrl(webUrl);
        controlerView.setDescribe(describe);
        controlerView.setTitle(title);
        controlerView.setImageurl(resultvalue.getHead_url());
        controlerView.showShareView(rootView);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShort("分享成功啦");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort("分享失败了");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort("分享取消了");
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }
    public static void goToStarInfoActivity(Context context, String code){
        Intent intent = new Intent(context,StarInfoActivity.class);
        intent.putExtra(AppConstant.STAR_CODE,code);
        context.startActivity(intent);
    }
    private void showPopupWindow() {
        View popView = LayoutInflater.from(this).inflate(R.layout.popwindow_navijation_3, null);
        final ImageView imageView = (ImageView) popView.findViewById(R.id.navigation_5_2);
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(popView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x33000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (controlerView!=null&&controlerView.isOpen() ==true) {
                    controlerView.closeShareView();
                    return true;
                } else {
                    return super.onKeyDown(keyCode, event);
                }
        }
        return super.onKeyDown(keyCode, event);

    }
}
