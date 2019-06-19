package com.tenton.memorygame.architecture.viewmodelsfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tenton.memorygame.architecture.viewmodels.MultiPlayerViewModel;
import com.tenton.memorygame.architecture.viewmodels.SinglePlayerViewModel;

public class MultiPlayerViewmodelFactory implements ViewModelProvider.Factory {
    private String mParam;
    public MultiPlayerViewmodelFactory(String param) {
        mParam = param;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MultiPlayerViewModel(mParam);
    }
}
