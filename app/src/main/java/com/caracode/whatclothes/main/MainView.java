package com.caracode.whatclothes.main;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

import io.reactivex.Observable;

public interface MainView extends TiView {

    @CallOnMainThread
    @DistinctUntilChanged
    void showWeather(final MainViewModel mainViewModel);

    @CallOnMainThread
    void showPhoto(String photoUrl);

    Observable<Object> onButtonPress();
}
