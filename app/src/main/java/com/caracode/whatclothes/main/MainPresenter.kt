package com.caracode.whatclothes.main

import com.caracode.whatclothes.api.response.FiveDayResponse
import com.caracode.whatclothes.common.Constants
import com.caracode.whatclothes.service.PhotoService
import com.caracode.whatclothes.service.WeatherService

import net.grandcentrix.thirtyinch.TiPresenter

import org.joda.time.DateTime
import org.joda.time.Days

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function5

import java.util.concurrent.TimeUnit.SECONDS

class MainPresenter(private val weatherService: WeatherService, private val photoService: PhotoService,
                    private val networkDisposable: CompositeDisposable, private val viewDisposable: CompositeDisposable) : TiPresenter<MainView>() {

    private var mainViewModel: MainViewModel? = null

    override fun onAttachView(view: MainView) {
        super.onAttachView(view)

        mainViewModel?.let {
            updateUi(it)
            return
        }

        val weather = weatherService.weather

        val threeHourlyUpdateObservable = weather.toObservable().share()
                .flatMapIterable<FiveDayResponse.ThreeHourlyUpdate> { it.threeHourlyUpdates() }

        val displayableDates = threeHourlyUpdateObservable
                .map { threeHourlyUpdate -> threeHourlyUpdate.dateTime().toString(Constants.DATE_FORMAT) }
                .distinctUntilChanged()

        val fiveDaysWeather = threeHourlyUpdateObservable
                .groupBy { threeHourlyUpdate -> Days.daysBetween(DateTime().toLocalDate(), threeHourlyUpdate.dateTime().toLocalDate()).days }

        val minTemps = fiveDaysWeather.flatMap { groupedObs ->
            groupedObs
                    .map { update -> update.main().minTemp() }
                    .reduce(Constants.MAX_TEMP, { a, b -> Math.min(a, b) })
                    .toObservable()
        }

        val maxTemps = fiveDaysWeather.flatMap { groupedObs ->
            groupedObs
                    .map { update -> update.main().maxTemp() }
                    .reduce(Constants.MIN_TEMP, { a, b -> Math.max(a, b) })
                    .toObservable()
        }

        val clothesRefsUpperLower = Observable.zip(maxTemps, minTemps, BiFunction<Double, Double, Pair<Int, Int>> { maxTemp, minTemp -> ClothesPicker.getClothesUpperLower(maxTemp, minTemp) })

        val photos = photoService
                .photos
                .toObservable()
                .flatMapIterable { photosResponse -> photosResponse.photos().photos() }
                .repeat()
                .take(MAX_NUMBER_DAYS_TO_DISPLAY)
                .map { photo ->
                    String.format(Constants.FLICKER_PHOTO_URL_FORMAT,
                            photo.farm(), photo.server(), photo.id(), photo.secret())
                }
                .onErrorResumeNext(Observable.fromIterable(Constants.BACKUP_PHOTOS))

        networkDisposable.add(
            Observable.zip(displayableDates, minTemps, maxTemps, photos, clothesRefsUpperLower, Function5(::DayModel))
                .collectInto(ArrayList<DayModel>(), { obj, e -> obj.add(e) })
                .timeout(TIMEOUT_SECONDS.toLong(), SECONDS)
                .retry(NUM_RETRIES.toLong())
                .subscribe(
                        { list ->
                            mainViewModel = MainViewModel(list)
                            mainViewModel?.let { updateUi(it) }
                        },
                        { it.printStackTrace() })
        )

        viewDisposable.add(
                view.onFabClick()
                        .subscribe { _ -> view.displayUnimplementedMessage() })
    }

    override fun onDetachView() {
        super.onDetachView()
        if (!viewDisposable.isDisposed) {
            viewDisposable.dispose()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!networkDisposable.isDisposed) {
            networkDisposable.dispose()
        }
    }

    private fun updateUi(mainViewModel: MainViewModel) {
        if (isViewAttached && view != null) {
            view!!.updateUi(mainViewModel)
        }
    }

    companion object {

        private const val MAX_NUMBER_DAYS_TO_DISPLAY: Long = 6
        private const val TIMEOUT_SECONDS = 10
        private const val NUM_RETRIES = 5
    }
}
