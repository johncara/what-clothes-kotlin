package com.caracode.whatclothes.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainView view;

    private MainPresenter presenter;

    @Before
    public void before() {
        presenter = new MainPresenter();
        presenter.create();
        when(view.onButtonPress()).thenReturn(Observable.never());
    }

    @Test
    public void testAttach() {
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
