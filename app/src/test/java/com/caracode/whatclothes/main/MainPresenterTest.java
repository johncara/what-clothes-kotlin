package com.caracode.whatclothes.main;

import android.support.v4.util.Pair;

import com.caracode.whatclothes.R;
import com.caracode.whatclothes.api.response.FiveDayResponse;
import com.caracode.whatclothes.api.response.PhotosResponse;
import com.caracode.whatclothes.common.Constants;
import com.caracode.whatclothes.service.PhotoService;
import com.caracode.whatclothes.service.WeatherService;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainView viewMock;
    @Mock
    WeatherService weatherServiceMock;
    @Mock
    PhotoService photoServiceMock;
    @Mock
    private FiveDayResponse fiveDayResponseMock;
    @Mock
    private FiveDayResponse.ThreeHourlyUpdate threeHourlyUpdateMock;
    @Mock
    private PhotosResponse photosResponseMock;
    @Mock
    private com.caracode.whatclothes.api.response.FiveDayResponse.ThreeHourlyUpdate.Main updateMainMock;

    CompositeDisposable networkDisposable = new CompositeDisposable();
    CompositeDisposable viewDisposable = new CompositeDisposable();

    private MainPresenter presenter;

    @Before
    public void before() {
        presenter = new MainPresenter(weatherServiceMock, photoServiceMock, networkDisposable, viewDisposable);
        presenter.create();
        when(viewMock.onFabClick()).thenReturn(Observable.never());
        when(weatherServiceMock.getWeather()).thenReturn(Single.never());
        when(photoServiceMock.getPhotos()).thenReturn(Single.never());
    }

    @Test
    public void testFabClick() {
        when(viewMock.onFabClick()).thenReturn(Observable.just(new Object()));

        presenter.attachView(viewMock);

        verify(viewMock).displayUnimplementedMessage();
    }

    @Test
    public void testWeatherWithDefaultPhotos() {
        final DateTime dateTime = new DateTime(2017, 1, 15, 0, 0);
        final String readableDate = "Sun 15 Jan";
        final double maxTemp = 23.47;
        final double minTemp = 7.21;
        arrangeWeatherMocks(dateTime, minTemp, maxTemp);
        arrangePhotoMocks();

        presenter.attachView(viewMock);

        verify(viewMock).updateUi(MainViewModel.create(Collections.singletonList(
                MainViewModel.DayModel.create(readableDate, minTemp, maxTemp, Constants.BACKUP_PHOTOS.get(0),
                        new Pair<>(R.drawable.jumper, R.drawable.trousers)))));
    }

    @Test
    public void testRestoringState() {
        final DateTime dateTime = new DateTime(2020, 3, 8, 0, 0);
        final String readableDate = "Sun 8 Mar";
        final double maxTemp = 15.19;
        final double minTemp = -3.42;
        arrangeWeatherMocks(dateTime, minTemp, maxTemp);
        arrangePhotoMocks();

        presenter.attachView(viewMock);

        presenter.detachView();
        reset(viewMock);

        presenter.attachView(viewMock);
        verify(viewMock).updateUi(MainViewModel.create(Collections.singletonList(
                MainViewModel.DayModel.create(readableDate, minTemp, maxTemp, Constants.BACKUP_PHOTOS.get(0),
                        new Pair<>(R.drawable.jacket, R.drawable.trousers)))));
    }

    @After
    public void after() {
        presenter.detachView();
        presenter.destroy();
        assertTrue(presenter.isDestroyed());
    }

    private void arrangeWeatherMocks(DateTime dateTime, double minTemp, double maxTemp) {
        when(weatherServiceMock.getWeather()).thenReturn(Single.just(fiveDayResponseMock));
        when(fiveDayResponseMock.threeHourlyUpdates()).thenReturn(Collections.singletonList(threeHourlyUpdateMock));
        when(threeHourlyUpdateMock.dateTime()).thenReturn(dateTime);
        when(threeHourlyUpdateMock.main()).thenReturn(updateMainMock);
        when(updateMainMock.maxTemp()).thenReturn(maxTemp);
        when(updateMainMock.minTemp()).thenReturn(minTemp);
    }

    private void arrangePhotoMocks() {
        when(photoServiceMock.getPhotos()).thenReturn(Single.just(photosResponseMock));
    }
}
