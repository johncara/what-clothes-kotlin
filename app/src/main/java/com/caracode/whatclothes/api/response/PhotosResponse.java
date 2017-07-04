package com.caracode.whatclothes.api.response;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class PhotosResponse {

    public static TypeAdapter<PhotosResponse> typeAdapter(Gson gson) {
        return new AutoValue_PhotosResponse.GsonTypeAdapter(gson);
    }

    @SerializedName("photos")
    public abstract Photos photos();

    @AutoValue
    public static abstract class Photos {

        public static TypeAdapter<PhotosResponse.Photos> typeAdapter(Gson gson) {
            return new AutoValue_PhotosResponse_Photos.GsonTypeAdapter(gson);
        }

        @SerializedName("photo")
        public abstract List<Photo> photos();

        @AutoValue
        public static abstract class Photo {

            public static TypeAdapter<PhotosResponse.Photos.Photo> typeAdapter(Gson gson) {
                return new AutoValue_PhotosResponse_Photos_Photo.GsonTypeAdapter(gson);
            }

            @SerializedName("id")
            public abstract long id();

            @SerializedName("secret")
            public abstract String secret();

            @SerializedName("server")
            public abstract long server();

            @SerializedName("farm")
            public abstract long farm();
        }
    }
}
