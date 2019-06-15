package com.tenton.memorygame.architecture.repositories;

import androidx.lifecycle.MutableLiveData;

import com.tenton.memorygame.architecture.api.Api;
import com.tenton.memorygame.architecture.api.ServiceFactory;
import com.tenton.memorygame.architecture.models.ImageResponse;
import com.tenton.memorygame.architecture.models.ResponseData;
import com.tenton.memorygame.utilities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagesRepository {

    private static ImagesRepository imagesRepository;
    private Api api;
    MutableLiveData<List<ImageResponse>> responseDataMutableLiveData;
    List<ImageResponse> imageResponses;

    public static ImagesRepository getInstance() {
        if (imagesRepository == null) {
            imagesRepository = new ImagesRepository();
        }
        return imagesRepository;
    }

    public ImagesRepository() {
        api = ServiceFactory.getRetrofitInstance().create(Api.class);
    }

    /*
    public MutableLiveData<List<ImageResponse>> getNews(String text,int imgNum) {
        responseDataMutableLiveData = new MutableLiveData<>();
        api.fetchImages(Constants.METHOD, Constants.API_KEY, Constants.FORMAT, Constants.NOJSONCALLBACK, text, Constants.EXTRAS)
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if(response.code() == 200){
                            ResponseData responseData=response.body();
                            if(responseData != null){
//imageResponses.add(new ImageResponse(responseData.))
                       //     responseDataMutableLiveData
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {

                    }
                });


    }*/

}
