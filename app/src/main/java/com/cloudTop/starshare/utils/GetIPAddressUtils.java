package com.cloudTop.starshare.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cloudTop.starshare.been.MyAddressBean;
import com.cloudTop.starshare.been.MyIpBean;

/**
 * Created by Administrator on 2017/8/19.
 */

public class GetIPAddressUtils {
    public static MyAddressBean getIpAddress() {
        MyAddressBean myAddressBean =null;
        try {
            String myIp = HttpUtils.request("http://ip.chinaz.com/getip.aspx").get();
            LogUtils.loge(myIp);
            MyIpBean myIpBean = JSON.parseObject(myIp, MyIpBean.class);
            if (!TextUtils.isEmpty(myIpBean.getIp())){
                LogUtils.loge("http://ip.taobao.com/service/getIpInfo.php?ip=" + myIpBean.getIp());
                String ipUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=" + myIpBean.getIp();
                String myAddress = HttpUtils.request(ipUrl).get();
                LogUtils.loge("ysl_myAddress"+myAddress.toString());
                if (!TextUtils.isEmpty(myAddress.toString())){
                    myAddressBean = JSON.parseObject(myAddress.toString(), MyAddressBean.class);
                    LogUtils.loge("ysl_myAddressBean"+myAddressBean.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myAddressBean;
    }
}
