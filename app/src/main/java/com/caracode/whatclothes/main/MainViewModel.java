package com.caracode.whatclothes.main;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class MainViewModel {

    public abstract List<DayInfo> days();

    public static MainViewModel create(List<DayInfo> mainViewDays) {
            return new AutoValue_MainViewModel(mainViewDays);
    }

    @AutoValue
    public static abstract class DayInfo {

        @Nullable
        public abstract WeatherInfo weatherInfo();

        @Nullable
        public abstract String photoUrl();

        public static DayInfo create(WeatherInfo weatherInfo, String photoUrl) {
            return new AutoValue_MainViewModel_DayInfo(weatherInfo, photoUrl);
        }

        @AutoValue
        public static abstract class WeatherInfo {

            public abstract String readableDate();

            public abstract double minTemperature();

            public abstract double maxTemperature();

            public static WeatherInfo create(String readableDate, double minTemperature, double maxTemperature) {
                return new AutoValue_MainViewModel_DayInfo_WeatherInfo(readableDate, minTemperature, maxTemperature);
            }
        }
    }
}
