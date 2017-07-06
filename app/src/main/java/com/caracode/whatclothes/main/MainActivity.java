package com.caracode.whatclothes.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.caracode.whatclothes.R;
import com.caracode.whatclothes.common.BaseActivity;
import com.caracode.whatclothes.common.ComponentManager;
import com.caracode.whatclothes.service.PhotoService;
import com.caracode.whatclothes.service.WeatherService;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends BaseActivity<MainPresenter, MainView> implements MainView {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    @Inject
    WeatherService weatherService;
    @Inject
    PhotoService photoService;
    @Inject
    CompositeDisposable networkDisposable;
    @Inject
    CompositeDisposable viewDisposable;

    private MainRecyclerAdapter mainRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainRecyclerAdapter = new MainRecyclerAdapter();
        rvMain.setAdapter(mainRecyclerAdapter);
    }

    @NonNull
    @Override
    public MainPresenter providePresenter() {
        ComponentManager.instance().get().inject(this);
        return new MainPresenter(weatherService, photoService, networkDisposable, viewDisposable);
    }

    @Override
    public void updateUi(MainViewModel mainViewModel) {
        mainRecyclerAdapter.setDayModels(mainViewModel.days());
    }
}
