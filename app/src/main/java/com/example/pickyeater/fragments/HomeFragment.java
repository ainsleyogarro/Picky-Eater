package com.example.pickyeater.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pickyeater.R;
import com.example.pickyeater.RestaurantsAdapter;
import com.example.pickyeater.models.ParseRestaurant;
import com.example.pickyeater.models.Restaurant;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;


public class HomeFragment extends Fragment {

    private RecyclerView rvRestaraunts;
    private List<Restaurant> restaurants;
    private List<ParseRestaurant> parseRestaurants;
    private RestaurantsAdapter adapter;
    private static String TAG = "HomeFragment";

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

        restaurants = new ArrayList<>();

        parseRestaurants = ParseUser.getCurrentUser().getList("Restaurants");
        if (parseRestaurants == null){
            parseRestaurants = new ArrayList<>();
        }

        for (ParseRestaurant pRestaurant: parseRestaurants
             ) {
            try {
                restaurants.add(new Restaurant(pRestaurant));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Log.i(TAG, restaurants.toString());

        adapter = new RestaurantsAdapter(getApplicationContext() , restaurants);
        rvRestaraunts = view.findViewById(R.id.rvRestaurants);
        rvRestaraunts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvRestaraunts.setAdapter(adapter);
    }
}