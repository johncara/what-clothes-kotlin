package com.caracode.whatclothes.main;

import android.support.annotation.NonNull;

import net.grandcentrix.thirtyinch.TiPresenter;

public class MainPresenter extends TiPresenter<MainView> {

    @Override
    protected void onAttachView(@NonNull final MainView view) {
        super.onAttachView(view);

        view.onButtonPress()
                .subscribe(o -> view.showText("Hello 30 Inch"));
    }
}
