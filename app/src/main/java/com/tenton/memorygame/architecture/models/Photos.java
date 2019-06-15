package com.tenton.memorygame.architecture.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photos {
    @SerializedName("page")
    int page;
    @SerializedName("pages")
    int pages;
    @SerializedName("total")
    String total=null;
    @SerializedName("photo")
    List<Photo> photo;
}
