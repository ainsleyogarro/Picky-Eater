package com.example.pickyeater;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register parse models


        // Use for troubleshooting -remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        // Use for mointoring Parse OkHttp traffic



        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // set applicationId, and server server based on the values in the Heroku settings


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("picky-eater")
                .clientKey("quickway")
                .clientBuilder(builder)
                .server("https://picky-eater-ao.herokuapp.com/parse/").build());

        //ParseObject testObject = new ParseObject("TestObject");
        //testObject.put("foo", "bar");
        //testObject.saveInBackground();
    }



}
