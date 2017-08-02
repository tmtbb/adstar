package com.cloudTop.starshare.ui.main.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.ui.main.fragment.NewsInfoFragment;
import com.cloudTop.starshare.ui.view.ForceUpdateDialog;
import com.cloudTop.starshare.ui.wangyi.config.preference.UserPreferences;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.netease.nim.uikit.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.permission.MPermission;
import com.netease.nim.uikit.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.mixpush.MixPushService;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.qiangxi.checkupdatelibrary.dialog.UpdateDialog;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.app.AppConstant;
import com.cloudTop.starshare.been.CheckUpdateInfoEntity;
import com.cloudTop.starshare.been.EventBusMessage;
import com.cloudTop.starshare.been.TabEntity;
import com.cloudTop.starshare.ui.main.fragment.FindStarFragment;
import com.cloudTop.starshare.ui.main.fragment.MarketDetailFragment;
import com.cloudTop.starshare.ui.main.fragment.UserInfoFragment;
import com.cloudTop.starshare.ui.wangyi.chatroom.helper.ChatRoomHelper;
import com.cloudTop.starshare.utils.CheckLoginUtil;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog.FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE;
import static com.qiangxi.checkupdatelibrary.dialog.UpdateDialog.UPDATE_DIALOG_PERMISSION_REQUEST_CODE;


public class MainActivity extends BaseActivity {
    @Bind(R.id.tab_bottom_layout)
    CommonTabLayout tabLayout;
    private String[] mTitles = {"发现明星", "明星热度", "个人中心"};
    private int[] mIconUnselectIds = {
            R.drawable.differ_answer_no_ok, R.drawable.market_no_ok, R.drawable.me_no_ok};
    private int[] mIconSelectIds = {
            R.drawable.differ_answer_ok, R.drawable.market_ok, R.drawable.me_ok};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private NewsInfoFragment newsInfoFragment;
    private FindStarFragment findStarFragment;
    //private MarketFragment marketFragment;
    private MarketDetailFragment marketFragment;
    //    private DifferAnswerFragment differAnswerFragment;
    private UserInfoFragment userInfoFragment;
    private final int BASIC_PERMISSION_REQUEST_CODE = 100;
    public static int CHECHK_LOGIN = 0;
    private boolean flag = true;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int i = (int)msg.obj;
                    showPopupWindow(i);
                    break;
            }
        }
    };

    Runnable runnablePermission = new Runnable() {
        @Override
        public void run() {
            requestBasicPermission();
        }
    };
    private int match_info;
    private boolean isFirest;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        initTab();
//        checkunReadMsg();
        handler.postDelayed(runnablePermission, 1000);
    }

    private void checkunReadMsg() {
        int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
        if (unreadNum > 0) {
            tabLayout.showDot(2);
        } else {
            tabLayout.hideMsg(2);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
        initWangYi();
        registerObservers(true);
        if (flag) {
            EventBus.getDefault().register(this); // EventBus注册广播()
            flag = false;//更改标记,使其不会再进行多次注册
        }
        Intent intent = getIntent();
        match_info = intent.getIntExtra(AppConstant.MATCH_SUCESS_INFO, 0);
        if (match_info == 1) {
            SwitchTo(2);
            tabLayout.setCurrentTab(2);
        }
    }


    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            findStarFragment = (FindStarFragment) getSupportFragmentManager().findFragmentByTag("FindStarFragment");
            //marketFragment = (MarketFragment) getSupportFragmentManager().findFragmentByTag("MarketFragment");
            marketFragment = (MarketDetailFragment) getSupportFragmentManager().findFragmentByTag("MarketFragment");
//            differAnswerFragment = (DifferAnswerFragment) getSupportFragmentManager().findFragmentByTag("DifferAnswerFragment");
            userInfoFragment = (UserInfoFragment) getSupportFragmentManager().findFragmentByTag("UserInfoFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            findStarFragment = new FindStarFragment();
            marketFragment = new MarketDetailFragment();
//            differAnswerFragment = new DifferAnswerFragment();
            userInfoFragment = new UserInfoFragment();
            transaction.add(R.id.fl_main, findStarFragment, "FindStarFragment");
            transaction.add(R.id.fl_main, marketFragment, "MarketFragment");
//            transaction.add(R.id.fl_main, differAnswerFragment, "DifferAnswerFragment");
            transaction.add(R.id.fl_main, userInfoFragment, "UserInfoFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        LogUtils.loge("onSaveInstanceState进来了1");
        if (tabLayout != null) {
            LogUtils.loge("onSaveInstanceState进来了2");
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }

    private void initWangYi() {
        // 等待同步数据完成
        boolean syncCompleted = LoginSyncDataStatusObserver.getInstance().observeSyncDataCompletedEvent(new Observer<Void>() {
            @Override
            public void onEvent(Void v) {

                syncPushNoDisturb(UserPreferences.getStatusConfig());

                DialogMaker.dismissProgressDialog();
            }
        });

        LogUtils.logd("sync completed = " + syncCompleted);
        if (!syncCompleted) {
            DialogMaker.showProgressDialog(MainActivity.this, getString(R.string.prepare_data)).setCanceledOnTouchOutside(false);
        } else {
            syncPushNoDisturb(UserPreferences.getStatusConfig());
        }
        // 聊天室初始化
        ChatRoomHelper.init();
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                transaction.hide(marketFragment);
//                transaction.hide(differAnswerFragment);
                transaction.hide(userInfoFragment);
                transaction.show(findStarFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                if (SharePrefUtil.getInstance().getStatusNav_4()==0){
                    SharePrefUtil.getInstance().setStatusNav_4(1);
                    Message message = Message.obtain();
                    message.what=1;
                    message.obj=4;
                    handler.sendMessageDelayed(message,500);
                }
                transaction.hide(findStarFragment);
//                transaction.hide(differAnswerFragment);
                transaction.hide(userInfoFragment);
                transaction.show(marketFragment);
                transaction.commitAllowingStateLoss();
                break;
//            case 2:
//                CheckLoginUtil.checkLogin(this);
//                transaction.hide(marketFragment);
//                transaction.hide(newsInfoFragment);
//                transaction.hide(userInfoFragment);
////                transaction.show(differAnswerFragment);
//                transaction.commitAllowingStateLoss();
//                break;
            case 2:
                CheckLoginUtil.checkLogin(this);
                transaction.hide(marketFragment);
                transaction.hide(findStarFragment);
//                transaction.hide(differAnswerFragment);
                transaction.show(userInfoFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }


    /**
     * 基本权限管理
     */
    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_NETWORK_STATE,
            //Manifest.permission.SYSTEM_ALERT_WINDOW
    };

    private void requestBasicPermission() {
        MPermission.printMPermissionResult(true, this, BASIC_PERMISSIONS);
        MPermission.with(MainActivity.this)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);

        LogUtils.loge("走到此处------------------");
        if (requestCode == UPDATE_DIALOG_PERMISSION_REQUEST_CODE) {
            LogUtils.loge("强制更新-------------");
            mUpdateDialog.download();
        } else if (requestCode == FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE) {
            mForceUpdateDialog.download();
        }
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(this, "未全部授权，部分功能可能无法正常运行！", Toast.LENGTH_SHORT).show();
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }

    /**
     * 若增加第三方推送免打扰（V3.2.0新增功能），则：
     * 1.添加下面逻辑使得 push 免打扰与先前的设置同步。
     * 2.设置界面 com.netease.nim.demo.main.activity.SettingsActivity 以及
     * 免打扰设置界面 com.netease.nim.demo.main.activity.NoDisturbActivity 也应添加 push 免打扰的逻辑
     * <p>
     * 注意：isPushDndValid 返回 false， 表示未设置过push 免打扰。
     */
    private void syncPushNoDisturb(StatusBarNotificationConfig staConfig) {

        boolean isNoDisbConfigExist = NIMClient.getService(MixPushService.class).isPushNoDisturbConfigExist();

        if (!isNoDisbConfigExist && staConfig.downTimeToggle) {
            NIMClient.getService(MixPushService.class).setPushNoDisturbConfig(staConfig.downTimeToggle,
                    staConfig.downTimeBegin, staConfig.downTimeEnd);
        }
    }

    /**
     * ********************** 收消息，处理状态变化 ************************
     */
    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeRecentContact(messageObserver, register);
    }

    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> recentContacts) {
            checkunReadMsg();
        }
    };

    @Override
    protected void onDestroy() {
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        registerObservers(false);
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveMessageEventBus(final EventBusMessage eventBusMessage) {
        switch (eventBusMessage.Message) {
            case 2:  //登录取消
                SwitchTo(0);
                tabLayout.setCurrentTab(0);
                break;
            case -11:
                if (eventBusMessage.getCheckUpdateInfoEntity().getIsForceUpdate() == 0) {
                    forceUpdateDialog(eventBusMessage.getCheckUpdateInfoEntity());
                } else {  //非强制更新
                    updateDialog(eventBusMessage.getCheckUpdateInfoEntity());
                }
                break;
        }
    }

    private UpdateDialog mUpdateDialog;
    private ForceUpdateDialog mForceUpdateDialog;

    /**
     * 强制更新
     */
    public void forceUpdateDialog(CheckUpdateInfoEntity mCheckUpdateInfo) {
        mForceUpdateDialog = new ForceUpdateDialog(MainActivity.this);
        mForceUpdateDialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
                .setDownloadUrl(mCheckUpdateInfo.getNewAppUrl())
                .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                .setFileName(getResources().getString(R.string.app_name))
                .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/XingXiang").show();
    }

    /**
     * 非强制更新
     */
    public void updateDialog(CheckUpdateInfoEntity mCheckUpdateInfo) {
        LogUtils.loge("mCheckUpdateInfo"+mCheckUpdateInfo.toString());
        mUpdateDialog = new UpdateDialog(this);
        mUpdateDialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
                .setDownloadUrl(mCheckUpdateInfo.getNewAppUrl())
                .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                .setFileName(getResources().getString(R.string.app_name))
                .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/XingXiang")
                .setShowProgress(true)
                .setIconResId(R.mipmap.ic_launcher)
                .setAppName(mCheckUpdateInfo.getAppName()).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (SharePrefUtil.getInstance().getStatusNav_1()==0){
            SharePrefUtil.getInstance().setStatusNav_1(1);
            Message message = Message.obtain();
            message.what=1;
            message.obj=1;
            handler.sendMessageDelayed(message,2000);
        }
    }

    private void showPopupWindow(final int i) {
        View popView = LayoutInflater.from(this).inflate(R.layout.popwindow_navijation_1, null);
        final ImageView imageView = (ImageView) popView.findViewById(R.id.navigation_1);
        final ImageView imageView3 = (ImageView) popView.findViewById(R.id.navigation_3);
        final ImageView navigation_2_2 = (ImageView) popView.findViewById(R.id.navigation_2_2);
        final RelativeLayout navigation_4 = (RelativeLayout) popView.findViewById(R.id.navigation_4);
        final ImageView navigation_4_2 = (ImageView) popView.findViewById(R.id.navigation_4_2);
        if (i==1){
            imageView.setVisibility(View.VISIBLE);
        }else if (i==2){
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.navigation_2));
        }else if (i==4){
            navigation_4.setVisibility(View.VISIBLE);
        }
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(popView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x33000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        isFirest = true;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i==1){
                    if (isFirest){
                        imageView.setImageResource(R.drawable.navigation_7);
                        isFirest =false;
                    }else {
                        imageView.setVisibility(View.GONE);
                        imageView3.setVisibility(View.VISIBLE);
                    }
                }else if (i==2){
                    imageView.setVisibility(View.GONE);
                    navigation_2_2.setVisibility(View.VISIBLE);
                }
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        navigation_2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        navigation_4_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }
}
