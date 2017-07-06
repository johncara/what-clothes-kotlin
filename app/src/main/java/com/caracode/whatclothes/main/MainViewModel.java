package com.caracode.whatclothes.main;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class MainViewModel {

    public abstract List<MainViewDay> mainViewDays();

    public static MainViewModel create(List<MainViewDay> mainViewDays) {
            return new AutoValue_MainViewModel(mainViewDays);
    }

    @AutoValue
    public static abstract class MainViewDay {

        public abstract String readableDate();

        public abstract double minTemperature();

        public abstract double maxTemperature();

        public static MainViewDay create(String readableDate, double minTemperature, double maxTemperature) {
            return new AutoValue_MainViewModel_MainViewDay(readableDate, minTemperature, maxTemperature);
        }
    }
}
