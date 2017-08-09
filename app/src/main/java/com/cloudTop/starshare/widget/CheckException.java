package com.cloudTop.starshare.widget;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-11-23 10:03
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class CheckException extends Exception {

    private String errorMsg;

    public CheckException() {
    }

    public CheckException(String message) {
        super(message);
    }


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
