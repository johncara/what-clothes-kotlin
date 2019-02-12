package com.caracode.whatclothes.common.dagger

import com.caracode.whatclothes.common.DateTimeDeserializer
import com.caracode.whatclothes.common.GsonAdapterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder

import org.joda.time.DateTime

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApplicationModule {

    @Provides
    @ApplicationScope
    internal fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder()
                .registerTypeAdapter(DateTime::class.java, DateTimeDeserializer())
                .registerTypeAdapterFactory(GsonAdapterFactory.create())
                .create())
    }

    /**
     * Stetho allows easy inspection of API calls
     * Should never release code with this Interceptor in place
     * @return
     */
    @Provides
    @ApplicationScope
    internal fun provideStethoOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().addNetworkInterceptor(StethoInterceptor()).build()
    }

    @Provides
    @ApplicationScope
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}
