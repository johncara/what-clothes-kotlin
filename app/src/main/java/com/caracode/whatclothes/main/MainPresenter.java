package com.caracode.whatclothes.main;

import android.support.annotation.NonNull;

import net.grandcentrix.thirtyinch.TiPresenter;

import io.reactivex.disposables.Disposable;

public class MainPresenter extends TiPresenter<MainView> {

    private Disposable disposable;

    @Override
    protected void onAttachView(@NonNull final MainView view) {
        super.onAttachView(view);

        disposable = view.onButtonPress()
                .subscribe(o -> view.showText("Hello 30 Inch"));
    }

    @Override
    protected void onDetachView() {
        super.onDetachView();
        disposable.dispose();
    }
}
