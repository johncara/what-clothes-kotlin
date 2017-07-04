package com.caracode.whatclothes.main;

import android.support.annotation.NonNull;

import com.caracode.whatclothes.api.response.FiveDayResponse;
import com.caracode.whatclothes.api.response.PhotosResponse;
import com.caracode.whatclothes.service.PhotoService;
import com.caracode.whatclothes.service.WeatherService;

import net.grandcentrix.thirtyinch.TiPresenter;

import io.reactivex.disposables.CompositeDisposable;

class MainPresenter extends TiPresenter<MainView> {

    private final WeatherService weatherService;
    private final PhotoService photoService;
    private final CompositeDisposable networkDisposable;
    private final CompositeDisposable viewDisposable;

    MainPresenter(@NonNull WeatherService weatherService, @NonNull PhotoService photoService,
                  @NonNull CompositeDisposable networkDisposable, @NonNull CompositeDisposable viewDisposable) {
        this.weatherService = weatherService;
        this.photoService = photoService;
        this.networkDisposable = networkDisposable;
        this.viewDisposable = viewDisposable;
    }

    @Override
    protected void onAttachView(@NonNull final MainView view) {
        super.onAttachView(view);

        networkDisposable.add(
                weatherService.getWeather().subscribe(
                        this::showCoordinates,
                        Throwable::printStackTrace));

        networkDisposable.add(
                photoService.getPhotos().subscribe(
                        this::showPhotoId,
                        Throwable::printStackTrace));


        viewDisposable.add(
                view.onButtonPress().subscribe(
                        o -> view.showText("Hello 30 Inch")));
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

    private void showCoordinates(FiveDayResponse fiveDayResponse) {
        if (isViewAttached() && getView() != null) {
            getView().showText("DateTime: {" + fiveDayResponse.threeHourlyUpdates().get(0).dateTime() + "}");
        }
    }

    private void showPhotoId(PhotosResponse photosResponse) {
        if (isViewAttached() && getView() != null) {
            getView().showText("Photos: {" + photosResponse + "}");
        }
    }
}
