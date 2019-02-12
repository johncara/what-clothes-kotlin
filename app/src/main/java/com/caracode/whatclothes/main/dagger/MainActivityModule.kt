package com.caracode.whatclothes.main.dagger

import com.caracode.whatclothes.api.PhotoApi
import com.caracode.whatclothes.api.WeatherApi
import com.caracode.whatclothes.main.MainPresenter
import com.caracode.whatclothes.service.NetworkService
import com.caracode.whatclothes.service.PhotoService
import com.caracode.whatclothes.service.WeatherService

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

@Module
class MainActivityModule {

    @Provides
    @MainScope
    internal fun provideNetworkService(gsonConverterFactory: GsonConverterFactory, stethoClient: OkHttpClient): NetworkService {
        return NetworkService(gsonConverterFactory, stethoClient)
    }

    @Provides
    @MainScope
    internal fun provideWeatherService(networkService: NetworkService): WeatherService {
        return WeatherService(networkService.createService(WeatherApi::class.java))
    }

    @Provides
    @MainScope
    internal fun providePhotoService(networkService: NetworkService): PhotoService {
        return PhotoService(networkService.createService(PhotoApi::class.java, "https://api.flickr.com"))
    }

    @Provides
    @MainScope
    internal fun provideMainPresenter(weatherService: WeatherService, photoService: PhotoService, networkDisposable: CompositeDisposable, viewDisposable: CompositeDisposable): MainPresenter {
        return MainPresenter(weatherService, photoService, networkDisposable, viewDisposable)
    }
}


