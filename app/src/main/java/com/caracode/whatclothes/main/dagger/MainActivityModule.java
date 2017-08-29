package com.caracode.whatclothes.main.dagger;

import com.caracode.whatclothes.api.PhotoApi;
import com.caracode.whatclothes.api.WeatherApi;
import com.caracode.whatclothes.main.MainPresenter;
import com.caracode.whatclothes.service.NetworkService;
import com.caracode.whatclothes.service.PhotoService;
import com.caracode.whatclothes.service.WeatherService;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MainActivityModule {

    @Provides
    @MainScope
    NetworkService provideNetworkService(GsonConverterFactory gsonConverterFactory, OkHttpClient stethoClient) {
        return new NetworkService(gsonConverterFactory, stethoClient);
    }

    @Provides
    @MainScope
    WeatherService provideWeatherService(NetworkService networkService) {
        return new WeatherService(networkService.createService(WeatherApi.class));
    }

    @Provides
    @MainScope
    PhotoService providePhotoService(NetworkService networkService) {
        return new PhotoService(networkService.createService(PhotoApi.class, "https://api.flickr.com"));
    }

    @Provides
    @MainScope
    MainPresenter provideMainPresenter(WeatherService weatherService, PhotoService photoService, CompositeDisposable networkDisposable, CompositeDisposable viewDisposable) {
        return new MainPresenter(weatherService, photoService, networkDisposable, viewDisposable);
    }
}


