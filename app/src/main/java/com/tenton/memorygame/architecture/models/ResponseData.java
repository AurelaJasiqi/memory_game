package com.tenton.memorygame.architecture.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

    @SerializedName("photos")
    @Expose
   public Photos photos = null;

    @SerializedName("stat")
    @Expose
    public String stat = null;
}
