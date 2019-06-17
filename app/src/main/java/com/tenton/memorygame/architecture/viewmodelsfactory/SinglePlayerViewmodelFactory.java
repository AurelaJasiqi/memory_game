package com.tenton.memorygame.architecture.viewmodelsfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tenton.memorygame.architecture.viewmodels.SinglePlayerViewModel;

public class SinglePlayerViewmodelFactory implements ViewModelProvider.Factory {
    private String mParam;
    public SinglePlayerViewmodelFactory(String param) {
        mParam = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SinglePlayerViewModel(mParam);
    }
}
