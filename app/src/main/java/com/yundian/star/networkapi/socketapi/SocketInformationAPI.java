package com.yundian.star.networkapi.socketapi;

import com.yundian.star.app.SocketAPIConstant;
import com.yundian.star.been.AdvBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.InformationAPI;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketDataPacket;
import com.yundian.star.ui.main.model.NewsInforModel;

import java.util.HashMap;

/**
 * Created by ysl.
 */

public class SocketInformationAPI extends SocketBaseAPI implements InformationAPI {

    @Override
    public void newsinfo(String name, String code, int startnum, int endnum, int all, OnAPIListener<NewsInforModel> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("code", code);
        map.put("startnum", startnum);
        map.put("endnum", endnum);
        map.put("all", all);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.NewInfo,
                SocketAPIConstant.ReqeutType.NewInfos, map);
        requestEntity(socketDataPacket,NewsInforModel.class,listener);
    }

    @Override
    public void advInfo(String code, int all, OnAPIListener<AdvBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("all", all);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Banner,
                SocketAPIConstant.ReqeutType.NewInfos, map);
        requestEntity(socketDataPacket,AdvBeen.class,listener);
    }

}
