package com.caracode.whatclothes.main;

import android.support.annotation.NonNull;

import com.caracode.whatclothes.service.WeatherService;

import net.grandcentrix.thirtyinch.TiPresenter;

import io.reactivex.disposables.CompositeDisposable;

class MainPresenter extends TiPresenter<MainView> {

    private final WeatherService weatherService;
    private final CompositeDisposable networkDisposable;
    private final CompositeDisposable viewDisposable;

    MainPresenter(@NonNull WeatherService weatherService, @NonNull CompositeDisposable networkDisposable,
                  @NonNull CompositeDisposable viewDisposable) {
        this.weatherService = weatherService;
        this.networkDisposable = networkDisposable;
        this.viewDisposable = viewDisposable;
    }

    @Override
    protected void onAttachView(@NonNull final MainView view) {
        super.onAttachView(view);

        networkDisposable.add(
                weatherService.getWeather()
                            .subscribe(
                                this::showCoordinates,
                                Throwable::printStackTrace));

        viewDisposable.add(view.onButtonPress()
                .subscribe(o -> view.showText("Hello 30 Inch")));
    }

    @Override
    protected void onDetachView() {
        super.onDetachView();
        if (!viewDisposable.isDisposed()) {
            viewDisposable.dispose();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!networkDisposable.isDisposed()) {
            networkDisposable.dispose();
        }
    }

    private void showCoordinates(Weather weather) {
        if (isViewAttached() && getView() != null) {
            getView().showText("Coordinates: {"
                    + weather.coordinates().longitude() + ","
                    + weather.coordinates().latitude() + "}");
        }
    }
}
