package com.tenton.memorygame.architecture.viewmodels;
import android.os.CountDownTimer;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tenton.memorygame.architecture.adapters.SinglePlayerAdapter;
import com.tenton.memorygame.architecture.models.ImageResponse;
import com.tenton.memorygame.architecture.repositories.ImagesRepository;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SinglePlayerViewModel extends ViewModel implements SinglePlayerAdapter.Listener {
   public MutableLiveData<List<ImageResponse>> imageResponse;
   public CountDownTimer countDownTimer;
public  MutableLiveData<Long> timeUntilFinished=new MutableLiveData<>();
public MutableLiveData<Boolean> gameOver=new MutableLiveData<>();
public MutableLiveData<Integer> point=new MutableLiveData<>();

    private ImagesRepository imagesRepository;

    public SinglePlayerViewModel(String param) {
    if (imageResponse != null) {
        return;
    }

    imagesRepository = ImagesRepository.getInstance();
    imageResponse = imagesRepository.getResponse(param);

}
    public void init(String text){
        if (imageResponse != null){
            return;
        }
        imagesRepository = ImagesRepository.getInstance();
        imageResponse = imagesRepository.getResponse(text);


    }
    public void setUpTimer(){
        countDownTimer=new CountDownTimer(30000,1000){
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


   public void cancelTimer(){
        countDownTimer.cancel();
   }

    @Override
    protected void onCleared() {
        super.onCleared();
        countDownTimer.cancel();
    }

    @Override
    public void itemClicked(int points) {

        point.setValue(points);
    }
}
