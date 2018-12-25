package com.example.jyj.choumou;

import android.app.Application;

import com.example.jyj.choumou.utils.L;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        L.init(this);
    }
}
