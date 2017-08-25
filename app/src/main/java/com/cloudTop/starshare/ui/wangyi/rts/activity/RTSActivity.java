package com.cloudTop.starshare.ui.wangyi.rts.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cloudTop.starshare.ui.wangyi.rts.ActionTypeEnum;
import com.cloudTop.starshare.ui.wangyi.rts.doodle.DoodleView;
import com.cloudTop.starshare.ui.wangyi.rts.doodle.SupportActionType;
import com.cloudTop.starshare.ui.wangyi.rts.doodle.action.MyPath;
import com.cloudTop.starshare.ui.wangyi.session.extension.RTSAttachment;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialog;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.cloudTop.starshare.R;

/**
 * 发起/接受会话界面
 * <p/>
 * Created by huangjun on 2015/7/27.
 */
public class RTSActivity extends UI implements View.OnClickListener {

    public static final int FROM_BROADCAST_RECEIVER = 0; // 来自广播
    public static final int FROM_INTERNAL = 1; // 来自发起方
    private static final String KEY_RTS_DATA = "KEY_RTS_DATA";
    private static final String KEY_INCOMING = "KEY_INCOMING";
    private static final String KEY_SOURCE = "KEY_SOURCE";
    private static final String KEY_UID = "KEY_UID";

    // data
    private boolean isIncoming = false;
    private String account;      // 对方帐号
    private String sessionId;    // 会话的唯一标识
    private boolean audioOpen = false; // 语音默认
    private boolean finishFlag = false; // 结束标记，避免多次回调onFinish
    private static boolean needFinish = true; // Activity销毁后，从最近任务列表恢复，则finish

    private static boolean isBusy = false;

    // 发起会话布局
    private View startSessionLayout;
    private TextView sessionStepText;
    private HeadImageView headImage;
    private TextView nameText;
    private View calleeAckLayout;
    private Button acceptBtn;
    private Button rejectBtn;
    private Button endSessionBtn;
    private Button audioSwitchBtn;

    // 白板布局
    private View sessionLayout;
    private DoodleView doodleView;
    private Button backBtn;
    private Button clearBtn;


    public static void startSession(Context context, String account, int source) {
        needFinish = false;
        Intent intent = new Intent();
        intent.setClass(context, RTSActivity.class);
        intent.putExtra(KEY_UID, account);
        intent.putExtra(KEY_INCOMING, false);
        intent.putExtra(KEY_SOURCE, source);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (needFinish) {
            finish();
            return;
        }

        isBusy = true;

        setContentView(R.layout.rts_activity);

        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = false;
        setToolBar(R.id.toolbar, options);

        isIncoming = getIntent().getBooleanExtra(KEY_INCOMING, false);
        findViews();
        initActionBarButton();


        initAudioSwitch();


        //放到所有UI的基类里面注册，所有的UI实现onKickOut接口
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, true);
    }

    private void initActionBarButton() {
        TextView closeSessionBtn = findView(R.id.action_bar_right_clickable_textview);
        closeSessionBtn.setText(R.string.close);
        closeSessionBtn.setBackgroundResource(R.drawable.nim_message_button_bottom_send_selector);
        closeSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyAlertDialogHelper.OnDialogActionListener listener = new EasyAlertDialogHelper.OnDialogActionListener() {

                    @Override
                    public void doCancelAction() {
                    }

                    @Override
                    public void doOkAction() {
                        endSession(); // 挂断
                    }
                };
                final EasyAlertDialog dialog = EasyAlertDialogHelper.createOkCancelDiolag(RTSActivity.this,
                        getString(R.string.end_session_tip_head),
                        getString(R.string.end_session_tip_content),
                        getString(R.string.ok), getString(R.string.cancel), true, listener);
                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected boolean displayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        // 这里需要重绘
        doodleView.onResume();
    }

    @Override
    protected void onDestroy() {
        if (doodleView != null) {
            doodleView.end();
        }

        super.onDestroy();
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, false);


        needFinish = true;

        isBusy = false;
    }

    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
                finish();
            }
        }
    };

    private void findViews() {
        startSessionLayout = findViewById(R.id.start_session_layout);
        sessionLayout = findViewById(R.id.session_layout);

        headImage = (HeadImageView) findViewById(R.id.head_image);
        sessionStepText = (TextView) findViewById(R.id.session_step_text);
        nameText = (TextView) findViewById(R.id.name);
        calleeAckLayout = findViewById(R.id.callee_ack_layout);
        acceptBtn = (Button) findViewById(R.id.accept);
        rejectBtn = (Button) findViewById(R.id.reject);
        endSessionBtn = (Button) findViewById(R.id.end_session);
        doodleView = (DoodleView) findViewById(R.id.doodle_view);
        backBtn = (Button) findViewById(R.id.doodle_back);
        clearBtn = (Button) findViewById(R.id.doodle_clear);
        audioSwitchBtn = (Button) findViewById(R.id.audio_switch);

        acceptBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
        endSessionBtn.setOnClickListener(this);
        audioSwitchBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);

        float screenWidth = ScreenUtil.screenWidth * 1.0f;
        ViewGroup.LayoutParams params = doodleView.getLayoutParams();
        params.width = ((int) (screenWidth / 100)) * 100; // 比屏幕小的100的整数
        params.height = params.width; // 保证宽高比为1:1
        doodleView.setLayoutParams(params);
    }

    private void incoming() {
        initIncomingSessionViews();
    }

    private void outgoing() {
        account = getIntent().getStringExtra(KEY_UID);

        initStartSessionViews();
    }

    private void initStartSessionViews() {
        initAccountInfoView();
        sessionStepText.setText(R.string.start_session);
        calleeAckLayout.setVisibility(View.GONE);
        endSessionBtn.setVisibility(View.VISIBLE);
        startSessionLayout.setVisibility(View.VISIBLE);
    }

    private void initIncomingSessionViews() {
        initAccountInfoView();
        sessionStepText.setText(R.string.receive_session);
        calleeAckLayout.setVisibility(View.VISIBLE);
        endSessionBtn.setVisibility(View.GONE);
        startSessionLayout.setVisibility(View.VISIBLE);
    }

    private void initAccountInfoView() {
        nameText.setText(NimUserInfoCache.getInstance().getUserDisplayName(account));
        headImage.loadBuddyAvatar(account);
    }












    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accept:
                acceptSession();
                break;
            case R.id.reject:
                endSession();
                break;
            case R.id.end_session:
                endSession();
                break;
            case R.id.doodle_back:
                doodleBack();
                break;
            case R.id.doodle_clear:
                clear();
                break;
            case R.id.audio_switch:
                audioSwitch();
                break;
            default:
                break;
        }
    }

    private void acceptSession() {
    }

    private void acceptView() {
        startSessionLayout.setVisibility(View.GONE);
        sessionLayout.setVisibility(View.VISIBLE);
        initDoodleView();


    }

    private void endSession() {

        onFinish();
    }

    private void onFinish() {
        onFinish(true);
    }

    private void onFinish(boolean selfFinish) {
        if (finishFlag) {
            return;
        }
        finishFlag = true;

        RTSAttachment attachment = new RTSAttachment((byte) 1);

        IMMessage msg = MessageBuilder.createCustomMessage(account, SessionTypeEnum.P2P, attachment.getContent(), attachment);
        if (!selfFinish) {
            // 被结束会话，在这里模拟一条接收的消息
            msg.setFromAccount(account);
            msg.setDirect(MsgDirectionEnum.In);
        }

        msg.setStatus(MsgStatusEnum.success);

        NIMClient.getService(MsgService.class).saveMessageToLocal(msg, true);

        finish();
    }

    /**
     * ***************************** 画板 ***********************************
     */
    private void initDoodleView() {
        // add support ActionType
        SupportActionType.getInstance().addSupportActionType(ActionTypeEnum.Path.getValue(), MyPath.class);

        doodleView.init(sessionId, account, DoodleView.Mode.BOTH, Color.WHITE, this);

        doodleView.setPaintSize(10);
        doodleView.setPaintType(ActionTypeEnum.Path.getValue());

        // adjust paint offset
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Rect frame = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;
                Log.i("Doodle", "statusBarHeight =" + statusBarHeight);

                int marginTop = doodleView.getTop();
                Log.i("Doodle", "doodleView marginTop =" + marginTop);

                int marginLeft = doodleView.getLeft();
                Log.i("Doodle", "doodleView marginLeft =" + marginLeft);

                float offsetX = marginLeft;
                float offsetY = statusBarHeight + marginTop;

                doodleView.setPaintOffset(offsetX, offsetY);
                Log.i("Doodle", "client1 offsetX = " + offsetX + ", offsetY = " + offsetY);
            }
        }, 50);
    }

    /**
     * 撤销一步
     */
    private void doodleBack() {
        doodleView.paintBack();
    }

    /**
     * 清屏
     */
    private void clear() {
        doodleView.clear();
    }

    /**
     * 语音开关
     */
    private void audioSwitch() {
        audioOpen = !audioOpen;

        audioSwitchBtn.setBackgroundResource(audioOpen ? R.drawable.icon_audio_open : R.drawable.icon_audio_close);

        // 通过控制协议通知对方
        String content = "对方静音" + (audioOpen ? "关闭" : "开启");

    }

    /**
     * 初始化语音开关(默认关闭)
     */
    private void initAudioSwitch() {
        audioSwitchBtn.setBackgroundResource(R.drawable.icon_audio_close);
    }
}
