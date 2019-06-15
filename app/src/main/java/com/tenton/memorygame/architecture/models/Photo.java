package com.tenton.memorygame.architecture.models;

import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("id")
    String id = null;

    @SerializedName("owner")
    String owner = null;

    @SerializedName("secret")
    String secret = null;

    @SerializedName("server")
    String server = null;

    @SerializedName("farm")
    String farm = null;

    @SerializedName("title")
    String title = null;

    @SerializedName("ispublic")
    String ispublic = null;

    @SerializedName("isfriend")
    String isfriend = null;

    @SerializedName("isfamily")
    String isfamily = null;

    @SerializedName("url_o")
    String url_o = null;

    @SerializedName("height_o")
    String height_o = null;

    @SerializedName("width_o")
    String width_o = null;
}
