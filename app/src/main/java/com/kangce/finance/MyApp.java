package com.kangce.finance;

import android.app.Application;

import com.kangce.finance.utils.L;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        L.init(this);
    }
}
