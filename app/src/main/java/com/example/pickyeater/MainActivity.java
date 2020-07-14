package com.example.pickyeater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    YelpClient yelpClient;
    private static final String API_KEY = "SBsaWidRTfoXuVxS04YZE74ExQNX_cmIgNZTz2CH01W_BonLGCy1B0QTCTbkI_6aqvOWk4rG1hEpn2fbPWTHK3JrzleQAkQxcjni5HH48VaGAsga3LZuDkJ4rrMMX3Yx";
    private static final String API_CLIENT_ID = "Tpc4as6QiW4h0QLRd8zHvA";
    private static final String Search_URL = "https://api.yelp.com/v3/businesses/search";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //yelpClient = new YelpClient(getApplicationContext());


    }


}