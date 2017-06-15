package com.yundian.star.networkapi;

/**
 * Created by wsz on 2016/4/27.
 */
public enum Host {
    INTERNAL_253("http://172.16.1.253"),
    INTERNAL_19("http://172.16.3.19"),
    INTERNAL_14("http://172.16.3.14/modou"),
    INTERNAL_165("http://172.16.1.165"),
    INTERNAL_TEST("http://code.ywwl.com/modou"),
    EXTERNAL_TEST("http://tandroidapi.modou.com"),
    EXTERNAL_RELEASE("http://androidapi.modou.com");

    private String host;
    private String oldHost;
    private int imPort = 5222;

    Host(String value) {
        this.host = value;
    }

    public String getValue() {
        return host;
    }

    public void switchHost() {
//        if (!EXTERNAL_RELEASE.getValue().equals(host)) {
//            oldHost = host;
//            host = EXTERNAL_RELEASE.getValue();
//        } else if (!TextUtils.isEmpty(oldHost)) {
//            host = oldHost;
//        }
    }

//    public static int getImPort(Host host) {
//
//        if (host == EXTERNAL_RELEASE) {
//            return 5432;
//        } else {
//            return 5222;
//        }
//    }

//    public static String getImHost(Host host) {
//
//        if (host == EXTERNAL_RELEASE) {
//            return "im.modou.com";
//        } else if (host == INTERNAL_TEST) {
//            return "code.ywwl.com";
//        } else {
//            return "tim.modou.com";
//        }
//    }

    public static String getSocketServerIp() {
          return "139.224.34.22";
//        return "61.147.114.87";
//        return "139.224.34.22";
//        return "61.147.114.78";
//        return "192.168.8.131";
    }

    public static short getSocketServerPort() {
        return (short) 16006 ;
//        return (short) 16001;
//        return (short) 17002;
//        return (short) 30001;
//        return (short) 16002;
//        return (short) 16008;
//        return (short) 16205;
//        return (short) 16007;
    }
}
