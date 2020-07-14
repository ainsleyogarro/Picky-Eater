package com.example.pickyeater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.pickyeater.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    YelpClient yelpClient;
    private static final String API_KEY = "SBsaWidRTfoXuVxS04YZE74ExQNX_cmIgNZTz2CH01W_BonLGCy1B0QTCTbkI_6aqvOWk4rG1hEpn2fbPWTHK3JrzleQAkQxcjni5HH48VaGAsga3LZuDkJ4rrMMX3Yx";
    private static final String API_CLIENT_ID = "Tpc4as6QiW4h0QLRd8zHvA";
    private static final String Search_URL = "https://api.yelp.com/v3/businesses/search";
    private static final String TAG = "MainActivity";

    private RecyclerView rvRestaraunts;
    private List<Restaurant> restaurants;
    private RestaurantsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //yelpClient = new YelpClient(getApplicationContext());
        Restaurant Granny = new Restaurant();
        Granny.setAddress("9712 Groffs Mill Dr");
        Granny.setImageUrl("https://s3-media0.fl.yelpcdn.com/bphoto/0Dq-rfP6vjCwsAx9ZHrXfw/o.jpg");
        Granny.setTitle("Granny's Restaurant");

        Restaurant Stanford = new Restaurant();
        Stanford.setAddress("10997 Owings Mills Blvd");
        Stanford.setImageUrl("https://s3-media0.fl.yelpcdn.com/bphoto/5SK7Ec7ml0zLhdk7HraLCQ/348s.jpg");
        Stanford.setTitle("Stanford Kitchen");

        Restaurant ShakingCrab = new Restaurant();
        ShakingCrab.setAddress("11316 Reisterstown Rd");
        ShakingCrab.setTitle("Shaking Crab");
        ShakingCrab.setImageUrl("https://s3-media0.fl.yelpcdn.com/bphoto/jdGElTrjRevv0h1hxusnUQ/348s.jpg");

        restaurants = new ArrayList<>();
        restaurants.add(Granny);
        restaurants.add(Stanford);
        restaurants.add(ShakingCrab);

        adapter = new RestaurantsAdapter(getApplicationContext() , restaurants);
        rvRestaraunts = findViewById(R.id.rvRestaurants);
        rvRestaraunts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvRestaraunts.setAdapter(adapter);


    }


}