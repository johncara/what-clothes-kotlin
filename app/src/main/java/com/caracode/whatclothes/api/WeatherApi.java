package com.caracode.whatclothes.api;

import com.caracode.whatclothes.main.Weather;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface WeatherApi {

    @GET("/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1")
    Single<Weather> getWeather();
}
