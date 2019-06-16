package com.tenton.memorygame.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;

public class NetworkUtil {
    private ConnectivityManager connectivityManager;
    private Context context;
    private NetworkInfo networkInfo = null;
    private InetAddress ipAddr;

   public NetworkUtil(Context ctx){
        this.context=ctx;
    }

        public Boolean isConnected () {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                try {
                    ipAddr = InetAddress.getByName("google.com");
                    if (!ipAddr.equals("")) {
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        }
    }
