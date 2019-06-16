package com.tenton.memorygame.architecture.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.tenton.memorygame.utilities.NetworkUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Single_Player extends Fragment {

    private SinglePlayerViewModel mViewModel;
    private SinglePlayerAdapter adapter;
    private SinglePlayerFragmentBinding binding;
    private String level;
    List<ImageResponse> imageResponse = new ArrayList<>();
    private NetworkUtil networkUtil;


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
        level = Single_PlayerArgs.fromBundle(getArguments()).getLevel();
        networkUtil=new NetworkUtil(getContext());
        if(!networkUtil.isConnected()){
            Toasty.info(getContext(), "No internet connection. Offline mode!", Toast.LENGTH_SHORT, true).show();
            addPhotos();
            sliceArray();
            Collections.shuffle(imageResponse);
        }else {
            onLoad();
            mViewModel.init();}
        setAdapter();
    }

    private void onLoad() {
        binding.setMViewModel(mViewModel);
        mViewModel.imageResponse.observe(this, newResponse -> {
            imageResponse.addAll(newResponse);
            sliceArray();
            Collections.shuffle(imageResponse);
            adapter.notifyDataSetChanged();
        });
    }

public void setAdapter() {
    if (level != null) {
        if (level.equals("easy")) {
            adapter = new SinglePlayerAdapter(imageResponse, getContext(), 6, 200, 250, 10, 0, 10, 0);
            binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            binding.recyclerView.setAdapter(adapter);
        }
        if (level.equals("hard")) {
            adapter = new SinglePlayerAdapter(imageResponse, getContext(), 12, 150, 200, 5, 0, 10, 0);
            binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
            binding.recyclerView.setAdapter(adapter);
        }
    }
}

public void sliceArray(){
    if (level.equals("easy")){
        imageResponse=imageResponse.subList(0,6);
    }
}

public void addPhotos(){
    imageResponse.add(new ImageResponse( 1, "p1id1",R.drawable.dog_icon));
    imageResponse.add(new ImageResponse( 2, "p2id1",R.drawable.sheep_icon));
    imageResponse.add(new ImageResponse( 3, "p3id1",R.drawable.lion_icon));

    imageResponse.add(new ImageResponse( 1, "p1id2",R.drawable.dog_icon));
    imageResponse.add(new ImageResponse( 2, "p2id2",R.drawable.sheep_icon));
    imageResponse.add(new ImageResponse( 3, "p3id2",R.drawable.lion_icon));

    imageResponse.add(new ImageResponse( 4, "p4id1",R.drawable.chicken_icon));
    imageResponse.add(new ImageResponse( 5, "p5id1",R.drawable.cow_icon));
    imageResponse.add(new ImageResponse( 6, "p6id1",R.drawable.bunny_icon));


    imageResponse.add(new ImageResponse( 4, "p4id2",R.drawable.chicken_icon));
    imageResponse.add(new ImageResponse( 5, "p5id2",R.drawable.cow_icon));
    imageResponse.add(new ImageResponse( 6, "p6id2",R.drawable.bunny_icon));
}
}

