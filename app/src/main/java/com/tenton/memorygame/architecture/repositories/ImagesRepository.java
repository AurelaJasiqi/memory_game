package com.tenton.memorygame.architecture.repositories;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.tenton.memorygame.architecture.api.Api;
import com.tenton.memorygame.architecture.api.ServiceFactory;
import com.tenton.memorygame.architecture.models.ImageResponse;
import com.tenton.memorygame.architecture.models.Photo;
import com.tenton.memorygame.architecture.models.ResponseData;
import com.tenton.memorygame.utilities.Constants;

import java.util.ArrayList;
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

    public MutableLiveData<List<ImageResponse>> getResponse(String text,int imgNum) {

        responseDataMutableLiveData = new MutableLiveData<>();
        api.fetchImages(Constants.METHOD, Constants.API_KEY, Constants.FORMAT, Constants.NOJSONCALLBACK, text, Constants.EXTRAS)
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if(response.code() == 200){
                            ResponseData responseData=response.body();
                            if(responseData != null){
                               List<Photo> photos=responseData.photos.photo;
                               if(responseData.photos.photo !=null  && responseData.photos.photo.size()>6) {
imageResponses=new ArrayList<>();
                                   imageResponses.add(new ImageResponse(photos.get(0).url_o, 1, "p1id1"));
                                   imageResponses.add(new ImageResponse(photos.get(1).url_o, 2, "p2id1"));
                                   imageResponses.add(new ImageResponse(photos.get(2).url_o, 3, "p3id1"));
                                   imageResponses.add(new ImageResponse(photos.get(0).url_o, 1, "p1id2"));
                                   imageResponses.add(new ImageResponse(photos.get(1).url_o, 2, "p2id2"));
                                   imageResponses.add(new ImageResponse(photos.get(2).url_o, 3, "p3id2"));

                                   imageResponses.add(new ImageResponse(photos.get(3).url_o, 4, "p4id1"));
                                   imageResponses.add(new ImageResponse(photos.get(4).url_o, 5, "p5id1"));
                                   imageResponses.add(new ImageResponse(photos.get(5).url_o, 6, "p6id1"));


                                   imageResponses.add(new ImageResponse(photos.get(3).url_o, 4, "p4id2"));
                                   imageResponses.add(new ImageResponse(photos.get(4).url_o, 5, "p5id2"));
                                   imageResponses.add(new ImageResponse(photos.get(5).url_o, 6, "p6id2"));

                                   responseDataMutableLiveData.setValue(imageResponses);
                               }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        responseDataMutableLiveData.setValue(null);
                    }
                });

        return responseDataMutableLiveData;

    }

}
