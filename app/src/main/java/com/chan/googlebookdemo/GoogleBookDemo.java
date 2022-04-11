package com.chan.googlebookdemo;

import android.app.Activity;
import android.app.Application;

import com.chan.googlebookdemo.data.network.RestApis;
import com.chan.googlebookdemo.data.network.RetroClient;

public class GoogleBookDemo extends Application {

    private static GoogleBookDemo _instance;
    private static Activity mCurrentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    public static GoogleBookDemo getAppContext() {
        return _instance;
    }

    public static RestApis getRestClient() {
        return RetroClient.getRetroClient().getApiServices();
    }

    public static Activity getCurrentActivity () {
        return mCurrentActivity ;
    }

    public static void setCurrentActivity (Activity currentActivity) {
        mCurrentActivity = currentActivity ;
    }
}
