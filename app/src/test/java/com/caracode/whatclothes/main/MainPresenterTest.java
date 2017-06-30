package com.caracode.whatclothes.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainView view;

    private MainPresenter presenter;

    @Before
    public void before() {
        presenter = new MainPresenter();
        presenter.create();
    }

    @Test
    public void testAttach() {
        presenter.attachView(view);
        verify(view).showText("Hello 30 Inch");
    }
}
