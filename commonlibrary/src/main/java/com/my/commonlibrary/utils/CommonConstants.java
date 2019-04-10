package com.my.commonlibrary.utils;
import android.os.Environment;
public class CommonConstants {

	/******************** 时间相关常量 ********************/
    /**
     * 毫秒与毫秒的倍数
     */
    public static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = 86400000;

    /******************** 时间相关常量 ********************/

    public static String SDCARDPATH = Environment.getExternalStorageDirectory() + "/";

    // #log
    public static final boolean LOG = true;

    public static final String LOG_TAG = "MyBabyProfessional";


}
