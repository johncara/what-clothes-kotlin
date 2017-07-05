package com.caracode.whatclothes.api;

import com.caracode.whatclothes.api.response.PhotosResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface PhotoApi {

    @GET("services/rest/?method=flickr.photos.search&api_key=34e3104d5b98390366242f35da9dcfbc&tags=scenery%2C+beautiful&lat=51.50853&lon=-0.12574&format=json&nojsoncallback=1&auth_token=72157683146330823-613ffef47685a463&api_sig=ac6066cc85be4205c2544cbbfcfedaee")
    Single<PhotosResponse> getPhotos();
}
