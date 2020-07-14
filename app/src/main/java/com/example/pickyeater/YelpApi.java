package com.example.pickyeater;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuth1RequestToken;

public class YelpApi extends DefaultApi20 {


    private static final String API_ACCESS_URL = "https://api.yelp.com/oauth2/token";
    private static final String API_ACCESS_BASE_URL = "https://api.yelp.com/oauth2/";

    public static YelpApi instance() {
        return YelpApi.InstanceHolder.INSTANCE;
    }



    @Override
    public String getAccessTokenEndpoint() {
        return API_ACCESS_URL;
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return API_ACCESS_BASE_URL;
    }



        private static class InstanceHolder {
            private static final YelpApi INSTANCE = new YelpApi();
        }






    }


