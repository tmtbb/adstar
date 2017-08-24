package com.cloudTop.starshare.app;

import android.os.Environment;

public class AppConfig {

    /**
     * The constant DEBUG_TAG.
     * "area_id" : 0,
     "area" : "",
     "isp_id" : 0,
     "isp" : "",
     */
    public static final String DEBUG_TAG = "logger";// LogCat的标记
   /* 自动更新配置*/
    public static String API_FIRE_TOKEN = "";
    public static String APP_FIRE_ID = "";
    //appid
    public static String APP_ID = "";
    public static final String SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DEFAULT_CACHE_DIR = SDCARD_DIR + "/PLDroidPlayer";
    public static String QI_NIU_PIC_ADRESS = "http://ouim6qew1.bkt.clouddn.com/";
    public static long AREA_ID = 0;
    public static String AREA = "";
    public static long ISP_ID = 0;
    public static String ISP = "";

    public static final String VIDEO_STORAGE_DIR = Environment.getExternalStorageDirectory() + "/ShortVideo";
    public static final String RECORD_FILE_PATH = VIDEO_STORAGE_DIR + "/record.mp4";

}
