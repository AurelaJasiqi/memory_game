package com.tenton.memorygame.architecture.api;

import com.tenton.memorygame.utilities.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();}
        return retrofit;
    }

}