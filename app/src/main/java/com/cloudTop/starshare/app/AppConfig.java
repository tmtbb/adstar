package com.cloudTop.starshare.app;

import android.os.Environment;

public class AppConfig {

    /**
     * The constant DEBUG_TAG.
     */
    public static final String DEBUG_TAG = "logger";// LogCat的标记
   /* 自动更新配置*/
    public static String API_FIRE_TOKEN = "";
    public static String APP_FIRE_ID = "";
    //appid
    public static String APP_ID = "";
    public static final String SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DEFAULT_CACHE_DIR = SDCARD_DIR + "/PLDroidPlayer";
}
