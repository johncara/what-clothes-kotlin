package com.caracode.whatclothes.service;

import android.support.annotation.NonNull;

import com.caracode.whatclothes.api.WeatherApi;
import com.caracode.whatclothes.main.Weather;

import io.reactivex.Single;

public class WeatherService {

    private final WeatherApi weatherApi;

    public WeatherService(@NonNull WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public Single<Weather> getWeather() {
        return weatherApi.getWeather();
    }
}
