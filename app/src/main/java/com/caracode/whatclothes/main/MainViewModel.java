package com.caracode.whatclothes.main;

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

        public abstract String readableDate();

        public abstract double minTemperature();

        public abstract double maxTemperature();

        public abstract String photoUrl();

        public static DayInfo create(String readableDate, double minTemperature, double maxTemperature, String photoUrl) {
            return new AutoValue_MainViewModel_DayInfo(readableDate, minTemperature, maxTemperature, photoUrl);
        }
    }
}
