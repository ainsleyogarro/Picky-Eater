package com.example.pickyeater.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

//@ParseClassName("Restaurant")
public class Restaurant  {
    private String title;
    private String imageUrl;
    private String address;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
