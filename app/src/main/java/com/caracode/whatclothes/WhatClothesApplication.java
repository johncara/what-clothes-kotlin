package com.caracode.whatclothes;

import android.app.Application;

import com.caracode.whatclothes.common.ComponentManager;
import com.caracode.whatclothes.common.dagger.ApplicationComponent;
import com.caracode.whatclothes.common.dagger.ApplicationModule;
import com.caracode.whatclothes.common.dagger.DaggerApplicationComponent;
import com.facebook.stetho.Stetho;

public class WhatClothesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule()).build();
        ComponentManager.instance().set(applicationComponent);
        Stetho.initializeWithDefaults(this);
    }

}


