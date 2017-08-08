package com.cloudTop.starshare.service;

import android.content.Context;
import android.util.Log;

import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class DemoIntentService extends GTIntentService {

    public DemoIntentService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        Log.e(TAG, "-------------------onReceiveServicePid -> " + "pid = " + pid);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        LogUtils.loge("接收到消息了--------------");
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90002);
        Log.d(TAG, "-------call sendFeedbackMessage = " + (result ? "success" : "failed"));
        if (payload == null) {

        } else {
            String data = new String(payload);
            // sendMessage(data, 0);
            System.out.println("data-------->" + data);
        }
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "-------------------onReceiveClientId -> " + "clientid = " + clientid);
        NetworkAPIFactoryImpl.getUserAPI().saveDevice(SharePrefUtil.getInstance().getUserId(),clientid, new OnAPIListener<Object>() {
            @Override
            public void onError(Throwable ex) {

                LogUtils.logd("上传设备id和类型失败:" + ex.toString());
            }

            @Override
            public void onSuccess(Object o) {
                LogUtils.logd("上传设备id和类型成功:" + o.toString());
            }
        });
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        LogUtils.loge("------------------离线上线通知:" + online);
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        LogUtils.loge("------------onReceiveCommandResult:" + cmdMessage);
    }
}
