package com.caracode.whatclothes.main;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

@AutoValue
public abstract class FiveDayResponse {

    public static TypeAdapter<FiveDayResponse> typeAdapter(Gson gson) {
        return new AutoValue_FiveDayResponse.GsonTypeAdapter(gson);
    }

    @SerializedName("list")
    public abstract List<ThreeHourlyUpdate> threeHourlyUpdates();

    @AutoValue
    public static abstract class ThreeHourlyUpdate {

        public static TypeAdapter<ThreeHourlyUpdate> typeAdapter(Gson gson) {
            return new AutoValue_FiveDayResponse_ThreeHourlyUpdate.GsonTypeAdapter(gson);
        }

        @SerializedName("dt")
        public abstract DateTime dateTime();
    }
}
