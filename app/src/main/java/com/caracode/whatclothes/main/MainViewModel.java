package com.caracode.whatclothes.main;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

@AutoValue
public abstract class MainViewModel {

    public abstract List<MainViewDay> mainViewDays();

    private static MainViewModel create(List<MainViewDay> mainViewDays) {
            return new AutoValue_MainViewModel(mainViewDays);
    }

    public static MainViewModel createFromLists(List<String> readableDates, List<Double> minTemperatures, List<Double> maxTemperatures) {
        List<MainViewDay> mainViewDays = new ArrayList<>();
        for (int i = 0; i < readableDates.size(); i++) {
            mainViewDays.add(MainViewDay.create(readableDates.get(i), minTemperatures.get(i), maxTemperatures.get(i)));
        }
        return MainViewModel.create(mainViewDays);
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
