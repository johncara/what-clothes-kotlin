package com.caracode.whatclothes.service;

import android.support.annotation.NonNull;

import com.caracode.whatclothes.api.PhotoApi;
import com.caracode.whatclothes.api.response.PhotosResponse;

import io.reactivex.Single;

public class PhotoService {

    private final String API_KEY = "7d960f5068e6de7eded7322168c942e1";
    private final String AUTH_TOKEN = "72157683288550223-600076ef4b961b32";
    private final String API_SIG = "ba94aaa84ae3527a150a814e302bd809";

    private final String METHOD = "flickr.photos.search";
    private final String TAGS = "scenery, beautiful";
    private final double LAT = 51.50853;
    private final double LON = -0.12574;
    private final String FORMAT = "json";
    private final int NO_JSON_CALLBACK = 1;


    private final PhotoApi photoApi;

    public PhotoService(@NonNull PhotoApi photoApi) {
        this.photoApi = photoApi;
    }

    public Single<PhotosResponse> getPhotos() {
        return photoApi.getPhotos(METHOD, API_KEY, TAGS, LAT, LON, FORMAT, NO_JSON_CALLBACK, AUTH_TOKEN, API_SIG);
    }
}
