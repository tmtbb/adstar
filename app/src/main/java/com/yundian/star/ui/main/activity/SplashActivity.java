package com.yundian.star.ui.main.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.testin.agent.Bugout;
import com.testin.agent.BugoutConfig;
import com.yundian.star.R;
import com.yundian.star.app.AppApplication;
import com.yundian.star.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/5.
 */

public class SplashActivity extends BaseActivity {
    private final int BASIC_PERMISSION_REQUEST_CODE = 100;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.tv_name)
    TextView tvName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
    //  Bugout.init(this, "1664ea921dcbe122834e440f7f584e2e", "yingyongbao");
    //  initBugOut();
        initGeTui();
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.3f, 1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(tvName, alpha, scaleX, scaleY);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(ivLogo, alpha, scaleX, scaleY);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator1, objectAnimator2);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(2000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startActivity(MainActivity.class);
                finish();
                overridePendingTransition(R.anim.act_in_from_right, R.anim.act_out_from_left);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    private void initBugOut() {
        BugoutConfig config = new BugoutConfig.Builder(mContext)
                //.withAppKey(a15147f843a6cdb414b8a61b6f5191b8)     // 您的应用的项目ID,如果已经在 Manifest 中配置则此处可略
                //  .withAppChannel(cnl)     // 发布应用的渠道,如果已经在 Manifest 中配置则此处可略
                .withUserInfo(AppApplication.getAndroidId())    // 用户信息-崩溃分析根据用户记录崩溃信息
                .withDebugModel(true)    // 输出更多SDK的debug信息
                .withErrorActivity(true)    // 发生崩溃时采集Activity信息
                .withCollectNDKCrash(true) //  收集NDK崩溃信息
                .withOpenCrash(true)    // 收集崩溃信息开关
                .withOpenEx(true)     // 是否收集异常信息
                .withReportOnlyWifi(true)    // 仅在 WiFi 下上报崩溃信息
                .withReportOnBack(true)    // 当APP在后台运行时,是否采集信息
                .withQAMaster(true)    // 是否收集摇一摇反馈
                .withCloseOption(false)   // 是否在摇一摇菜单展示‘关闭摇一摇选项’
                .withLogCat(true)  // 是否系统操作信息
                .build();
        Bugout.init(config);
    }

    private void initGeTui() {
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), com.yundian.star.service.DemoPushService.class);

        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), com.yundian.star.service.DemoIntentService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //* 注：回调 1
      // Bugout.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //* 注：回调 2
     // Bugout.onPause(this);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //* 注：回调 3
     // Bugout.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

}
