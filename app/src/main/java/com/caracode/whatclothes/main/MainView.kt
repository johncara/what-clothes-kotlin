package com.caracode.whatclothes.main

import io.reactivex.Observable
import net.grandcentrix.thirtyinch.TiView
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged

interface MainView : TiView {

    @CallOnMainThread
    @DistinctUntilChanged
    fun updateUi(mainViewModel: MainViewModel)

    fun onFabClick(): Observable<Any>

    @CallOnMainThread
    fun displayUnimplementedMessage()
}
