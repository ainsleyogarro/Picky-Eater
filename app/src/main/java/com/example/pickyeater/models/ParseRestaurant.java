package com.example.pickyeater.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Restaurant")
public class ParseRestaurant extends ParseObject {

    public static final String KEY_TITLE = "Title";
    public static final String KEY_ID = "id";
    public static final String KEY_IMAGE_URL = "imageUrl";
    public static final String KEY_ADDRESS = "address";

    public String getKeyTitle(){
        return getString(KEY_TITLE);
    }

    public void setKeyTitle(String title){
        put(KEY_TITLE,title);
    }

    public String getKeyId() {
        return getString(KEY_ID);
    }

    public void setKeyId(String id){
        put(KEY_ID,id);
    }


    public  String getKeyImageUrl() {
        return getString(KEY_IMAGE_URL);
    }

    public void setKeyImageUrl(String address){
        put(KEY_IMAGE_URL,address);
    }

    public  String getKeyAddress() {
        return getString(KEY_ADDRESS);
    }
}
