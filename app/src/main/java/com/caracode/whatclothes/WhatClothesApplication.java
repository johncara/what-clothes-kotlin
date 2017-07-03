package com.caracode.whatclothes;

import android.app.Application;

import com.caracode.whatclothes.common.dagger.ApplicationComponent;
import com.caracode.whatclothes.common.dagger.ApplicationModule;
import com.caracode.whatclothes.common.dagger.DaggerApplicationComponent;

public class WhatClothesApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule()).build();
    }

}

