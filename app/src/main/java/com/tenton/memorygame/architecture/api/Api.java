package com.tenton.memorygame.architecture.api;

import com.tenton.memorygame.architecture.models.ResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("services/rest/?")
    Call<ResponseData> fetchImages(@Query("method") String method, @Query("api_key") String api_key, @Query("format") String format,
                                   @Query("nojsoncallback") String nojsoncallback, @Query("text") String text,
                                   @Query("extras") String extras);
}
