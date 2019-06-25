package com.tenton.memorygame.architecture.models;

import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("id")
    public String id = null;

    @SerializedName("owner")
    public String owner = null;

    @SerializedName("secret")
    public String secret = null;

    @SerializedName("server")
    public String server = null;

    @SerializedName("farm")
    public String farm = null;

    @SerializedName("title")
    public String title = null;

    @SerializedName("ispublic")
    public String ispublic = null;

    @SerializedName("isfriend")
    public String isfriend = null;

    @SerializedName("isfamily")
    public String isfamily = null;

    @SerializedName("url_s")
    public String url_s = null;

    @SerializedName("height_o")
    public String height_o = null;

    @SerializedName("width_o")
    public  String width_o = null;
}
