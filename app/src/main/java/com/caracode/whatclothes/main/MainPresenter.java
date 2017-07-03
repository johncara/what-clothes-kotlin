package com.caracode.whatclothes.main;

import android.support.annotation.NonNull;

import com.caracode.whatclothes.service.WeatherService;

import net.grandcentrix.thirtyinch.TiPresenter;

import io.reactivex.disposables.Disposable;

class MainPresenter extends TiPresenter<MainView> {

    private final WeatherService weatherService;
    private Disposable disposable;

    MainPresenter(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    protected void onAttachView(@NonNull final MainView view) {
        super.onAttachView(view);

        /*networkDisposable=*/ weatherService.getWeather()
                .subscribe(weather -> view.showText("Coordinates: {" +
                                weather.coordinates().longitude() + "," + weather.coordinates().latitude() + "}"),
                        Throwable::printStackTrace);

        disposable = view.onButtonPress()
                .subscribe(o -> view.showText("Hello 30 Inch"));
    }

    @Override
    protected void onDetachView() {
        super.onDetachView();
        disposable.dispose();
    }
}
