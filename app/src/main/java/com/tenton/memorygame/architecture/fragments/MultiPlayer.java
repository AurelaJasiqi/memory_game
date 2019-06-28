package com.tenton.memorygame.architecture.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.tenton.memorygame.architecture.adapters.MultiPlayerAdapter;
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


import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class MultiPlayer extends Fragment {

    private MultiPlayerViewModel mViewModel;
    private MultiPlayerFragmentBinding binding;
    private String animal;
    private MultiPlayerAdapter adapterMultiPlayer;
    private NetworkUtil networkUtil;
    private String playerOneName;
    private String playerTwoName;
    private String contentText = "";
    static String extraText="";
    private Integer p1Points = 0;
    private Integer p2Points = 0;
    private SweetAlertDialog dialog;
    private MultiPlayer fragment;
    int drawable;
    private MultiPlayerDirections.ActionMultiPlayerSelf action;



    List<ImageResponse> imageResponseMultiPlayer = new ArrayList<>();

    public static MultiPlayer newInstance() {
        return new MultiPlayer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.multi_player_fragment, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        animal = MultiPlayerArgs.fromBundle(getArguments()).getAnimalMultiPlayer();
        mViewModel = ViewModelProviders.of(this, new MultiPlayerViewmodelFactory(animal)).get(MultiPlayerViewModel.class);
        binding.setViewModel(mViewModel);
        mViewModel.setUpTimer();

        playerOneName = MultiPlayerArgs.fromBundle(getArguments()).getPlayerOneName();
        playerTwoName = MultiPlayerArgs.fromBundle(getArguments()).getPlayerTwoName();

        binding.linearlayoutPlayerOne.setBackgroundResource(R.drawable.playerturnbackground);
        binding.tvFirstPlayerName.setText(playerOneName);
        binding.tvSecondPlayerName.setText(playerTwoName);



        networkUtil = new NetworkUtil(getContext());
        if (!networkUtil.isConnected()) {
            Toasty.info(getContext(), "No internet connection. \n Offline mode ON!", Toast.LENGTH_SHORT, true).show();
            addPhotos();
            setAdapter();
            Collections.shuffle(imageResponseMultiPlayer);
            observeData();
            mViewModel.startTimer();
        } else {
            onLoad();
        }

    }

    private void onLoad() {
        mViewModel.multiPlayerListDataResponse.observe(this, newResponse -> {
            imageResponseMultiPlayer.addAll(newResponse);
            setAdapter();
            Collections.shuffle(imageResponseMultiPlayer);
            mViewModel.startTimer();
        });

        observeData();
    }

    public void setAdapter() {
        adapterMultiPlayer = new MultiPlayerAdapter(imageResponseMultiPlayer, getContext(),mViewModel);
        binding.rvMultiPlayer.setLayoutManager(new GridLayoutManager(getContext(), 4));
        binding.rvMultiPlayer.addItemDecoration(new EqualSpacingItemDecoration(dpToPx(5), EqualSpacingItemDecoration.GRID));
        binding.rvMultiPlayer.setAdapter(adapterMultiPlayer);
    }

    public void addPhotos() {
        imageResponseMultiPlayer.add(new ImageResponse(1, "p1id1", R.drawable.dog_icon));
        imageResponseMultiPlayer.add(new ImageResponse(2, "p2id1", R.drawable.sheep_icon));
        imageResponseMultiPlayer.add(new ImageResponse(3, "p3id1", R.drawable.lion_icon));

        imageResponseMultiPlayer.add(new ImageResponse(1, "p1id2", R.drawable.dog_icon));
        imageResponseMultiPlayer.add(new ImageResponse(2, "p2id2", R.drawable.sheep_icon));
        imageResponseMultiPlayer.add(new ImageResponse(3, "p3id2", R.drawable.lion_icon));

        imageResponseMultiPlayer.add(new ImageResponse(4, "p4id1", R.drawable.chicken_icon));
        imageResponseMultiPlayer.add(new ImageResponse(5, "p5id1", R.drawable.cow_icon));
        imageResponseMultiPlayer.add(new ImageResponse(6, "p6id1", R.drawable.bunny_icon));

        imageResponseMultiPlayer.add(new ImageResponse(4, "p4id2", R.drawable.chicken_icon));
        imageResponseMultiPlayer.add(new ImageResponse(5, "p5id2", R.drawable.cow_icon));
        imageResponseMultiPlayer.add(new ImageResponse(6, "p6id2", R.drawable.bunny_icon));
    }

    public int dpToPx(int dp) {
        return Math.round(dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public void SweetAlertDialogWarning() {
         dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(contentText)
                 .setContentText(extraText)
                 .setCustomImage(drawable)
                .setConfirmText("Restart Game")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        action = MultiPlayerDirections.actionMultiPlayerSelf(animal,playerOneName,playerTwoName);
                        Navigation.findNavController(getView()).navigate(action);
                        dialog.dismiss();

                    }
                })
                .setCancelButton("New Game", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        getActivity().onBackPressed();
                        dialog.dismiss();
                    }
                });
         dialog.setCancelable(false);
         dialog.show();

         mViewModel.countDownTimer.cancel();

    }

    public void observeData() {
        mViewModel.timeUntilFinished.observe(this, timeLeft -> {
            binding.tvTime.setVisibility(View.VISIBLE);
            binding.tvTimer.setText(timeLeft.toString());
        });
        mViewModel.gameOver.observe(this, gameOver -> {
            contentText = "Game Over!";
            extraText="Try again harder";
            drawable=R.drawable.game_over;
            SweetAlertDialogWarning();
        });

        mViewModel.p1PointsLive.observe(this, p1PointsFromLive ->{
            binding.tvP1Points.setText(Integer.toString(p1PointsFromLive));
            p1Points=p1PointsFromLive;
            checkForTheWinner();
        });

        mViewModel.p2PointsLive.observe(this, p2PointsFromLive ->{
            binding.tvP2Points.setText(Integer.toString(p2PointsFromLive));
            p2Points = p2PointsFromLive;
            checkForTheWinner();
        });

        mViewModel.turnLive.observe(this, turn ->{
            if(turn){
                binding.linearLayoutPlayerTwo.setBackgroundResource(R.drawable.playerturnbackground);
                binding.linearlayoutPlayerOne.setBackgroundResource(0);
            }else{
                binding.linearlayoutPlayerOne.setBackgroundResource(R.drawable.playerturnbackground);
                binding.linearLayoutPlayerTwo.setBackgroundResource(0);
            }
        });
    }

    public void checkForTheWinner(){
        if (p1Points + p2Points == 6){
            drawable=R.drawable.trophy;
            if(p1Points > p2Points){
                contentText = "Congrats!";
                extraText="The winner is:"+playerOneName;
                SweetAlertDialogWarning();

            }else if(p1Points < p2Points){
                contentText = "Congrats!";
                extraText="The winner is:"+playerTwoName;
                SweetAlertDialogWarning();

            }else if(p1Points == p2Points){
                contentText = "Try again!";
                extraText="No winner";
                SweetAlertDialogWarning();

            }else{
                contentText = "No no, it can't be, try again!";
                SweetAlertDialogWarning();
            }
        }
    }
}



