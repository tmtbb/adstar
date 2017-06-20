package com.yundian.star.been;

/**
 * eventBus消息类
 * Created by Administrator on 2017/4/4.
 */
public class EventBusMessage {
    public int Message;
    public EventBusMessage(int message) {
        Message = message;
    }
    public  CheckUpdateInfoEntity checkUpdateInfoEntity;

    public int getMessage() {
        return Message;
    }

    public void setMessage(int message) {
        Message = message;
    }

    public CheckUpdateInfoEntity getCheckUpdateInfoEntity() {
        return checkUpdateInfoEntity;
    }

    public void setCheckUpdateInfoEntity(CheckUpdateInfoEntity checkUpdateInfoEntity) {
        this.checkUpdateInfoEntity = checkUpdateInfoEntity;
    }
}
