package com.tenton.memorygame.architecture.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.tenton.memorygame.architecture.viewmodels.AvalaibleDevicesViewModel;
import com.tenton.memorygame.R;
import com.tenton.memorygame.databinding.AvalaibleDevicesFragmentBinding;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class AvalaibleDevices extends Fragment {

    private AvalaibleDevicesViewModel mViewModel;
    private AvalaibleDevicesFragmentBinding binding;
    private BluetoothAdapter bluetoothAdapter;

    ArrayList<String> discoverDevices = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    BluetoothDevice[] btArray;
    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FALIED = 4;
    static final int MESSAGE_RECEIVED = 5;

    private static final String APP_NAME = "MemoryGame";
    private static final UUID MYUUID = UUID.fromString("acc5a150-1be4-4353-97d8-3ff2827be654");

//    IntentFilter intentFilter1 = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
//    BroadcastReceiver scanModeReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
//                int modeValue = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);
//                if (modeValue == BluetoothAdapter.SCAN_MODE_CONNECTABLE) {
//                    Toast.makeText(getContext(), "Device is not discoverable but can receive connection!", Toast.LENGTH_SHORT).show();
//                } else if (modeValue == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
//                    Toast.makeText(getContext(), "Device is on discoverable mode!", Toast.LENGTH_SHORT).show();
//                } else if (modeValue == BluetoothAdapter.SCAN_MODE_NONE) {
//                    Toast.makeText(getContext(), "The device is not discoverable and can not receive connection!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    };

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
//        showDiscoveredDevices();
        implementListeners();

        if(binding.tvStatus.getText().equals("Connected")){
            Navigation.findNavController(getView()).navigate(AvalaibleDevicesDirections.actionAvalaibleDevices2ToBluetoothGame());
        }
    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                discoverDevices.add(device.getName());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    public void showPairedDevices() {
        binding.btnShowPaireddevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.pairedDevicesView.setVisibility(View.VISIBLE);
                binding.listViewPairedDevices.setVisibility(View.VISIBLE);
                Set<BluetoothDevice> bd = bluetoothAdapter.getBondedDevices();
                int index = 0;
                String[] strings = new String[bd.size()];
                btArray = new BluetoothDevice[bd.size()];
                if (bd.size() > 0) {
                    for (BluetoothDevice device : bd) {
                        btArray[index] = device;
                        strings[index] = device.getName();
                        index++;
                    }

                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, strings);
                    binding.listViewPairedDevices.setAdapter(arrayAdapter1);
                }
            }
        });
    }

//    public void showDiscoveredDevices() {
//        binding.btnShowDiscoverDevices.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bluetoothAdapter.startDiscovery();
//
//                IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//                getActivity().registerReceiver(myReceiver, intentFilter);
//
//                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 10);
//                startActivity(intent);
//
//                getActivity().registerReceiver(scanModeReceiver, intentFilter1);
//
//            }
//        });
//    }

    public void implementListeners() {

        binding.btnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerClass serverClass = new ServerClass();
                serverClass.start();
            }
        });

        binding.listViewPairedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClientClass clientClass = new ClientClass(btArray[position]);
                clientClass.start();
                binding.tvStatus.setText("Connecting");
            }
        });
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case STATE_LISTENING:
                    binding.tvStatus.setText("Listening");
                    break;

                case STATE_CONNECTING:
                    binding.tvStatus.setText("Connecting");
                    break;

                case STATE_CONNECTED:
                    binding.tvStatus.setText("Connected");
                    break;

                case STATE_CONNECTION_FALIED:
                    binding.tvStatus.setText("Failed");
                    break;

                case MESSAGE_RECEIVED:
                    //
                    break;
            }
            return true;
        }
    });

    private class ServerClass extends Thread{
        public BluetoothServerSocket serverSocket;

        public ServerClass(){
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME,MYUUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run(){
            BluetoothSocket blsocket = null;

            if( blsocket == null){
                try {
                    Message message= Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);
                    blsocket = serverSocket.accept();
                } catch (IOException e) {
                    Message message1= Message.obtain();
                    message1.what = STATE_CONNECTION_FALIED;
                    handler.sendMessage(message1);
                    e.printStackTrace();
                }

                if(blsocket != null){
                    Message message= Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);
                }
            }
        }
    }

    private class ClientClass extends Thread{
        public BluetoothDevice device;
        public BluetoothSocket clientSocket;


        public ClientClass(BluetoothDevice device1){
            device = device1;
            try {
                clientSocket = device.createRfcommSocketToServiceRecord(MYUUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run(){
            try {
                clientSocket.connect();
                Message message = Message.obtain();
                message.what = STATE_CONNECTED;
                handler.sendMessage(message);
            } catch (IOException e) {
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_FALIED;
                handler.sendMessage(message);
                e.printStackTrace();
                }
            }
        }
    }
