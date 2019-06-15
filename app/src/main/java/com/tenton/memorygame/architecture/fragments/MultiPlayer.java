package com.tenton.memorygame.architecture.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tenton.memorygame.architecture.viewmodels.MultiPlayerViewModel;
import com.tenton.memorygame.R;

public class MultiPlayer extends Fragment {

    private MultiPlayerViewModel mViewModel;

    public static MultiPlayer newInstance() {
        return new MultiPlayer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.multi_player_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MultiPlayerViewModel.class);
    }

}
