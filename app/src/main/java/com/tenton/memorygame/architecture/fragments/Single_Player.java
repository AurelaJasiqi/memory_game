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
import com.tenton.memorygame.architecture.models.ResponseData;
import com.tenton.memorygame.architecture.viewmodels.SinglePlayerViewModel;
import com.tenton.memorygame.databinding.SinglePlayerFragmentBinding;
import com.tenton.memorygame.utilities.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Single_Player extends Fragment {

    private SinglePlayerViewModel mViewModel;
    private SinglePlayerAdapter adapter;
    private SinglePlayerFragmentBinding binding;
    private String level;
    private Api api;


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
        if (level != null) {
            if (level.equals("easy")) {

                adapter = new SinglePlayerAdapter(9, 200, 250, 10, 0, 10, 0);
                binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                binding.recyclerView.setAdapter(adapter);
            }
            if (level.equals("hard")) {
                adapter = new SinglePlayerAdapter(16, 150, 200, 5, 0, 10, 0);
                binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
                binding.recyclerView.setAdapter(adapter);
            }
        }
        apiCall();
    }

    private void apiCall(){

            api = ServiceFactory.getRetrofitInstance().create(Api.class);
        api.fetchImages(Constants.METHOD, Constants.API_KEY, Constants.FORMAT, Constants.NOJSONCALLBACK, "dogs", Constants.EXTRAS)
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if(response.code() == 200){
                            ResponseData responseData=response.body();
                            if(responseData != null){
//imageResponses.add(new ImageResponse(responseData.))
                                //     responseDataMutableLiveData
                                Toast.makeText(getContext(),responseData.photos.photo.get(0).url_o,Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {

                    }
                });

    }
}
