package com.caracode.whatclothes.common.dagger;

import android.support.annotation.NonNull;

import com.caracode.whatclothes.api.WeatherApi;
import com.caracode.whatclothes.common.GsonAdapterFactory;
import com.caracode.whatclothes.service.NetworkService;
import com.caracode.whatclothes.service.WeatherService;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    @Provides
    @ApplicationScope
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create(new GsonBuilder()
                    .registerTypeAdapterFactory(GsonAdapterFactory.create())
                    .create());
    }

    @Provides
    @ApplicationScope
    NetworkService provideNetworkService(@NonNull GsonConverterFactory gsonConverterFactory) {
        return new NetworkService(gsonConverterFactory, new OkHttpClient().newBuilder().build());
    }

    @Provides
    @ApplicationScope
    WeatherService provideWeatherService(@NonNull NetworkService networkService) {
        return new WeatherService(networkService.createService(WeatherApi.class));
    }

    @Provides
    @ApplicationScope
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}