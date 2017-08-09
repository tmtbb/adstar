package com.cloudTop.starshare.utils;


/**
 * 防止按钮并发
 * Created by sll on 2017/6/8.
 */

public class ViewConcurrencyUtils {
    private static long exitNow;

    /**
     * 防止并发
     */
    public static void preventConcurrency() {
        if ((System.currentTimeMillis() - exitNow) < 3000) {
            LogUtils.loge("3秒内多次点击--------------");
            return;
        }
        exitNow = System.currentTimeMillis();
    }

}
