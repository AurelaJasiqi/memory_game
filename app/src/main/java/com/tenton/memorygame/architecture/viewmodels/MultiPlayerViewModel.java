package com.tenton.memorygame.architecture.viewmodels;

import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tenton.memorygame.architecture.adapters.MultiPlayerAdapter;
import com.tenton.memorygame.architecture.models.ImageResponse;
import com.tenton.memorygame.architecture.repositories.ImagesRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MultiPlayerViewModel extends ViewModel implements MultiPlayerAdapter.Listener{
    public MutableLiveData<List<ImageResponse>> multiPlayerListDataResponse;
    public CountDownTimer countDownTimer;
    public  MutableLiveData<Long> timeUntilFinished=new MutableLiveData<>();
    public MutableLiveData<Boolean> gameOver=new MutableLiveData<>();


    private ImagesRepository imagesRepository;
    public MutableLiveData<Integer> p1PointsLive = new MutableLiveData<>();
    public MutableLiveData<Integer> p2PointsLive = new MutableLiveData<>();

    public MutableLiveData<Boolean> turnLive =new MutableLiveData<>();


    //Pasi ne ViewModel do e bejme Api Callin, athere metoda qe e kemi shkruar ne ImageRepo ka nevoj per nje parameter,
    // te cilin do e marrim nga fragmenti permes konstruktorit te ViewModelit
    public MultiPlayerViewModel(String animal) {
        if (imagesRepository != null) {
            return;
        }
        imagesRepository = ImagesRepository.getInstance();
        multiPlayerListDataResponse = imagesRepository.getResponse(animal);
    }

    public void setUpTimer(){
        countDownTimer=new CountDownTimer(60000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timeUntilFinished.setValue(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                gameOver.setValue(false);
            }
        };
    }
    public void startTimer(){
        countDownTimer.start();
    }

    @Override
    public void onPointsChanged(int p1Points, int p2Points) {
        p1PointsLive.postValue(p1Points);
        p2PointsLive.postValue(p2Points);
    }

    @Override
    public void checkTurn(Boolean turn) {
        turnLive.postValue(turn);
    }
}
