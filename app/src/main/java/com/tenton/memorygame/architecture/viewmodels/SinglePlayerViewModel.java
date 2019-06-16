package com.tenton.memorygame.architecture.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tenton.memorygame.architecture.models.ImageResponse;
import com.tenton.memorygame.architecture.repositories.ImagesRepository;

import java.util.List;

public class SinglePlayerViewModel extends ViewModel {
   public MutableLiveData<List<ImageResponse>> imageResponse;

    private ImagesRepository imagesRepository;
public SinglePlayerViewModel(){
    if (imageResponse != null){
        return;
    }
    imagesRepository = ImagesRepository.getInstance();
    imageResponse = imagesRepository.getResponse("chicken",6);

}
    public void init(String text){
        if (imageResponse != null){
            return;
        }
        imagesRepository = ImagesRepository.getInstance();
        imageResponse = imagesRepository.getResponse(text,6);

    }


    public LiveData<List<ImageResponse>> getImageRepository() {
        return imageResponse;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
