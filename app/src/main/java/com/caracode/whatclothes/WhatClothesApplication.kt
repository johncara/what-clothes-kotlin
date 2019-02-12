package com.caracode.whatclothes

import android.app.Activity
import android.app.Application
import com.caracode.whatclothes.common.dagger.DaggerApplicationComponent

import com.facebook.stetho.Stetho

import net.danlew.android.joda.JodaTimeAndroid

import javax.inject.Inject

import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

class WhatClothesApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
        Stetho.initializeWithDefaults(this)
        JodaTimeAndroid.init(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }
}


