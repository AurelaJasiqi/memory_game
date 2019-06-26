package com.tenton.memorygame.architecture.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tenton.memorygame.architecture.adapters.MultiPlayerAdapter;
import com.tenton.memorygame.architecture.adapters.SinglePlayerAdapter;
import com.tenton.memorygame.architecture.models.ImageResponse;
import com.tenton.memorygame.architecture.viewmodels.MultiPlayerViewModel;
import com.tenton.memorygame.R;
import com.tenton.memorygame.architecture.viewmodelsfactory.MultiPlayerViewmodelFactory;
import com.tenton.memorygame.databinding.MultiPlayerFragmentBinding;
import com.tenton.memorygame.utilities.EqualSpacingItemDecoration;
import com.tenton.memorygame.utilities.NetworkUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class MultiPlayer extends Fragment {

    private MultiPlayerViewModel mViewModel;
    private MultiPlayerFragmentBinding binding;
    private String animal;
    private MultiPlayerAdapter adapterMultiPlayer;
    private CountDownTimer countDownTimer;
    private NetworkUtil networkUtil;


    List<ImageResponse> imageResponseMultiPlayer = new ArrayList<>();

    public static MultiPlayer newInstance() {
        return new MultiPlayer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.multi_player_fragment,container,false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        animal=MultiPlayerArgs.fromBundle(getArguments()).getAnimalMultiPlayer();
        mViewModel = ViewModelProviders.of(this, new MultiPlayerViewmodelFactory(animal)).get(MultiPlayerViewModel.class);
        binding.setViewModel(mViewModel);
        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Long timeInSeconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                binding.tvTimer.setText(timeInSeconds.toString());
            }

            @Override
            public void onFinish() {
                SweetAlertDialogWarning();
            }
        };

        networkUtil=new NetworkUtil(getContext());
        if(!networkUtil.isConnected()){
            Toasty.info(getContext(), "No internet connection. \n Offline mode ON!", Toast.LENGTH_SHORT, true).show();
            addPhotos();
            setAdapter();
            Collections.shuffle(imageResponseMultiPlayer);
            countDownTimer.start();
        }else {
            onLoad();
        }
    }

    private void onLoad(){
        mViewModel.multiPlayerListDataResponse.observe(this, newResponse -> {
            imageResponseMultiPlayer.addAll(newResponse);
            setAdapter();
            Collections.shuffle(imageResponseMultiPlayer);
            countDownTimer.start();
        });
    }

    public void setAdapter() {
        adapterMultiPlayer=new MultiPlayerAdapter(imageResponseMultiPlayer,getContext());
        binding.rvMultiPlayer.setLayoutManager(new GridLayoutManager(getContext(), 4));
        binding.rvMultiPlayer.addItemDecoration(new EqualSpacingItemDecoration(dpToPx(5),EqualSpacingItemDecoration.GRID));
        binding.rvMultiPlayer.setAdapter(adapterMultiPlayer);
            }

    public void addPhotos(){
        imageResponseMultiPlayer.add(new ImageResponse( 1, "p1id1",R.drawable.dog_icon));
        imageResponseMultiPlayer.add(new ImageResponse( 2, "p2id1",R.drawable.sheep_icon));
        imageResponseMultiPlayer.add(new ImageResponse( 3, "p3id1",R.drawable.lion_icon));

        imageResponseMultiPlayer.add(new ImageResponse( 1, "p1id2",R.drawable.dog_icon));
        imageResponseMultiPlayer.add(new ImageResponse( 2, "p2id2",R.drawable.sheep_icon));
        imageResponseMultiPlayer.add(new ImageResponse( 3, "p3id2",R.drawable.lion_icon));

        imageResponseMultiPlayer.add(new ImageResponse( 4, "p4id1",R.drawable.chicken_icon));
        imageResponseMultiPlayer.add(new ImageResponse( 5, "p5id1",R.drawable.cow_icon));
        imageResponseMultiPlayer.add(new ImageResponse( 6, "p6id1",R.drawable.bunny_icon));

        imageResponseMultiPlayer.add(new ImageResponse( 4, "p4id2",R.drawable.chicken_icon));
        imageResponseMultiPlayer.add(new ImageResponse( 5, "p5id2",R.drawable.cow_icon));
        imageResponseMultiPlayer.add(new ImageResponse( 6, "p6id2",R.drawable.bunny_icon));
    }

    public int  dpToPx(int dp) {
        return Math.round(dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public void SweetAlertDialogWarning(){
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Your time is over!")
                .setContentText("We think that you're a good player, try again!")
                .setConfirmText("Yes, try again!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        getActivity().finish();
                    }
                })
                .show();
    }

        }



