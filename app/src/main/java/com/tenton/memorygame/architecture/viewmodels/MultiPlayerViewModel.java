package com.tenton.memorygame.architecture.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tenton.memorygame.architecture.models.ImageResponse;
import com.tenton.memorygame.architecture.repositories.ImagesRepository;

import java.util.List;

public class MultiPlayerViewModel extends ViewModel {
    public MutableLiveData<List<ImageResponse>> multiPlayerListDataResponse;

    private ImagesRepository imagesRepository;

    //Pasi ne ViewModel do e bejme Api Callin, athere metoda qe e kemi shkruar ne ImageRepo ka nevoj per nje parameter,
    // te cilin do e marrim nga fragmenti permes konstruktorit te ViewModelit
    public MultiPlayerViewModel(String animal) {
        if (imagesRepository != null) {
            return;
        }

        imagesRepository = ImagesRepository.getInstance();
        multiPlayerListDataResponse = imagesRepository.getResponse(animal);

    }

}
