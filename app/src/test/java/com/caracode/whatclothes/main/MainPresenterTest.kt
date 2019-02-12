package com.caracode.whatclothes.main

import com.caracode.whatclothes.R
import com.caracode.whatclothes.api.response.FiveDayResponse
import com.caracode.whatclothes.api.response.PhotosResponse
import com.caracode.whatclothes.common.Constants
import com.caracode.whatclothes.service.PhotoService
import com.caracode.whatclothes.service.WeatherService

import org.joda.time.DateTime
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

import junit.framework.Assert.assertTrue
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    private lateinit var viewMock: MainView
    @Mock
    private lateinit var weatherServiceMock: WeatherService
    @Mock
    private lateinit var photoServiceMock: PhotoService
    @Mock
    private lateinit var fiveDayResponseMock: FiveDayResponse
    @Mock
    private lateinit var threeHourlyUpdateMock: FiveDayResponse.ThreeHourlyUpdate
    @Mock
    private lateinit var photosResponseMock: PhotosResponse
    @Mock
    private lateinit var updateMainMock: com.caracode.whatclothes.api.response.FiveDayResponse.ThreeHourlyUpdate.Main

    private var networkDisposable = CompositeDisposable()
    private var viewDisposable = CompositeDisposable()

    private lateinit var presenter: MainPresenter

    @Before
    fun before() {
        presenter = MainPresenter(weatherServiceMock, photoServiceMock, networkDisposable, viewDisposable)
        presenter.create()
        `when`(viewMock.onFabClick()).thenReturn(Observable.never())
        `when`(weatherServiceMock.weather).thenReturn(Single.never())
        `when`(photoServiceMock.photos).thenReturn(Single.never())
    }

    @Test
    fun testFabClick() {
        `when`(viewMock.onFabClick()).thenReturn(Observable.just(Any()))

        presenter.attachView(viewMock)

        verify<MainView>(viewMock).displayUnimplementedMessage()
    }

    @Test
    fun testWeatherWithDefaultPhotos() {
        val dateTime = DateTime(2017, 1, 15, 0, 0)
        val readableDate = "Sun 15 Jan"
        val maxTemp = 23.47
        val minTemp = 7.21
        arrangeWeatherMocks(dateTime, minTemp, maxTemp)
        arrangePhotoMocks()

        presenter.attachView(viewMock)

        verify<MainView>(viewMock).updateUi(MainViewModel(listOf(DayModel(readableDate, minTemp, maxTemp, Constants.BACKUP_PHOTOS[0],
                Pair(R.drawable.jumper, R.drawable.trousers)))))
    }

    @Test
    fun testRestoringState() {
        val dateTime = DateTime(2020, 3, 8, 0, 0)
        val readableDate = "Sun 8 Mar"
        val maxTemp = 15.19
        val minTemp = -3.42
        arrangeWeatherMocks(dateTime, minTemp, maxTemp)
        arrangePhotoMocks()

        presenter.attachView(viewMock)

        presenter.detachView()
        reset<MainView>(viewMock)

        presenter.attachView(viewMock)
        verify<MainView>(viewMock).updateUi(MainViewModel(listOf(DayModel(readableDate, minTemp, maxTemp, Constants.BACKUP_PHOTOS[0],
                Pair(R.drawable.coat, R.drawable.trousers)))))
    }

    @After
    fun after() {
        presenter.detachView()
        presenter.destroy()
        assertTrue(presenter.isDestroyed)
    }

    private fun arrangeWeatherMocks(dateTime: DateTime, minTemp: Double, maxTemp: Double) {
        `when`(weatherServiceMock.weather).thenReturn(Single.just(fiveDayResponseMock))
        `when`(fiveDayResponseMock.threeHourlyUpdates()).thenReturn(listOf(threeHourlyUpdateMock))
        `when`(threeHourlyUpdateMock.dateTime()).thenReturn(dateTime)
        `when`(threeHourlyUpdateMock.main()).thenReturn(updateMainMock)
        `when`(updateMainMock.maxTemp()).thenReturn(maxTemp)
        `when`(updateMainMock.minTemp()).thenReturn(minTemp)
    }

    private fun arrangePhotoMocks() {
        `when`(photoServiceMock.photos).thenReturn(Single.just(photosResponseMock))
    }
}
