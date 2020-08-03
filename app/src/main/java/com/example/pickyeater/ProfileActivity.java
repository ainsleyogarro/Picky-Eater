package com.example.pickyeater;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.RadialGradient;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.pickyeater.models.ParseRestaurant;
import com.example.pickyeater.models.Restaurant;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.utils.URIBuilder;
import okhttp3.Headers;

public class ProfileActivity extends AppCompatActivity {

    private ImageView ivProfilePic;
    private RecyclerView rvFriendRestaurantList;
    private RestaurantsAdapter adapter;
    private List<Restaurant> restaurants;
    private List<ParseRestaurant> parseRestaurants;
    private List<String> restaurantIDs;
    private RadioGroup rgListOptions;
    private boolean open = false;
    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        ParseUser profile = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        actionBar.setTitle(profile.getUsername());
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Crimson)));

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

    // Meant to find list of all restaurants that are fused and open

    public void Party(View view) throws ParseException {
        HashMap<String, Restaurant> HashRestaurants = new HashMap<String, Restaurant>();

        final ArrayList<Restaurant> fuseRestaurants = new ArrayList();


        List<ParseRestaurant> myParseRestaurants = ParseUser.getCurrentUser().getList("restaurants");
        if (myParseRestaurants == null) {
            myParseRestaurants = new ArrayList<>();
        }
        for (ParseRestaurant myParseRestaurant :
                myParseRestaurants) {

            HashRestaurants.put(myParseRestaurant.getKeyId(), new Restaurant(myParseRestaurant));

        }


        for (int i = 0; i < restaurants.size(); i++) {
            if (HashRestaurants.containsKey(restaurants.get(i).getId())) {
                // calculates if restaurant is open
                isOpen(restaurants.get(i));
                if (open) {
                    fuseRestaurants.add(restaurants.get(i));
                }

            }
        }
            //adapter = new RestaurantsAdapter(getApplicationContext(), fuseRestaurants);
            //rvFriendRestaurantList.setAdapter(adapter);
            adapter.clear();
            adapter.addAll(fuseRestaurants);


    }
    public void isOpen(final Restaurant restaurantInput){
        //Log.i(TAG, restaurantInput.getTitle());



        AsyncHttpClient client = new AsyncHttpClient();

        RequestHeaders authorization = new RequestHeaders();
        authorization.put("Authorization", "Bearer " + getResources().getString(R.string.rest_secret));
        String apiUrl = "https://api.yelp.com/v3/businesses/" +  restaurantInput.getId();
        RequestParams params = new RequestParams();
        client.get(apiUrl, authorization, params, new JsonHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject restaurant = json.jsonObject;
                try {
                    JSONArray hours = restaurant.getJSONArray("hours").getJSONObject(0).getJSONArray("open");
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;

                    for (int j = 0; j <hours.length() ; j++) {
                        if (hours.getJSONObject(j).getInt("day") == day){
                            String todaystart= hours.getJSONObject(j).getString("start");
                            String todayend = hours.getJSONObject(j).getString("end");
                            Boolean overnight = hours.getJSONObject(j).getBoolean("is_overnight");
                            LocalTime start = LocalTime.of(Integer.valueOf(todaystart.substring(0,2)),Integer.valueOf(todaystart.substring(2)));
                            LocalTime end = LocalTime.of(Integer.valueOf(todayend.substring(0,2)), Integer.valueOf(todayend.substring(2)));
                            open = inBetween(start, end, overnight);
                            Log.i(TAG, "aa" + open);

                        }

                    }


                }

                catch (JSONException e) {
                       Log.e(TAG, "Failure calculating hours. " , e );
                    }
            }
                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.i(TAG, "Client failure");
                }
            });

        Log.i(TAG, "ha" + open);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean inBetween(LocalTime start, LocalTime end, boolean overnight ){

        if (!overnight){
            //Log.i(TAG,(start.compareTo(LocalTime.now()) )+ " " + (LocalTime.now().compareTo(end))) ;
            return ((start.compareTo(LocalTime.now())) == -1 && (LocalTime.now().compareTo(end) != 1));
        }
        else{
            if (LocalTime.now().compareTo(LocalTime.MIDNIGHT) == -1){
                return ((start.compareTo(LocalTime.now())) == -1);
            }
            else{
                return ((start.compareTo(LocalTime.now())) == -1 && (LocalTime.now().compareTo(end) != -1));

            }
        }
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