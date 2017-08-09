package com.cloudTop.starshare.networkapi.socketapi.SocketReqeust;

import com.cloudTop.starshare.networkapi.NetworkAPIException;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketAPIResponse {
    private SocketDataPacket socketDataPacket;
    private JSONObject jsonObject;

    public SocketAPIResponse(SocketDataPacket socketDataPacket) {
        this.socketDataPacket = socketDataPacket;
    }

    public JSONObject jsonObject() {
        if( jsonObject == null ) {
            try {
                jsonObject = new JSONObject(new String(socketDataPacket.getDataBody(), Charset.forName("UTF-8")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public int statusCode() {
        jsonObject();
        try {
            if( jsonObject == null ) {
                return NetworkAPIException.JSON_ERROR;
            }
            else if( ! jsonObject.isNull("errorCode") ) {
                return jsonObject.getInt("errorCode");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
