package com.example.pickyeater.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pickyeater.R;
import com.example.pickyeater.RestaurantsAdapter;
import com.example.pickyeater.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;


public class HomeFragment extends Fragment {

    private RecyclerView rvRestaraunts;
    private List<Restaurant> restaurants;
    private RestaurantsAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        rvRestaraunts = view.findViewById(R.id.rvRestaurants);
        rvRestaraunts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvRestaraunts.setAdapter(adapter);
    }
}