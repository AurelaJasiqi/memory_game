package com.tenton.memorygame.bluetooth.interfaces;

public interface BluetoothCallback {
    void onBluetoothTurningOn();
    void onBluetoothOn();
    void onBluetoothTurningOff();
    void onBluetoothOff();
    void onUserDeniedActivation();
}