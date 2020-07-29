package com.example.pickyeater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;


import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.pickyeater.fragments.FriendsFragment;
import com.example.pickyeater.fragments.HomeFragment;
import com.example.pickyeater.fragments.ProfileFragment;
import com.example.pickyeater.fragments.SearchFragment;
import com.example.pickyeater.models.Restaurant;
import com.github.scribejava.core.model.OAuthRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;


public class MainActivity extends AppCompatActivity {



    private static final String TAG = "MainActivity";


    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().collapseActionView();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(ParseUser.getCurrentUser().getUsername());
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Crimson)));


        //getWindow().setEnterTransition(getResources().getAnimation(R.transition.));

        ParseUser curr = ParseUser.getCurrentUser();

        if(curr.getList("friends") == null){
            curr.put("friends", new ArrayList());
        }

        if(curr.getList("restaurants") == null){
            curr.put("restaurants", new ArrayList());
        }


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.White));

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

                    case R.id.action_profile:
                        fragment = new ProfileFragment();
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