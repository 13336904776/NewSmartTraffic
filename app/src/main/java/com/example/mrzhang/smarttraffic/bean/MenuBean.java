package com.example.mrzhang.smarttraffic.bean;

/**
 *
 */
public class MenuBean {
    int iv;
    String title;

    public MenuBean() {
    }

    public MenuBean(int iv, String title) {
        this.iv = iv;
        this.title = title;
    }

    public int getIv() {
        return iv;
    }

    public void setIv(int iv) {
        this.iv = iv;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
