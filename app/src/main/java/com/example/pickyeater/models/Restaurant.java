package com.example.pickyeater.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.List;

//@ParseClassName("Restaurant")
@Parcel
public class Restaurant  {
    private String title;
    private String imageUrl;
    private String address;
    private String id;
    private List<String> hours;

    public Restaurant(JSONObject jsonObject) throws JSONException{
        title = jsonObject.getString("name");
        imageUrl = jsonObject.getString("image_url");
        address = jsonObject.getJSONObject("location").getString("address1");
        id = jsonObject.getString("id");
        hours = (List<String>) jsonObject.getJSONArray("hours").getJSONObject(0).getJSONArray("open");
    }

    public Restaurant(ParseRestaurant restaurant) throws ParseException {
        title = restaurant.fetchIfNeeded().getString("Title");
        imageUrl = restaurant.fetchIfNeeded().getString("imageUrl");
        id = restaurant.fetchIfNeeded().getString("Restid");
        address = restaurant.fetchIfNeeded().getString("address");
        hours = restaurant.fetchIfNeeded().getList("Hours");
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getHours() {
        return hours;
    }

    public void setHours(List<String> hours) {
        this.hours = hours;
    }
}
