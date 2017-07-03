package com.caracode.whatclothes.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertTrue;
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

    @After
    public void after() {
        presenter.detachView();
        presenter.destroy();
        assertTrue(presenter.isDestroyed());
    }
}
