package com.tenton.memorygame.architecture.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photos {
    @SerializedName("page")
    public int page;

    @SerializedName("pages")
    public int pages;

    @SerializedName("total")
    public String total = null;

    @SerializedName("photo")
   public List<Photo> photo;
}
