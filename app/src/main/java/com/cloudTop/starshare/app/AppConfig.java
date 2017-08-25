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
    public static final String TRIM_STORAGE_DIR = Environment.getExternalStorageDirectory() + "/ShortVideo";
    public static final String RECORD_FILE_PATH = VIDEO_STORAGE_DIR + "/";
    //七牛云demotoken
    public static final String TOKEN = "MqF35-H32j1PH8igh-am7aEkduP511g-5-F7j47Z:0gzBOkhm3KsFGbGk2HdKfA4jZp4=:eyJzY29wZSI6InNob3J0LXZpZGVvIiwiZGVhZGxpbmUiOjE2NTA3MTExMDcsInVwaG9zdHMiOlsiaHR0cDovL3VwLXoyLnFpbml1LmNvbSIsImh0dHA6Ly91cGxvYWQtejIucWluaXUuY29tIiwiLUggdXAtejIucWluaXUuY29tIGh0dHA6Ly8xODMuNjAuMjE0LjE5OCJdfQ==";
    //项目token
//    public static final String TOKEN = "4jvwuLa_Xcux7WQ40KMO89DfinEuI3zXizMpwnc7:SHbzUwkFdCr-h9x77cI1v9yY4RE=:eyJzY29wZSI6InN0YXJzaGFyZWltYWdlIiwiZGVhZGxpbmUiOjE1MDM2NTQxMTJ9";

}
