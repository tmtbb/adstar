package com.yundian.star.ui.wangyi.avchat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.yundian.star.R;
import com.yundian.star.ui.wangyi.avchat.AVChatNotification;
import com.yundian.star.ui.wangyi.avchat.AVChatProfile;
import com.yundian.star.ui.wangyi.avchat.AVChatSoundPlayer;
import com.yundian.star.ui.wangyi.avchat.AVChatUI;
import com.yundian.star.ui.wangyi.avchat.constant.CallStateEnum;

/**
 * 音视频界面
 * Created by hzxuwen on 2015/4/21.
 */
public class AVChatActivity extends UI implements AVChatUI.AVChatListener {
    // constant
    private static final String TAG = "AVChatActivity";
    private static final String KEY_IN_CALLING = "KEY_IN_CALLING";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    private static final String KEY_CALL_TYPE = "KEY_CALL_TYPE";
    private static final String KEY_SOURCE = "source";
    private static final String KEY_CALL_CONFIG = "KEY_CALL_CONFIG";
    public static final String INTENT_ACTION_AVCHAT = "INTENT_ACTION_AVCHAT";

    /**
     * 来自广播
     */
    public static final int FROM_BROADCASTRECEIVER = 0;
    /**
     * 来自发起方
     */
    public static final int FROM_INTERNAL = 1;
    /**
     * 来自通知栏
     */
    public static final int FROM_NOTIFICATION = 2;
    /**
     * 未知的入口
     */
    public static final int FROM_UNKNOWN = -1;

    // data
    private AVChatUI avChatUI; // 音视频总管理器
    private int state; // calltype 音频或视频
    private String receiverId; // 对方的account

    // state
    private boolean isUserFinish = false;
    private boolean mIsInComingCall = false;// is incoming call or outgoing call
    private boolean isCallEstablished = false; // 电话是否接通
    private static boolean needFinish = true; // 若来电或去电未接通时，点击home。另外一方挂断通话。从最近任务列表恢复，则finish
    private boolean hasOnPause = false; // 是否暂停音视频

    // notification
    private AVChatNotification notifier;

    public static void launch(Context context, String account, int callType, int source) {
        needFinish = false;
        Intent intent = new Intent();
        intent.setClass(context, AVChatActivity.class);
        intent.putExtra(KEY_ACCOUNT, account);
        intent.putExtra(KEY_IN_CALLING, false);
        intent.putExtra(KEY_CALL_TYPE, callType);
        intent.putExtra(KEY_SOURCE, source);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (needFinish || !checkSource()) {
            finish();
            return;
        }
        View root = LayoutInflater.from(this).inflate(R.layout.avchat_activity, null);
        setContentView(root);
        mIsInComingCall = getIntent().getBooleanExtra(KEY_IN_CALLING, false);
        avChatUI = new AVChatUI(this, root, this);
        if (!avChatUI.initiation()) {
            this.finish();
            return;
        }

        notifier = new AVChatNotification(this);
        notifier.init(receiverId);
        isCallEstablished = false;
        //放到所有UI的基类里面注册，所有的UI实现onKickOut接口
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        avChatUI.pauseVideo(); // 暂停视频聊天（用于在视频聊天过程中，APP退到后台时必须调用）
        hasOnPause = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activeCallingNotifier();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cancelCallingNotifier();
        if (hasOnPause) {
            avChatUI.resumeVideo();
            hasOnPause = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, false);
        AVChatProfile.getInstance().setAVChatting(false);
        cancelCallingNotifier();
        needFinish = true;
    }

    @Override
    public void onBackPressed() {
    }

    /**
     * 判断来电还是去电
     *
     * @return
     */
    private boolean checkSource() {
        switch (getIntent().getIntExtra(KEY_SOURCE, FROM_UNKNOWN)) {
            case FROM_INTERNAL: // outgoing call
                parseOutgoingIntent();
                return false;
            default:
                return false;
        }
    }


    /**
     * 去电参数解析
     */
    private void parseOutgoingIntent() {
        receiverId = getIntent().getStringExtra(KEY_ACCOUNT);
        state = getIntent().getIntExtra(KEY_CALL_TYPE, -1);
    }


    Observer<Integer> autoHangUpForLocalPhoneObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer integer) {

            AVChatSoundPlayer.instance().stop();

            avChatUI.closeSessions(AVChatExitCode.PEER_BUSY);
        }
    };


    /**
     * *************************** AVChatListener *********************************
     */

    @Override
    public void uiExit() {
        finish();
    }


    /****************************** 连接建立处理 ********************/

    /**
     * 处理连接服务器的返回值
     *
     * @param auth_result
     */
    protected void handleWithConnectServerResult(int auth_result) {
        LogUtil.i(TAG, "result code->" + auth_result);
        if (auth_result == 200) {
            Log.d(TAG, "onConnectServer success");
        } else if (auth_result == 101) { // 连接超时
            avChatUI.closeSessions(AVChatExitCode.PEER_NO_RESPONSE);
        } else if (auth_result == 401) { // 验证失败
            avChatUI.closeSessions(AVChatExitCode.CONFIG_ERROR);
        } else if (auth_result == 417) { // 无效的channelId
            avChatUI.closeSessions(AVChatExitCode.INVALIDE_CHANNELID);
        } else { // 连接服务器错误，直接退出
            avChatUI.closeSessions(AVChatExitCode.CONFIG_ERROR);
        }
    }

    /**************************** 处理音视频切换 *********************************/


    /**
     * 音频切换为视频
     */
    private void onAudioToVideo() {
        avChatUI.onAudioToVideo();
        avChatUI.initAllSurfaceView(avChatUI.getVideoAccount());
    }

    /**
     * 视频切换为音频
     */
    private void onVideoToAudio() {
        avChatUI.onCallStateChange(CallStateEnum.AUDIO);
        avChatUI.onVideoToAudio();
    }

    /**
     * 通知栏
     */
    private void activeCallingNotifier() {
        if (notifier != null && !isUserFinish) {
            notifier.activeCallingNotification(true);
        }
    }

    private void cancelCallingNotifier() {
        if (notifier != null) {
            notifier.activeCallingNotification(false);
        }
    }

    private void activeMissCallNotifier() {
        if (notifier != null) {
            notifier.activeMissCallNotification(true);
        }
    }

    @Override
    public void finish() {
        isUserFinish = true;
        super.finish();
    }


    /**
     * ************************ AVChatStateObserver ****************************
     */


    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
                AVChatSoundPlayer.instance().stop();
                finish();
            }
        }
    };

}

