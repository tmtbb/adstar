package com.yundian.star.base;

/**
 * des:baseview
 * Created by ysl
 */
public interface BaseView {
    /*******内嵌加载*******/
    void showLoading(String title);
    void stopLoading();
    void showErrorTip(String msg);
}
