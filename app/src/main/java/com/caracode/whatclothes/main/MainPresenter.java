package com.caracode.whatclothes.main;

import android.support.annotation.NonNull;

import com.caracode.whatclothes.api.response.FiveDayResponse;
import com.caracode.whatclothes.service.PhotoService;
import com.caracode.whatclothes.service.WeatherService;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observables.GroupedObservable;

class MainPresenter extends TiPresenter<MainView> {

    private static final double MIN_TEMP = -100;
    private static final double MAX_TEMP = 100;
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

        Observable<FiveDayResponse.ThreeHourlyUpdate> threeHourlyUpdateObservable =
                weather.toObservable()
                        .flatMapIterable(FiveDayResponse::threeHourlyUpdates);

        Single<List<String>> displayableDates = threeHourlyUpdateObservable
                .map(threeHourlyUpdate -> threeHourlyUpdate.dateTime().toString("EEE d MMM"))
                .distinctUntilChanged()
                .collectInto(new ArrayList<>(), List::add);

        Observable<GroupedObservable<Integer, FiveDayResponse.ThreeHourlyUpdate>> fiveDaysWeather =
                threeHourlyUpdateObservable
                        .groupBy(threeHourlyUpdate -> Days.daysBetween(new DateTime().toLocalDate(), threeHourlyUpdate.dateTime().toLocalDate()).getDays());

        Single<ArrayList<Double>> minTemps = fiveDaysWeather.flatMap(groupedObs -> groupedObs
                .map(update -> update.main().minTemp())
                .reduce(MAX_TEMP, Math::min)
                .toObservable())
                .collectInto(new ArrayList<>(), List::add);

        Single<ArrayList<Double>> maxTemps = fiveDaysWeather.flatMap(groupedObs -> groupedObs
                .map(update -> update.main().maxTemp())
                .reduce(MIN_TEMP, Math::max)
                .toObservable())
                .collectInto(new ArrayList<>(), List::add);


        networkDisposable.add(
                Single.zip(displayableDates, minTemps, maxTemps, MainViewModel::createFromLists)
                .subscribe(
                        this::showWeather,
                        Throwable::printStackTrace));

        networkDisposable.add(
                photoService
                        .getPhotos()
                        .map(photosResponse -> photosResponse.photos().photos().get(3))
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

    private void showWeather(MainViewModel mainViewModel) {
        if (isViewAttached() && getView() != null) {
            getView().showWeather(mainViewModel);
        }
    }

    private void showPhoto(String photoUrl) {
        if (isViewAttached() && getView() != null) {
            getView().showPhoto(photoUrl);
        }
    }
}
