package com.tenton.memorygame.architecture.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tenton.memorygame.R;
import com.tenton.memorygame.architecture.adapters.SinglePlayerAdapter;
import com.tenton.memorygame.architecture.api.Api;
import com.tenton.memorygame.architecture.api.ServiceFactory;
import com.tenton.memorygame.architecture.models.ImageResponse;
import com.tenton.memorygame.architecture.models.ResponseData;
import com.tenton.memorygame.architecture.viewmodels.SinglePlayerViewModel;
import com.tenton.memorygame.databinding.SinglePlayerFragmentBinding;
import com.tenton.memorygame.utilities.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Single_Player extends Fragment {

    private SinglePlayerViewModel mViewModel;
    private SinglePlayerAdapter adapter;
    private SinglePlayerFragmentBinding binding;
    private String level;
    ArrayList<ImageResponse> imageResponse = new ArrayList<>();


    public static Single_Player newInstance() {
        return new Single_Player();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.single__player_fragment, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SinglePlayerViewModel.class);
        onLoad();
        mViewModel.init();

        level = Single_PlayerArgs.fromBundle(getArguments()).getLevel();
        if (level != null) {

            if (level.equals("easy")) {
                adapter = new SinglePlayerAdapter(imageResponse,getContext(),6, 200, 250, 10, 0, 10, 0);
                binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                binding.recyclerView.setAdapter(adapter);
            }
            if (level.equals("hard")) {
                adapter = new SinglePlayerAdapter(imageResponse,getContext(),12, 150, 200, 5, 0, 10, 0);
                binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
                binding.recyclerView.setAdapter(adapter);
            }
        }




    }


    private void onLoad(){
        binding.setMViewModel(mViewModel);

        mViewModel.imageResponse.observe(this,newResponse ->{
            imageResponse.addAll(newResponse);
            Collections.shuffle(imageResponse);
            adapter.notifyDataSetChanged();

        });
    }
}
