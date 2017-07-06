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
    private static final long MAX_NUMBER_DAYS_TO_DISPLAY = 6;
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
                weather.toObservable().share()
                        .flatMapIterable(FiveDayResponse::threeHourlyUpdates);

        Observable<String> displayableDates = threeHourlyUpdateObservable
                .map(threeHourlyUpdate -> threeHourlyUpdate.dateTime().toString("EEE d MMM"))
                .distinctUntilChanged();

        Observable<GroupedObservable<Integer, FiveDayResponse.ThreeHourlyUpdate>> fiveDaysWeather =
                threeHourlyUpdateObservable
                        .groupBy(threeHourlyUpdate ->
                                Days.daysBetween(new DateTime().toLocalDate(), threeHourlyUpdate.dateTime().toLocalDate()).getDays());

        Observable<Double> minTemps = fiveDaysWeather.flatMap(groupedObs ->
                groupedObs
                        .map(update -> update.main().minTemp())
                        .reduce(MAX_TEMP, Math::min)
                        .toObservable());

        Observable<Double> maxTemps = fiveDaysWeather.flatMap(groupedObs ->
                groupedObs
                        .map(update -> update.main().maxTemp())
                        .reduce(MIN_TEMP, Math::max)
                        .toObservable());

        Observable<String> photos = photoService
                .getPhotos()
                .toObservable()
                .flatMapIterable(photosResponse -> photosResponse.photos().photos())
                .repeat()
                .take(MAX_NUMBER_DAYS_TO_DISPLAY)
                .map(photo -> String.format(FLICKER_PHOTO_URL_FORMAT,
                        photo.farm(), photo.server(), photo.id(), photo.secret()));


        networkDisposable.add(
                Observable.zip(displayableDates, minTemps, maxTemps, photos, MainViewModel.DayInfo::create)
                        .collectInto(new ArrayList<MainViewModel.DayInfo>(), List::add)
                .subscribe(
                        list -> updateUi(MainViewModel.create(list)),
                        Throwable::printStackTrace));


//        viewDisposable.add(
//                view.onButtonPress().subscribe(
//                        o -> view.updateUi("Changed date")));
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

    private void updateUi(MainViewModel mainViewModel) {
        if (isViewAttached() && getView() != null) {
            getView().updateUi(mainViewModel);
        }
    }
}
