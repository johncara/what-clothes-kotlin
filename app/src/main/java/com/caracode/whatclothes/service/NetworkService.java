package com.caracode.whatclothes.service;

import android.support.annotation.NonNull;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String DEFAULT_WEATHER_URL = "http://api.openweathermap.org";

    private final Retrofit retrofit;

    public NetworkService(@NonNull GsonConverterFactory gsonConverterFactory, @NonNull OkHttpClient stethoClient) {
        retrofit = new Retrofit.Builder()
                .baseUrl(DEFAULT_WEATHER_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(stethoClient)
                .build();
    }

    @NonNull
    public <T> T createService(final Class<T> service) {
        return retrofit.create(service);
    }

    @NonNull
    public <T> T createService(final Class<T> service, String url) {
        return retrofit.newBuilder().baseUrl(url).build().create(service);
    }
}
