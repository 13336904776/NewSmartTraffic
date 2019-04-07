package com.example.mrzhang.newsmarttraffic.bean;

public class VideoBean {
    String path;
    int imgId;
    String name;

    public VideoBean() {
    }

    public VideoBean(String path, int imgId, String name) {
        this.path = path;
        this.imgId = imgId;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
