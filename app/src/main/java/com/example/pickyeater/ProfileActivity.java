package com.example.pickyeater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pickyeater.models.ParseRestaurant;
import com.example.pickyeater.models.Restaurant;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ImageView ivProfilePic;
    private Button btnFusion;
    private RecyclerView rvFriendRestaurantList;
    private RestaurantsAdapter adapter;
    private List<Restaurant> restaurants;
    private List<ParseRestaurant> parseRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ParseUser profile = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        ivProfilePic = findViewById(R.id.ivProfileActivityPic);
        btnFusion = findViewById(R.id.btnFusion);
        rvFriendRestaurantList = findViewById(R.id.rvProfileLists);

        Glide.with(getApplicationContext()).load(profile.getParseFile("Picture").getUrl()).into(ivProfilePic);

        restaurants = new ArrayList<>();
        parseRestaurants = profile.getList("restaurants");
        if (parseRestaurants == null){
            parseRestaurants = new ArrayList<>();
        }
        for (ParseRestaurant pRestaurant:
             parseRestaurants) {
            try {
                restaurants.add(new Restaurant(pRestaurant));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        adapter = new RestaurantsAdapter(getApplicationContext(), restaurants);

        rvFriendRestaurantList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvFriendRestaurantList.setAdapter(adapter);
        
        btnFusion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FuseLists();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void FuseLists() throws ParseException {
        ArrayList<Restaurant> fuseRestaurants = new ArrayList();
        ArrayList<Restaurant> myRestaurants  = new ArrayList();
        List<ParseRestaurant> myParseRestaurants = ParseUser.getCurrentUser().getList("restaurants");
        if (myParseRestaurants == null){
            myParseRestaurants = new ArrayList<>();
        }
        for (ParseRestaurant myParseRestaurant:
             myParseRestaurants) {
            myRestaurants.add(new Restaurant(myParseRestaurant));
        }

        // Used if myRestaurants is smaller to save time
        if (myRestaurants.size() < restaurants.size()){
            for (int i = 0; i < myRestaurants.size(); i++) {
                    if (restaurants.contains(myRestaurants.get(i))){
                        fuseRestaurants.add(myRestaurants.get(i));
                    }

            }

        }

        // Used if Restaurants is smaller to save time
        else{
            for (int i = 0; i < restaurants.size(); i++) {
                if (myRestaurants.contains(restaurants.get(i))){
                    fuseRestaurants.add(restaurants.get(i));
                }

            }
        }

        adapter = new RestaurantsAdapter(getApplicationContext(), fuseRestaurants);
        rvFriendRestaurantList.setAdapter(adapter);

    }
}