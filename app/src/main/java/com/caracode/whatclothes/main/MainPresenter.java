package com.caracode.whatclothes.main;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.caracode.whatclothes.api.response.FiveDayResponse;
import com.caracode.whatclothes.service.PhotoService;
import com.caracode.whatclothes.service.WeatherService;

import net.grandcentrix.thirtyinch.TiPresenter;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

class MainPresenter extends TiPresenter<MainView> {

    private static final String FLICKER_PHOTO_URL_FORMAT = "https://farm%1$s.staticflickr.com/%2$s/%3$s_%4$s.jpg";

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

        Single<FiveDayResponse> weather = weatherService.getWeather();
        Single<String> displayableDate =
                weather.map(fiveDayResponse ->
                                fiveDayResponse.threeHourlyUpdates().get(0).dateTime().toString("EEE d MMM"));
        Single<Double> minTemperature =
                weather.toObservable()
                        .flatMapIterable(FiveDayResponse::threeHourlyUpdates)
                        .reduce((double) 100, (running, thisUpdate) ->
                                Math.min(running, thisUpdate.main().minTemp()));

        networkDisposable.add(
                Single.zip(displayableDate, minTemperature, Pair::new)
                .subscribe(
                        this::showWeather,
                        Throwable::printStackTrace));



//        networkDisposable.add(
//                weatherService.getWeather()
//                        .subscribe(
//                                this::showWeather,
//                                Throwable::printStackTrace));

        networkDisposable.add(
                photoService
                        .getPhotos()
                        .map(photosResponse -> photosResponse.photos().photos().get(2))
                        .map(photo -> String.format(FLICKER_PHOTO_URL_FORMAT,
                                                photo.farm(), photo.server(), photo.id(), photo.secret()))
                        .subscribe(
                                this::showPhoto,
                                Throwable::printStackTrace));


//        viewDisposable.add(
//                view.onButtonPress().subscribe(
//                        o -> view.showWeather("Changed date")));
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

//    private void showDate(FiveDayResponse fiveDayResponse) {
//        if (isViewAttached() && getView() != null) {
//            getView().showWeather(fiveDayResponse.threeHourlyUpdates().get(0).dateTime().toString("EEE d MMM"));
//        }
//    }

    private void showWeather(Pair<String, Double> dateAndMinTemp) {
        if (isViewAttached() && getView() != null) {
            getView().showWeather(dateAndMinTemp);
        }
    }

    private void showPhoto(String photoUrl) {
        if (isViewAttached() && getView() != null) {
            getView().showPhoto(photoUrl);
        }
    }
}
