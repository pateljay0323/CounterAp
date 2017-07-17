package com.jay.counterapp;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by baldor on 4/7/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(MyApplication.this);
    }
}
