package com.caracode.whatclothes.main;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Weather {

    public static TypeAdapter<Weather> typeAdapter(Gson gson) {
        return new AutoValue_Weather.GsonTypeAdapter(gson);
    }

    @SerializedName("coord")
    public abstract Coordinates coordinates();

    @AutoValue
    public static abstract class Coordinates {

        public static TypeAdapter<Coordinates> typeAdapter(Gson gson) {
            return new AutoValue_Weather_Coordinates.GsonTypeAdapter(gson);
        }

        @SerializedName("lon")
        public abstract float longitude();

        @SerializedName("lat")
        public abstract float latitude();
    }
}
