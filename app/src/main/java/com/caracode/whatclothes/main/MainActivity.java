package com.caracode.whatclothes.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    @BindView(R.id.iv_main)
    ImageView ivMain;
    @BindView(R.id.tv_date_time)
    TextView tvDateTime;
    @BindView(R.id.tv_max_temp)
    TextView tvMaxTemp;
    @BindView(R.id.tv_min_temp)
    TextView tvMinTemp;
    @BindView(R.id.main_text)
    TextView tvHelloWorld;
    @BindView(R.id.button)
    Button bChange;

    @Inject
    WeatherService weatherService;
    @Inject
    PhotoService photoService;
    @Inject
    CompositeDisposable networkDisposable;
    @Inject
    CompositeDisposable viewDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @NonNull
    @Override
    public MainPresenter providePresenter() {
        ComponentManager.instance().get().inject(this);
        return new MainPresenter(weatherService, photoService, networkDisposable, viewDisposable);
    }

    @Override
    public Observable<Object> onButtonPress() {
        return RxView.clicks(bChange);
    }

    @Override
    public void showWeather(MainViewModel mainViewModel) {
        MainViewModel.DayInfo.WeatherInfo weatherInfo = mainViewModel.days().get(0).weatherInfo();
        if (weatherInfo != null) {
            tvDateTime.setText(weatherInfo.readableDate());
            tvMaxTemp.setText(getString(R.string.max_temp_format, weatherInfo.maxTemperature()));
            tvMinTemp.setText(getString(R.string.min_temp_format, weatherInfo.minTemperature()));
        }

    }

    @Override
    public void showPhoto(String photoUrl) {
        Glide.with(this).load(photoUrl).into(ivMain);
    }
}
