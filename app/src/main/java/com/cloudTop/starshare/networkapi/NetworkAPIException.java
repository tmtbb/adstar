package com.cloudTop.starshare.networkapi;


public class NetworkAPIException extends Exception {

    public static final int INITCONFIG_ERROR = 100000;
    public static final int TOKEN_ERROR = INITCONFIG_ERROR + 1;
    public static final int NOTNETWORK_ERROR = TOKEN_ERROR + 1;
    public static final int NETWORK_ERROR = NOTNETWORK_ERROR + 1;
    public static final int SYSTEM_ERROR = NETWORK_ERROR + 1;
    public static final int JSON_ERROR = SYSTEM_ERROR + 1;
    public static final int HINT_ERROR = JSON_ERROR + 1;
    public static final int CREATE_UNION_ERROR = HINT_ERROR + 1;
    public static final int SOCKET_REQEUST_TIMEOUT = CREATE_UNION_ERROR + 1;
    public static final int SERVER_ERROR = -1;
    private int errorCode;
    private static boolean Debug = true;

    public NetworkAPIException(int errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public NetworkAPIException(int errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }


    public static String formatException(Throwable ex) {
        return formatException(ex, null);
    }

    public static String formatException(Throwable ex, String defError) {
        String message = null;
        if (ex instanceof NetworkAPIException) {
            switch (((NetworkAPIException) ex).getErrorCode()) {
                case NetworkAPIException.HINT_ERROR:
                case NetworkAPIException.CREATE_UNION_ERROR:
                    message = ex.getMessage();
                    break;
                case NetworkAPIException.TOKEN_ERROR:
                    message = "登录已失效请重新登录";
                    //todo token失效
//                    UserManager.getInstance().logout();
                    break;
                case NetworkAPIException.NOTNETWORK_ERROR:
                    if (!Debug) {
                        message = "网络不可用";
                        break;
                    }
                case NetworkAPIException.NETWORK_ERROR:
                    if (!Debug) {
                        message = "网络不给力";
                        break;
                    }
                case NetworkAPIException.SERVER_ERROR:
                    if (!Debug) {
                        message = "服务器异常，请稍后再试!";
                        break;
                    }
                case NetworkAPIException.SOCKET_REQEUST_TIMEOUT:
                    message = "timeout!";
                    break;
                case NetworkAPIException.JSON_ERROR:
                    if (!Debug) {
                        message = "JSON解析失败!";
                        break;
                    }

                default:
                    if (!Debug) {
                        message = defError != null ? defError : "获取数据失败！";
                    } else
                        message = ex.getMessage();
                    break;
            }
        }
        return message;
    }
}
