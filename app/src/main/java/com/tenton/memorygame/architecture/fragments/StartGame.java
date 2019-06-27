package com.tenton.memorygame.architecture.fragments;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertTheme;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.tenton.memorygame.R;
import com.tenton.memorygame.architecture.viewmodels.StartGameViewModel;
import com.tenton.memorygame.bluetooth.Bluetooth;
import com.tenton.memorygame.databinding.StartGameFragmentBinding;
import es.dmoral.toasty.Toasty;

public class StartGame extends Fragment {
    private StartGameViewModel mViewModel;
    private StartGameFragmentBinding binding;
    private String animal="dog";
    private PopupWindow pw;
    private FrameLayout frameLayout;
    private StartGameDirections.ActionStartGameFragmentToSinglePlayerFragment action;
    //CFAlertDialogBuilder
    private CFAlertDialog.Builder builder;
    private Bluetooth bluetooth;
    private static final int REQUEST_ENABLE_BT = 3;
    private AlertView alert;
    private AlertAction alertAction;

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
        frameLayout=(FrameLayout) getActivity().findViewById(R.id.frame_layout);
        navigateToSinglePlayer();
        navigateToMultiPlayer();
        navigateToBluetooth();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pw = new PopupWindow(
                inflater.inflate(R.layout.item_popup, null, false),
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );

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
                        //Permes konstruktorit i dergojme safe_args
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
                binding.parent.setAlpha(0.1f);
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                pw.getContentView().findViewById(R.id.btn_startGame).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText playerOneNameEt = pw.getContentView().findViewById(R.id.et_firstPlayerName);
                        String playerOneName = playerOneNameEt.getText().toString();
                        EditText playerTwoNameEt = pw.getContentView().findViewById(R.id.et_secondPlayerName);
                        String playerTwoName = playerTwoNameEt.getText().toString();

                        if(playerOneName.isEmpty() || playerTwoName.isEmpty()){
                            Toasty.normal(getContext(),"Please write players names!",Toasty.LENGTH_SHORT).show();
                        }else{
                            Navigation.findNavController(getView()).navigate(StartGameDirections.actionStartGameFragmentToMultiPlayer(animal,playerOneName,playerTwoName));

                            pw.dismiss();
                        }
                    }
                });
                pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        binding.parent.setAlpha(1f);
                    }
                });

            }
        });

    }

    private void navigateToBluetooth(){
        binding.btnMultiPlayerBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetooth = new Bluetooth(getContext());
                bluetooth.onStart();
                if(!bluetooth.isEnabled()){
                    AlertView alert = new AlertView("Bluetooth", "Do you want to enable bluetooth for you?", AlertStyle.BOTTOM_SHEET);
                    alert.addAction(new AlertAction("Yes", AlertActionStyle.DEFAULT, action -> {
                        bluetooth.enable();
                    }));
                    alert.addAction(new AlertAction("No", AlertActionStyle.NEGATIVE, action -> {
                        getExitTransition();
                    }));
                    alert.show((AppCompatActivity) getActivity());
                }
            }
        });
    }
}
