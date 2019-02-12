package com.caracode.whatclothes.common.dagger

import android.app.Application

import com.caracode.whatclothes.WhatClothesApplication

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@ApplicationScope
@dagger.Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    ActivityBuilder::class])
interface ApplicationComponent {

    fun inject(app: WhatClothesApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

}
