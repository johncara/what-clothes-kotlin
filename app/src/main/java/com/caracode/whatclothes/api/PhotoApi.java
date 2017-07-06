package com.caracode.whatclothes.api;

import com.caracode.whatclothes.api.response.PhotosResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface PhotoApi {

    @GET("services/rest/?method=flickr.photos.search&api_key=9134eb4a1896a0f30f1814811f02a760&tags=scenery%2C+beautiful&lat=51.50853&lon=-0.12574&format=json&nojsoncallback=1&auth_token=72157685845978866-b9f24eada6e9f506&api_sig=9b62394de04066c44d8a62641d612f38")
    Single<PhotosResponse> getPhotos();
}
