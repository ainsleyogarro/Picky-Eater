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
import java.util.HashMap;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ImageView ivProfilePic;
    private Button btnFusion;
    private RecyclerView rvFriendRestaurantList;
    private RestaurantsAdapter adapter;
    private List<Restaurant> restaurants;
    private List<ParseRestaurant> parseRestaurants;
    private List<String> restaurantIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ParseUser profile = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        ivProfilePic = findViewById(R.id.ivProfileActivityPic);
        btnFusion = findViewById(R.id.btnFusion);
        rvFriendRestaurantList = findViewById(R.id.rvProfileLists);


        restaurantIDs = new ArrayList<>();
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
                restaurantIDs.add(pRestaurant.getKeyId());
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
        HashMap<String, Restaurant> HashRestaurants = new HashMap<String, Restaurant>();

        ArrayList<Restaurant> fuseRestaurants = new ArrayList();
        
        List<ParseRestaurant> myParseRestaurants = ParseUser.getCurrentUser().getList("restaurants");
        if (myParseRestaurants == null){
            myParseRestaurants = new ArrayList<>();
        }
        for (ParseRestaurant myParseRestaurant:
             myParseRestaurants) {

            HashRestaurants.put(myParseRestaurant.getKeyId(),new Restaurant(myParseRestaurant));

        }


            for (int i = 0; i < restaurants.size(); i++) {
                if (HashRestaurants.containsKey(restaurants.get(i).getId())){
                    fuseRestaurants.add(restaurants.get(i));
                }

            }


        adapter = new RestaurantsAdapter(getApplicationContext(), fuseRestaurants);
        rvFriendRestaurantList.setAdapter(adapter);

    }
}