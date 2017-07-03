package com.caracode.whatclothes.common.dagger;

import com.caracode.whatclothes.service.NetworkService;

@ApplicationScope
@dagger.Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    NetworkService networkService();

}