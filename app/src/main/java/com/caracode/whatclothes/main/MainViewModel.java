package com.caracode.whatclothes.main;

import android.support.v4.util.Pair;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class MainViewModel {

    public abstract List<DayModel> days();

    public static MainViewModel create(List<DayModel> mainViewDays) {
            return new AutoValue_MainViewModel(mainViewDays);
    }

    @AutoValue
    public static abstract class DayModel {

        public abstract String readableDate();

        public abstract double minTemperature();

        public abstract double maxTemperature();

        public abstract String photoUrl();

        public abstract Pair<Integer, Integer> clothesRefsUpperLower();

        public static DayModel create(String readableDate, double minTemperature, double maxTemperature, String photoUrl, Pair<Integer, Integer> clothesRefsUpperLower) {
            return new AutoValue_MainViewModel_DayModel(readableDate, minTemperature, maxTemperature, photoUrl, clothesRefsUpperLower);
        }
    }
}
