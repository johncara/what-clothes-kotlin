package com.caracode.whatclothes.common.dagger;

import com.caracode.whatclothes.common.DateTimeDeserializer;
import com.caracode.whatclothes.common.GsonAdapterFactory;
import com.caracode.whatclothes.main.dagger.MainActivityComponent;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(subcomponents = MainActivityComponent.class)
public class ApplicationModule {

    @Provides
    @ApplicationScope
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create(new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                    .registerTypeAdapterFactory(GsonAdapterFactory.create())
                    .create());
    }

    /**
     * Stetho allows easy inspection of API calls
     * Should never release code with this Interceptor in place
     * @return
     */
    @Provides
    @ApplicationScope
    OkHttpClient provideStethoOkHttpClient() {
        return new OkHttpClient().newBuilder().addNetworkInterceptor(new StethoInterceptor()).build();
    }

    @Provides
    @ApplicationScope
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
