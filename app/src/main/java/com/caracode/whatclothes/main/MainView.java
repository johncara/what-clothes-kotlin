package com.caracode.whatclothes.main;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

public interface MainView extends TiView {

    @CallOnMainThread
    @DistinctUntilChanged
    void updateUi(final MainViewModel mainViewModel);
}
