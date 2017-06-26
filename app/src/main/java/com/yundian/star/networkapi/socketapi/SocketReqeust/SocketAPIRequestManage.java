package com.yundian.star.networkapi.socketapi.SocketReqeust;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIException;
import com.yundian.star.utils.ErrorCodeUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.ResultCodeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketAPIRequestManage {

    private static SocketAPIRequestManage socketAPIRequestManage = null;
    private HashMap<Long, SocketAPIRequest> socketAPIRequestHashMap = new HashMap<Long, SocketAPIRequest>();
    private long sessionId = 10000;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            checkReqeustTimeout();
            handler.postDelayed(this, 1000);
        }
    };
    private Handler mainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SocketAPIResponse socketAPIResponse = (SocketAPIResponse) msg.obj;
                    ResultCodeUtil.showEeorMsg(socketAPIResponse);
                    break;
                case 2:
                    int errcode = (int) msg.obj;
                    ErrorCodeUtil.showEeorMsg(errcode);
                    break;
            }
        }
    };

    public static synchronized SocketAPIRequestManage getInstance() {
        if (socketAPIRequestManage == null) {
            socketAPIRequestManage = new SocketAPIRequestManage();
        }
        return socketAPIRequestManage;
    }


    public void start() {
        stop();
        SocketAPINettyBootstrap.getInstance().connect(true);
        handler.postDelayed(runnable, 1000);// 打开定时器，执行操作
    }


    public void stop() {
        handler.removeCallbacks(runnable);// 关闭定时器处理
        SocketAPINettyBootstrap.getInstance().closeChannel();
    }

    private synchronized long getSessionId() {
        if (sessionId > 2000000000) {
            sessionId = 10000;
        }
        sessionId += 1;
        return sessionId;
    }

    public synchronized void notifyResponsePacket(SocketDataPacket socketDataPacket) {
        if (socketDataPacket != null) {
            LogUtils.loge("移除前接收口getOperateCode:" + socketDataPacket.getOperateCode());
            if (socketDataPacket.getOperateCode() == 5101 || socketDataPacket.getOperateCode() == 5102||socketDataPacket.getOperateCode()==3040) {
                EventBus.getDefault().postSticky(socketDataPacket);
                if (sucessListener != null) {
                    LogUtils.loge("调用之前:"+socketDataPacket.getOperateCode()+"----------------------");
                    sucessListener.onMatchListener(socketDataPacket);
                }
            }
            SocketAPIRequest socketAPIRequest = socketAPIRequestHashMap.get(socketDataPacket.getSessionId());
            if (socketAPIRequest != null && socketAPIRequest.getListener() != null) {
                LogUtils.loge("移除前接收口getOperateCode:" + socketDataPacket.getOperateCode());
                socketAPIRequestHashMap.remove(socketDataPacket.getSessionId());
                SocketAPIResponse socketAPIResponse = new SocketAPIResponse(socketDataPacket);
                int statusCode = socketAPIResponse.statusCode();
                if (statusCode == 0) {
                    Message obtain = Message.obtain();
                    obtain.what = 1;
                    obtain.obj = socketAPIResponse;
                    mainHandler.sendMessage(obtain);
                    socketAPIRequest.onSuccess(socketAPIResponse);
                    LogUtils.loge("服务器返回Result:" + socketDataPacket.getOperateCode() + "----jsonResponse:" + socketAPIResponse.jsonObject().toString());
                } else {
                    LogUtils.loge("服务器返回errcode:" + statusCode + "----");
                    Message obtain = Message.obtain();
                    obtain.what = 2;
                    obtain.obj = statusCode;
                    mainHandler.sendMessage(obtain);
                    socketAPIRequest.onErrorCode(statusCode);
                }
            }
        }
    }

    private OnMatchSucessListener sucessListener;

    //收到消息接口回调
    public interface OnMatchSucessListener {
        void onMatchListener(SocketDataPacket socketDataPacket);
    }

    public void setOnMatchSucessListener(OnMatchSucessListener sucessListener) {
        this.sucessListener = sucessListener;
    }

    public void unboundOnMatchSucessListener() {
        this.sucessListener = null;
    }

    public void startJsonRequest(SocketDataPacket socketDataPacket, OnAPIListener<SocketAPIResponse> listener) {
        if (socketDataPacket != null && listener != null) {
            SocketAPIRequest socketAPIRequest = new SocketAPIRequest();
            socketAPIRequest.setListener(listener);
            long sessionId = getSessionId();
            socketDataPacket.setSessionId(sessionId);
            socketDataPacket.setTimestamp((int) (socketAPIRequest.getTimestamp() / 1000));
            socketAPIRequestHashMap.put(sessionId, socketAPIRequest);
            SocketAPINettyBootstrap.getInstance().writeAndFlush(socketDataPacket);
        }
    }

    private synchronized void checkReqeustTimeout() {
        for (HashMap.Entry<Long, SocketAPIRequest> entry : socketAPIRequestHashMap.entrySet()) {
            if (entry.getValue().isReqeustTimeout()) {
                socketAPIRequestHashMap.remove(entry.getKey());
                entry.getValue().onErrorCode(NetworkAPIException.SOCKET_REQEUST_TIMEOUT);
                break;
            }

        }
    }
}
