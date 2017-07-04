package com.caracode.whatclothes.main;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import io.reactivex.Observable;

public interface MainView extends TiView {

    @CallOnMainThread
    void showText(final String text);

    @CallOnMainThread
    void showPhoto(String photoUrl);

    Observable<Object> onButtonPress();
}
