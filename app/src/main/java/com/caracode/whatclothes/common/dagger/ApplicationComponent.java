package com.caracode.whatclothes.common.dagger;

import android.app.Application;

import com.caracode.whatclothes.WhatClothesApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@ApplicationScope
@dagger.Component(modules = {
        AndroidInjectionModule.class,
        ApplicationModule.class,
        ActivityBuilder.class
})
public interface ApplicationComponent {

    void inject(WhatClothesApplication app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

}
