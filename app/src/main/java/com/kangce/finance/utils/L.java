package com.kangce.finance.utils;

import android.app.Application;

import com.kangce.finance.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


/**
 * @description: L 日志工具类
 * @data 2018/1/24-16:45
 * @author: AoJiaoQiang
 */
public class L {


    private L() {
    }

    /**
     * 初始化
     */
    public static void init(Application application) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .tag(application.getPackageName())
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.d(msg);
        }
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.i(msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.e(msg);
        }
    }

    public static void json(String json) {
        if (BuildConfig.DEBUG) {
            Logger.json(json);
        }
    }

}
