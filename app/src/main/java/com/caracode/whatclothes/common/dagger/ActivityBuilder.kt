package com.caracode.whatclothes.common.dagger


import com.caracode.whatclothes.main.MainActivity
import com.caracode.whatclothes.main.dagger.MainActivityModule
import com.caracode.whatclothes.main.dagger.MainScope

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @MainScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}
