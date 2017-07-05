package com.caracode.whatclothes.main;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MainViewModel {

    public abstract String readableDate();

    public abstract double minTemperature();

    public abstract double maxTemperature();

    public static MainViewModel create(String readableDate, double minTemperature, double maxTemperature) {
        return new AutoValue_MainViewModel(readableDate, minTemperature, maxTemperature);
    }
}
