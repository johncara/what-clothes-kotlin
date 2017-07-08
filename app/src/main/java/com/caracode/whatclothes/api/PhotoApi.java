package com.caracode.whatclothes.api;

import com.caracode.whatclothes.api.response.PhotosResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoApi {

    @GET("/services/rest/")
    Single<PhotosResponse> getPhotos(@Query("method") String method, @Query("api_key") String apiKey, @Query("tags") String tags, @Query("lat") double lat,
                                     @Query("lon") double lon, @Query("format") String format, @Query("nojsoncallback") int noJsonCallback, @Query("auth_token") String authToken,
                                     @Query("api_sig") String apiSig);
}
