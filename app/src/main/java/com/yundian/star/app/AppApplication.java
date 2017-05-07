package com.yundian.star.app;

import com.yundian.star.BuildConfig;
import com.yundian.star.base.baseapp.BaseApplication;
import com.yundian.star.utils.LogUtils;

/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);
    }
}
