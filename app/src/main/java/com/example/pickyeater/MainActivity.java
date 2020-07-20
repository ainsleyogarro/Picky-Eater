package com.example.pickyeater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.pickyeater.fragments.FriendsFragment;
import com.example.pickyeater.fragments.HomeFragment;
import com.example.pickyeater.fragments.SearchFragment;
import com.example.pickyeater.models.Restaurant;
import com.github.scribejava.core.model.OAuthRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;


public class MainActivity extends AppCompatActivity {


    private static final String API_KEY = "SBsaWidRTfoXuVxS04YZE74ExQNX_cmIgNZTz2CH01W_BonLGCy1B0QTCTbkI_6aqvOWk4rG1hEpn2fbPWTHK3JrzleQAkQxcjni5HH48VaGAsga3LZuDkJ4rrMMX3Yx";
    private static final String API_CLIENT_ID = "Tpc4as6QiW4h0QLRd8zHvA";
    private static final String Search_URL = "https://api.yelp.com/v3/businesses/search";
    private static final String TAG = "MainActivity";


    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_friends:
                        fragment = new FriendsFragment();
                        break;

                    case R.id.action_search:
                        fragment = new SearchFragment();
                        break;
                     default:
                        fragment = new HomeFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_home);



    }


}