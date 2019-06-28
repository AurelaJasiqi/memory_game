package com.tenton.memorygame.architecture.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tenton.memorygame.architecture.viewmodels.AvalaibleDevicesViewModel;
import com.tenton.memorygame.R;
import com.tenton.memorygame.databinding.AvalaibleDevicesFragmentBinding;

import java.util.Set;

public class AvalaibleDevices extends Fragment {

    private AvalaibleDevicesViewModel mViewModel;
    private AvalaibleDevicesFragmentBinding binding;
    private BluetoothAdapter bluetoothAdapter;

    public static AvalaibleDevices newInstance() {
        return new AvalaibleDevices();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.avalaible_devices_fragment, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AvalaibleDevicesViewModel.class);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        show();

    }

    public void show(){
        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> bd = bluetoothAdapter.getBondedDevices();
                int index = 0;
                String[] strings = new String[bd.size()];
                if(bd.size() > 0){
                    for(BluetoothDevice device:bd){
                        strings[index] = device.getName();
                        index++ ;
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,strings);
                    binding.listView.setAdapter(arrayAdapter);
                }
            }
        });
    }
}
