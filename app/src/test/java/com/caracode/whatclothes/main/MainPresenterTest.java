package com.caracode.whatclothes.main;

import com.caracode.whatclothes.api.response.FiveDayResponse;
import com.caracode.whatclothes.service.WeatherService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainView view;
    @Mock
    WeatherService weatherServiceMock;
    @Mock
    private FiveDayResponse fiveDayResponseMock;
    @Mock
    private FiveDayResponse.ThreeHourlyUpdate threeHourlyUpdateMock;

    CompositeDisposable networkDisposable = new CompositeDisposable();
    CompositeDisposable viewDisposable = new CompositeDisposable();

    private MainPresenter presenter;

    @Before
    public void before() {
        presenter = new MainPresenter(weatherServiceMock, networkDisposable, viewDisposable);
        presenter.create();
        when(view.onButtonPress()).thenReturn(Observable.never());
        when(weatherServiceMock.getWeather()).thenReturn(Single.never());
    }

    @Test
    public void testAttachShowText() {
        when(view.onButtonPress()).thenReturn(Observable.just(new Object()));

        presenter.attachView(view);

        verify(view).showText("Hello 30 Inch");
    }

    @After
    public void after() {
        presenter.detachView();
        presenter.destroy();
        assertTrue(presenter.isDestroyed());
    }
}
