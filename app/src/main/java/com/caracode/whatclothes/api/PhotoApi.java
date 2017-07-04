package com.caracode.whatclothes.api;

import com.caracode.whatclothes.api.response.PhotosResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface PhotoApi {

    @GET("/services/rest/?method=flickr.photos.search&api_key=10d45badcee01e477e4d56b5f285fda3&tags=scenery%2C+beautiful&lat=51.50853&lon=-0.12574&format=json&nojsoncallback=1&auth_token=72157683481135041-cabdb3e9693279d4&api_sig=a338b0c022fbc6e2b3c3e5f481e52b7f")
    Single<PhotosResponse> getPhotos();
}
