package com.caracode.whatclothes.main;

import android.support.v4.util.Pair;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import io.reactivex.Observable;

public interface MainView extends TiView {

    @CallOnMainThread
    void showWeather(final MainViewModel mainViewModel);

    @CallOnMainThread
    void showPhoto(String photoUrl);

    Observable<Object> onButtonPress();
}
