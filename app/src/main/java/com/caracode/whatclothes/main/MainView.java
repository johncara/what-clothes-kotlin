package com.caracode.whatclothes.main;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface MainView extends TiView {

    @CallOnMainThread
    void showText(final String text);
}
