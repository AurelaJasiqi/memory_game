package com.tenton.memorygame.architecture.models;

public class ImageResponse {

    private String imgUrl;
    private int tag;
    private String imgId;
private int source;

    public ImageResponse(String imgUrl, int tag, String imgId) {
        this.imgUrl = imgUrl;
        this.tag = tag;
        this.imgId = imgId;
    }

    public ImageResponse(int tag, String imgId, int source) {
        this.tag = tag;
        this.imgId = imgId;
        this.source = source;
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

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
