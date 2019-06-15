package com.tenton.memorygame.architecture.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("services/rest/?")
    Call<String> fetchImages(@Query("method")String method, @Query("api_key")String api_key, @Query("format")String format,
                           @Query("nojsoncallback")String nojsoncallback, @Query("text")String text,
                           @Query("extras")String extras);
}
