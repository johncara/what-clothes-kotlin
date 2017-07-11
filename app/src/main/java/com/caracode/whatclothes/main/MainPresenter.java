package com.caracode.whatclothes.main;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.caracode.whatclothes.api.response.FiveDayResponse;
import com.caracode.whatclothes.common.Constants;
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

import static java.util.concurrent.TimeUnit.SECONDS;

class MainPresenter extends TiPresenter<MainView> {

    private static final long MAX_NUMBER_DAYS_TO_DISPLAY = 6;
    private static final int TIMEOUT_SECONDS = 10;
    private static final int NUM_RETRIES = 5;

    private final WeatherService weatherService;
    private final PhotoService photoService;
    private final CompositeDisposable networkDisposable;
    private final CompositeDisposable viewDisposable;

    private MainViewModel mainViewModel;

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

        if (mainViewModel != null) {
            updateUi(mainViewModel);
            return;
        }

        Single<FiveDayResponse> weather = weatherService.getWeather();

        Observable<FiveDayResponse.ThreeHourlyUpdate> threeHourlyUpdateObservable =
                weather.toObservable().share()
                        .flatMapIterable(FiveDayResponse::threeHourlyUpdates);

        Observable<String> displayableDates = threeHourlyUpdateObservable
                .map(threeHourlyUpdate -> threeHourlyUpdate.dateTime().toString(Constants.DATE_FORMAT))
                .distinctUntilChanged();

        Observable<GroupedObservable<Integer, FiveDayResponse.ThreeHourlyUpdate>> fiveDaysWeather =
                threeHourlyUpdateObservable
                        .groupBy(threeHourlyUpdate ->
                                Days.daysBetween(new DateTime().toLocalDate(), threeHourlyUpdate.dateTime().toLocalDate()).getDays());

        Observable<Double> minTemps = fiveDaysWeather.flatMap(groupedObs ->
                groupedObs
                        .map(update -> update.main().minTemp())
                        .reduce(Constants.MAX_TEMP, Math::min)
                        .toObservable());

        Observable<Double> maxTemps = fiveDaysWeather.flatMap(groupedObs ->
                groupedObs
                        .map(update -> update.main().maxTemp())
                        .reduce(Constants.MIN_TEMP, Math::max)
                        .toObservable());

        Observable<Pair<Integer, Integer>> clothesRefsUpperLower =
                Observable.zip(maxTemps, minTemps, ClothesPicker::getClothesUpperLower);

        Observable<String> photos = photoService
                .getPhotos()
                .toObservable()
                .flatMapIterable(photosResponse -> photosResponse.photos().photos())
                .repeat()
                .take(MAX_NUMBER_DAYS_TO_DISPLAY)
                .map(photo -> String.format(Constants.FLICKER_PHOTO_URL_FORMAT,
                        photo.farm(), photo.server(), photo.id(), photo.secret()))
                .onErrorResumeNext(Observable.fromIterable(Constants.BACKUP_PHOTOS));


        networkDisposable.add(
                Observable.zip(displayableDates, minTemps, maxTemps, photos, clothesRefsUpperLower, MainViewModel.DayModel::create)
                        .collectInto(new ArrayList<MainViewModel.DayModel>(), List::add)
                        .timeout(TIMEOUT_SECONDS, SECONDS)
                        .retry(NUM_RETRIES)
                .subscribe(
                        list -> {
                            mainViewModel = MainViewModel.create(list);
                            updateUi(mainViewModel);
                        },
                        Throwable::printStackTrace));

        viewDisposable.add(
                view.onFabClick()
                        .subscribe(o -> view.displayUnimplementedMessage()));
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
