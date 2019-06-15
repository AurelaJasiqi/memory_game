package com.tenton.memorygame.architecture.models;

public class ImageResponse {

    private String imgUrl;
    private int tag;
    private String imgId;

    public ImageResponse(String imgUrl, int tag, String imgId) {
        this.imgUrl = imgUrl;
        this.tag = tag;
        this.imgId = imgId;
}

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
}
