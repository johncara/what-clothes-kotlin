package com.caracode.whatclothes.api;

import com.caracode.whatclothes.main.FiveDayResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface WeatherApi {

    @GET("/data/2.5/forecast?lat=51.50853&lon=-0.12574&units=metric&appid=d52ea0fdd7c7767c590786b06d336d96")
    Single<FiveDayResponse> getWeather();
}
