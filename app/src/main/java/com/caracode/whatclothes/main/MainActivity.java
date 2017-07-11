package com.caracode.whatclothes.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;

import com.caracode.whatclothes.R;
import com.caracode.whatclothes.common.BaseActivity;
import com.caracode.whatclothes.common.ComponentManager;
import com.caracode.whatclothes.service.PhotoService;
import com.caracode.whatclothes.service.WeatherService;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends BaseActivity<MainPresenter, MainView> implements MainView {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;

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

    @Override
    public Observable<Object> onFabClick() {
        return RxView.clicks(fab);
    }

    @Override
    public void displayUnimplementedMessage() {
        Snackbar snackbar = Snackbar.make(fab, R.string.edit_temperatures_coming_soon, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.dismiss, v -> snackbar.dismiss()).show();
    }
}
