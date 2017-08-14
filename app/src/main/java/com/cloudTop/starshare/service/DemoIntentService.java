package com.cloudTop.starshare.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.im.activity.SystemMessagesActivity;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import java.util.Random;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class DemoIntentService extends GTIntentService {

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    public DemoIntentService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        Log.e(TAG, "-------------------onReceiveServicePid -> " + "pid = " + pid);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        notificationTest();
        LogUtils.loge("接收到消息了--------------");
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();
        if (payload == null) {

        } else {
            String data = new String(payload);
            // sendMessage(data, 0);
            System.out.println("data-------->" + data);
            if ("1".equals(data)){
                /*mBuilder.setContentText("委托匹配成功");
                mBuilder.setTicker("委托匹配成功");
                showNotify();*/
            }else if ("2".equals(data)){
                mBuilder.setContentText("交易成功");
                mBuilder.setTicker("交易成功");
                showNotify();
            }else if ("0".equals(data)){
                mBuilder.setContentText("扣费成功");
                mBuilder.setTicker("扣费成功");
                showNotify();
            }else if ("-4".equals(data)){
                mBuilder.setContentText("订单异常");
                mBuilder.setTicker("订单异常");
                showNotify();
            } else if ("-3".equals(data)){
                mBuilder.setContentText("资金不足");
                mBuilder.setTicker("资金不足");
                showNotify();
            } else if ("-2".equals(data)){
                mBuilder.setContentText("时间不足");
                mBuilder.setTicker("时间不足");
                showNotify();
            } else if ("-1".equals(data)){
                mBuilder.setContentText("取消订单");
                mBuilder.setTicker("取消订单");
                showNotify();
            }else if ("10".equals(data)){
                mBuilder.setContentText("委托匹配成功");
                mBuilder.setTicker("委托匹配成功");
                showNotify();
            }

        }


        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90002);
        Log.d(TAG, "-------call sendFeedbackMessage = " + (result ? "success" : "failed"));
        Log.d(TAG, "-------call context = "+context);
    }

    private void showNotify() {
        mBuilder.setContentTitle("星享");
        mBuilder.setContentIntent(getDefalutIntent(SystemMessagesActivity.class,Notification.FLAG_AUTO_CANCEL)); //设置通知栏点击意图
        mNotificationManager.notify(new Random().nextInt(Integer.MAX_VALUE), mBuilder.build());
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
    private void notificationTest() {
        mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        Notification notification = mBuilder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        mBuilder.build().defaults = Notification.DEFAULT_ALL;
        mBuilder.setContentText("")
//                .setFullScreenIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL), true)
//  .setNumber(10) //设置通知集合的数量
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_MAX) //设置该通知优先级
                //  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
    }
    public PendingIntent getDefalutIntent(Class aClass, int flags) {
        Intent intent = new Intent(this, aClass);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, flags);
        return pendingIntent;
    }

}
