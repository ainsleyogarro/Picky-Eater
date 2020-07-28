package com.example.pickyeater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.RadialGradient;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

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
    private RecyclerView rvFriendRestaurantList;
    private RestaurantsAdapter adapter;
    private List<Restaurant> restaurants;
    private List<ParseRestaurant> parseRestaurants;
    private List<String> restaurantIDs;
    private RadioGroup rgListOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ParseUser profile = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        ivProfilePic = findViewById(R.id.ivProfileActivityPic);
        rvFriendRestaurantList = findViewById(R.id.rvProfileLists);
        rgListOptions = findViewById(R.id.rgListOptions);

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
        


    }



    public void FuseLists(View view) throws ParseException {
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


    public void originalLists(View view) {

        adapter.clear();
        adapter.addAll(restaurants);

    }

    // Sets view to restaurant list that include all restaurants between user and friend

    public void CombineLists(View view) throws ParseException {

        HashMap<String, Restaurant> HashRestaurants = new HashMap<String, Restaurant>();

        ArrayList<Restaurant> fuseRestaurants = new ArrayList();
        ArrayList<Restaurant> myRestaurants = new ArrayList<>();

        List<ParseRestaurant> myParseRestaurants = ParseUser.getCurrentUser().getList("restaurants");
        if (myParseRestaurants == null){
            myParseRestaurants = new ArrayList<>();
        }
        for (ParseRestaurant myParseRestaurant:
                myParseRestaurants) {
            myRestaurants.add(new Restaurant(myParseRestaurant));

        }
        fuseRestaurants.addAll(restaurants);


        for (int i = 0; i < restaurants.size(); i++) {
           HashRestaurants.put(restaurants.get(i).getId(), restaurants.get(i));

        }

        for (int i = 0; i < myRestaurants.size(); i++) {
            if (!HashRestaurants.containsKey(myRestaurants.get(i).getId())){
                fuseRestaurants.add(myRestaurants.get(i));
            }
        }


        adapter.clear();
        adapter.addAll(fuseRestaurants);
    }

    // Exclusive List that show restaurant list that the friend has but you do not

    public void ExclusiveList(View view) throws ParseException {
        HashMap<String, Restaurant> HashRestaurants = new HashMap<String, Restaurant>();

        ArrayList<Restaurant> fuseRestaurants = new ArrayList();
        ArrayList<Restaurant> myRestaurants = new ArrayList<>();

        List<ParseRestaurant> myParseRestaurants = ParseUser.getCurrentUser().getList("restaurants");
        if (myParseRestaurants == null){
            myParseRestaurants = new ArrayList<>();
        }
        for (ParseRestaurant myParseRestaurant:
                myParseRestaurants) {
            HashRestaurants.put(myParseRestaurant.getKeyId(),new Restaurant(myParseRestaurant));

        }


        for (int i = 0; i < restaurants.size(); i++) {
            if (!HashRestaurants.containsKey(restaurants.get(i).getId())){
                fuseRestaurants.add(restaurants.get(i));
            }
        }


        adapter.clear();
        adapter.addAll(fuseRestaurants);


    }
}