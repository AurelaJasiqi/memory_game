package com.tenton.memorygame.architecture.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.tenton.memorygame.R;
import com.tenton.memorygame.architecture.viewmodels.StartGameViewModel;
import com.tenton.memorygame.databinding.StartGameFragmentBinding;

public class StartGame extends Fragment {

    private StartGameViewModel mViewModel;
    private StartGameFragmentBinding binding;
    private String animal="dog";

    private StartGameDirections.ActionStartGameFragmentToSinglePlayerFragment action;



    //CFAlertDialogBuilder
    private CFAlertDialog.Builder builder;

    public static StartGame newInstance() {
        return new StartGame();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.start_game_fragment, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StartGameViewModel.class);
        binding.setViewModel(mViewModel);
        navigateToSinglePlayer();
        navigateToMultiPlayer();
        binding.segmentedAnimalBtn.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(int position) {
                animal=binding.segmentedAnimalBtn.getButton(position).getTag().toString();
            }
        });

    }

    private void navigateToSinglePlayer() {
        builder = new CFAlertDialog.Builder(getContext()).setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setMessage("Choose level: ")
                .setTextColor(getResources().getColor(R.color.whitee))
                .setTextGravity(1).setDialogBackgroundColor(getResources().getColor(R.color.dark_pink))
                .setCornerRadius(25f);
        builder.setCancelable(true);
        builder.addButton("EASY", getResources().getColor(R.color.yellow), getResources().getColor(R.color.dark_blue),
                CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        action = StartGameDirections.actionStartGameFragmentToSinglePlayerFragment("easy",animal);
                        Navigation.findNavController(getView()).navigate(action);
                        dialog.dismiss();
                    }
                });
        builder.addButton("HARD", getResources().getColor(R.color.yellow), getResources().getColor(R.color.dark_blue),
                CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Permes konstruktorit i dergojme afe_args
                        action = StartGameDirections.actionStartGameFragmentToSinglePlayerFragment("hard",animal);
                        Navigation.findNavController(getView()).navigate(action);
                        dialog.dismiss();
                    }
                });

        binding.btnSinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });

    }

    private void navigateToMultiPlayer() {
        binding.btnMultiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getView()).navigate(StartGameDirections.actionStartGameFragmentToMultiPlayer(animal));
            }
        });

    }

}
