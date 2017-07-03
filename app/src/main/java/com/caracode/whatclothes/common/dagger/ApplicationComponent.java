package com.caracode.whatclothes.common.dagger;

import com.caracode.whatclothes.main.MainActivity;

@ApplicationScope
@dagger.Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

}