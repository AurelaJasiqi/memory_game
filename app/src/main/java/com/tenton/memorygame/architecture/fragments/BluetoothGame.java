package com.tenton.memorygame.architecture.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tenton.memorygame.R;
import com.tenton.memorygame.architecture.viewmodels.BluetoothGameViewModel;

public class BluetoothGame extends Fragment {

    private BluetoothGameViewModel mViewModel;

    public static BluetoothGame newInstance() {
        return new BluetoothGame();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bluetooth_game_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BluetoothGameViewModel.class);
        // TODO: Use the ViewModel
    }

}
