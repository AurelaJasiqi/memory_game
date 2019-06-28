package com.tenton.memorygame.architecture.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import java.util.ArrayList;
import java.util.Set;

public class AvalaibleDevices extends Fragment {

    private AvalaibleDevicesViewModel mViewModel;
    private AvalaibleDevicesFragmentBinding binding;
    private BluetoothAdapter bluetoothAdapter;

    ArrayList<String> discoverDevices = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
    BroadcastReceiver scanModeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED));
        }
    };

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
        showPairedDevices();
        showDiscoveredDevices();



    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                discoverDevices.add(device.getName());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    public void showPairedDevices(){
        binding.btnShowPaireddevices.setOnClickListener(new View.OnClickListener() {
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

                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,strings);
                    binding.listViewPairedDevices.setAdapter(arrayAdapter1);
                }
            }
        });
    }

    public void showDiscoveredDevices(){
        binding.btnShowDiscoverDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothAdapter.startDiscovery();

                IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                getActivity().registerReceiver(myReceiver,intentFilter);

                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,10);
                startActivity(intent);

                arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,discoverDevices);
                binding.listViewDiscoveredDevices.setAdapter(arrayAdapter);
            }
        });
    }
}
