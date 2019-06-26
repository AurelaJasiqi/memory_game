package com.tenton.memorygame.architecture.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
import com.tenton.memorygame.architecture.models.ImageResponse;
import com.tenton.memorygame.architecture.viewmodels.SinglePlayerViewModel;
import com.tenton.memorygame.architecture.viewmodelsfactory.SinglePlayerViewmodelFactory;
import com.tenton.memorygame.databinding.SinglePlayerFragmentBinding;
import com.tenton.memorygame.utilities.EqualSpacingItemDecoration;
import com.tenton.memorygame.utilities.NetworkUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Single_Player extends Fragment implements SinglePlayerAdapter.Listener {

    private SinglePlayerViewModel mViewModel;
    private SinglePlayerAdapter adapter;
    private SinglePlayerFragmentBinding binding;
    private String level;
    private String animal;
    List<ImageResponse> imageResponse = new ArrayList<>();
    private NetworkUtil networkUtil;
    private int maxPoints;

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
        animal = Single_PlayerArgs.fromBundle(getArguments()).getAnimal();
        mViewModel = ViewModelProviders.of(this, new SinglePlayerViewmodelFactory(animal)).get(SinglePlayerViewModel.class);
        mViewModel.setUpTimer();

        level = Single_PlayerArgs.fromBundle(getArguments()).getLevel();
        networkUtil = new NetworkUtil(getContext());
        if (!networkUtil.isConnected()) {
            Toasty.info(getContext(), "No internet connection. \n Offline mode ON!", Toast.LENGTH_SHORT, true).show();
            addPhotos();
            sliceArray();
            setAdapter();
            Collections.shuffle(imageResponse);
            onLoadTimer();
            mViewModel.startTimer();
        } else {
            onLoad();
            mViewModel.init("chicken");
        }


    }

    private void onLoad() {
        binding.setMViewModel(mViewModel);
        mViewModel.imageResponse.observe(this, newResponse -> {

            imageResponse.addAll(newResponse);
            sliceArray();
            setAdapter();
            Collections.shuffle(imageResponse);
            adapter.notifyDataSetChanged();
            mViewModel.startTimer();

        });

        onLoadTimer();

    }

    private void onLoadTimer() {

        mViewModel.timeUntilFinished.observe(this, timeLeft -> {
            binding.time.setText(timeLeft.toString());
        });
        mViewModel.gameOver.observe(this, gameOver -> {
            Toasty.error(getContext(), "Game over", Toasty.LENGTH_LONG).show();
        });

    }

    public void setAdapter() {
        if (level != null) {
            if (level.equals("easy")) {
                adapter = new SinglePlayerAdapter(imageResponse, getContext(), 6, dpToPx(80), dpToPx(100), this);
                binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                binding.recyclerView.addItemDecoration(new EqualSpacingItemDecoration(dpToPx(10), EqualSpacingItemDecoration.GRID));
                binding.recyclerView.setAdapter(adapter);
                maxPoints = 3;
            }
            if (level.equals("hard")) {
                adapter = new SinglePlayerAdapter(imageResponse, getContext(), 12, dpToPx(65), dpToPx(90), this);
                binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
                binding.recyclerView.addItemDecoration(new EqualSpacingItemDecoration(dpToPx(5), EqualSpacingItemDecoration.GRID));
                binding.recyclerView.setAdapter(adapter);
                maxPoints = 6;
            }
        }
    }

    public void sliceArray() {
        if (level.equals("easy")) {
            imageResponse = imageResponse.subList(0, 6);
        }
    }

    public void addPhotos() {
        imageResponse.add(new ImageResponse(1, "p1id1", R.drawable.dog_icon));
        imageResponse.add(new ImageResponse(2, "p2id1", R.drawable.sheep_icon));
        imageResponse.add(new ImageResponse(3, "p3id1", R.drawable.lion_icon));

        imageResponse.add(new ImageResponse(1, "p1id2", R.drawable.dog_icon));
        imageResponse.add(new ImageResponse(2, "p2id2", R.drawable.sheep_icon));
        imageResponse.add(new ImageResponse(3, "p3id2", R.drawable.lion_icon));

        imageResponse.add(new ImageResponse(4, "p4id1", R.drawable.chicken_icon));
        imageResponse.add(new ImageResponse(5, "p5id1", R.drawable.cow_icon));
        imageResponse.add(new ImageResponse(6, "p6id1", R.drawable.bunny_icon));


        imageResponse.add(new ImageResponse(4, "p4id2", R.drawable.chicken_icon));
        imageResponse.add(new ImageResponse(5, "p5id2", R.drawable.cow_icon));
        imageResponse.add(new ImageResponse(6, "p6id2", R.drawable.bunny_icon));
    }

    public int dpToPx(int dp) {
        return Math.round(dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void itemClicked(int points) {
        binding.score.setText(Integer.toString(points));
        if (points == maxPoints) {
            Toasty.success(getContext(), "Congrats", Toasty.LENGTH_LONG).show();
            mViewModel.cancelTimer();
        }
    }

}

