package com.caracode.whatclothes.service;

import android.support.annotation.NonNull;

import com.caracode.whatclothes.api.PhotoApi;
import com.caracode.whatclothes.api.response.PhotosResponse;

import io.reactivex.Single;

public class PhotoService {

    private final PhotoApi photoApi;

    public PhotoService(@NonNull PhotoApi photoApi) {
        this.photoApi = photoApi;
    }

    public Single<PhotosResponse> getPhotos() {
        return photoApi.getPhotos();
    }
}
