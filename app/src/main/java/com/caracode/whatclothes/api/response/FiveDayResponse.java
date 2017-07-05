package com.caracode.whatclothes.api.response;

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

        @SerializedName("main")
        public abstract Main main();

        @AutoValue
        public static abstract class Main {
            public static TypeAdapter<Main> typeAdapter(Gson gson) {
                return new AutoValue_FiveDayResponse_ThreeHourlyUpdate_Main.GsonTypeAdapter(gson);
            }

            @SerializedName("temp_min")
            public abstract double minTemp();

            @SerializedName("temp_max")
            public abstract double maxTemp();

        }
    }
}
