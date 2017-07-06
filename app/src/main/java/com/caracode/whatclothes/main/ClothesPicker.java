package com.caracode.whatclothes.main;

import android.support.annotation.DrawableRes;
import android.support.v4.util.Pair;

import com.caracode.whatclothes.R;

class ClothesPicker {

    private ClothesPicker() {

    }

    // ---------------------  UPPER WEAR  ----------------------------------

    // I will wear a t-shirt if the min is higher 15 and the max is higher than 20
    interface TShirtWeather {
        double MIN_TEMP_ABOVE = 15;
        double MAX_TEMP_ABOVE = 20;
        @DrawableRes int res = R.drawable.tshirt;
    }

    // I will wear a jacket if the min is below 12 and the max is below 18
    interface JacketWeather {
        double MIN_TEMP_BELOW = 12;
        double MAX_TEMP_BELOW = 18;
        @DrawableRes int res = R.drawable.jacket;
    }

    // I will wear a coat if the min is below 8 and the max is below 12
    interface CoatWeather {
        double MIN_TEMP_BELOW = 8;
        double MAX_TEMP_BELOW = 12;
        @DrawableRes int res = R.drawable.coat;
    }

    // ... Otherwise I default to jumper


    // ---------------------  LOWER WEAR  ----------------------------------

    // I wear shorts if the min is higher than 17 and the max is higher than 22
    interface ShortsWeather {
        double MIN_TEMP_ABOVE = 17;
        double MAX_TEMP_ABOVE = 22;
        @DrawableRes int res = R.drawable.shorts;
    }

    // ... Otherwise I default to trousers

    static Pair<Integer, Integer> getClothesUpperLower(double maxTemp, double minTemp) {

        int upper;
        if (minTemp > TShirtWeather.MIN_TEMP_ABOVE && maxTemp > TShirtWeather.MAX_TEMP_ABOVE) {
            upper = TShirtWeather.res;
        } else if (minTemp < JacketWeather.MIN_TEMP_BELOW && maxTemp < JacketWeather.MAX_TEMP_BELOW) {
            upper = JacketWeather.res;
        } else if (minTemp < CoatWeather.MIN_TEMP_BELOW && maxTemp < CoatWeather.MAX_TEMP_BELOW) {
            upper = CoatWeather.res;
        } else {
            upper = R.drawable.jumper;
        }

        Integer lower;
        if (minTemp > ShortsWeather.MIN_TEMP_ABOVE && maxTemp > ShortsWeather.MAX_TEMP_ABOVE) {
            lower = ShortsWeather.res;
        } else {
            lower = R.drawable.trousers;
        }

        return new Pair<>(upper, lower);
    }
}
