package com.example.pickyeater.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONException;
import org.json.JSONObject;

//@ParseClassName("Restaurant")
public class Restaurant  {
    private String title;
    private String imageUrl;
    private String address;

    public Restaurant(JSONObject jsonObject) throws JSONException{
        title = jsonObject.getString("name");
        imageUrl = jsonObject.getString("image_url");
        address = jsonObject.getJSONObject("location").getString("address1");

    }

    public Restaurant(){

    }

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
