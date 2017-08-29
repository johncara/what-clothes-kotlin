package com.caracode.whatclothes.main.dagger;


import com.caracode.whatclothes.main.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@MainScope
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
    }

}
