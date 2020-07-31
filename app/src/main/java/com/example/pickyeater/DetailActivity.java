package com.example.pickyeater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.pickyeater.models.ParseRestaurant;
import com.example.pickyeater.models.Restaurant;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {
    private ImageView ivRestaurant;
    private TextView tvTitle;
    private TextView tvAddress;
    private RatingBar rbRestaurant;
    private TextView tvHours;
    private Button btnAddRemove;
    private static String TAG = "DetailActivity";
    private List<ParseRestaurant> Userrestaurants;
    private ArrayList<String> restaurantsID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();

       ivRestaurant = findViewById(R.id.ivRestaurantDetail);
       tvTitle = findViewById(R.id.tvTitleDetail);
       tvAddress = findViewById(R.id.tvAddressDetail);
       rbRestaurant = findViewById(R.id.rbRestaurant);
       tvHours = findViewById(R.id.tvHours);
       btnAddRemove = findViewById(R.id.btnAddRemove);

       Userrestaurants = ParseUser.getCurrentUser().getList("restaurants");



        AsyncHttpClient client = new AsyncHttpClient();

        RequestHeaders authorization = new RequestHeaders();
        authorization.put("Authorization", "Bearer " + getResources().getString(R.string.rest_secret));

        final Restaurant currrentRestaurant = Parcels.unwrap(getIntent().getParcelableExtra("restaurant"));

        String apiUrl = "https://api.yelp.com/v3/businesses/" +  currrentRestaurant.getId();
        RequestParams params = new RequestParams();


        btnAddRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRestaurant(currrentRestaurant);
            }
        });


        // Gets website and binds data to view
        client.get(apiUrl, authorization, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject restaurant = json.jsonObject;
                try {

                    tvTitle.setText(restaurant.getString("name"));
                    JSONArray address = restaurant.getJSONObject("location").getJSONArray("display_address");
                    for (int i = 0; i <address.length() ; i++) {
                        tvAddress.setText(tvAddress.getText() + address.getString(i) + "\n");
                    }

                    rbRestaurant.setNumStars( (int) Math.round(restaurant.getDouble("rating")));
                    rbRestaurant.getProgressDrawable().setTint(getResources().getColor(R.color.Gold));
                    Glide.with(getApplicationContext()).load(restaurant.optString("image_url")).into(ivRestaurant);
                    JSONArray hours = restaurant.getJSONArray("hours").getJSONObject(0).getJSONArray("open");
                    for (int i = 0; i < hours.length() ; i++) {
                        JSONObject day = hours.getJSONObject(i);
                        tvHours.setText(tvHours.getText() + HoursConverter(day.getString("start"), day.getString("end"), day.getInt("day")) + "\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG, "Json request failed", throwable);
            }
        });
    }

    // Sees if Restaurant exists in backend if not creates Restaurant then adds to users list
    private void AddRestaurant(final Restaurant restaurant){

        // First make sure user has Restaurant list
        if (Userrestaurants == null){
            Userrestaurants = new ArrayList<>();
        }

        ParseQuery<ParseRestaurant> query = ParseQuery.getQuery(ParseRestaurant.class);
        query.include(ParseRestaurant.KEY_ID);
        query.whereEqualTo(ParseRestaurant.KEY_ID, restaurant.getId());
        query.findInBackground(new FindCallback<ParseRestaurant>() {
            @Override
            public void done(List<ParseRestaurant> restaurants, ParseException e) {
                // If restaurant is already created in Parse
                if (restaurants.size() != 0 && restaurants.get(0).getKeyHours() == null){

                    // Restaurant add function
                    if (RemoveFrom(restaurants.get(0)) == false){
                        Userrestaurants.add(restaurants.get(0));
                        ParseUser.getCurrentUser().put("restaurants",Userrestaurants);
                        ParseUser.getCurrentUser().saveInBackground();
                        Log.i(TAG, ParseUser.getCurrentUser().getList("restaurants").toString());
                    }
                    // If restaurant removed
                    else {
                        ParseUser.getCurrentUser().put("restaurants",Userrestaurants);
                        ParseUser.getCurrentUser().saveInBackground();
                        Log.i(TAG, "Remove " + ParseUser.getCurrentUser().getList("restaurants").toString());
                    }

                    return;
                }

                // If restaurant is in parse
                else{
                    final ParseRestaurant newRestaurant = new ParseRestaurant();
                    newRestaurant.setKeyId(restaurant.getId());
                    newRestaurant.setKeyImageUrl(restaurant.getImageUrl());
                    newRestaurant.setKeyTitle(restaurant.getTitle());
                    newRestaurant.setKeyAddress(restaurant.getAddress());
                    newRestaurant.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null){
                                Log.e(TAG, "Error while saving", e);
                            }
                            else {
                                Log.i(TAG, "Successful saving");
                                Userrestaurants.add(newRestaurant);
                                ParseUser.getCurrentUser().put("restaurants",Userrestaurants);
                                Log.i(TAG, ParseUser.getCurrentUser().getList("restaurants").toString());
                                ParseUser.getCurrentUser().saveInBackground();
                            }
                        }
                    });
                }
            }
        });

        // Go back to home screen
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }

    // Used to see if Restaurant exists and if so removes
    private boolean RemoveFrom(ParseRestaurant restaurant){
        for (int i = 0; i < Userrestaurants.size(); i++) {

            if (Userrestaurants.get(i).getKeyId().equals(restaurant.getKeyId())) {
                Userrestaurants.remove(i);

                return true;
            }
        }
        return false;
    }

    // Takes time range and day and returns desired formatted output
    public String HoursConverter(String start, String end, int day){
        String open = MilitarytoNormal(start);
        String close = MilitarytoNormal(end);
        if (day == 0){
            return "Mon " + open + "-" + close;
        }

        else if (day == 1){
            return "Tues " + open + "-" + close;
        }

        else if (day == 2){
            return "Wed " + open + "-" + close;
        }
        else if (day == 3){
            return "Thurs " + open + "-" + close;
        }

        else if (day == 4){
            return "Tues " + open + "-" + close;
        }

        else if (day == 5){
            return "Fri " + open + "-" + close;
        }
        else {
            return "Sat " + open + "-" + close;
        }


    }

    // Converts military time to normal
    public String MilitarytoNormal(String time){
        String newtime = "";
        String first2digits = time.substring(0,2);
        String last2digits = time.substring(2,4);
        if (Integer.valueOf(first2digits) < 12){
            if (first2digits.equals("00")){
                newtime = "12" + ":" + last2digits + "AM";
            }
            else if (Integer.valueOf(first2digits) <  10){
                newtime = first2digits.substring(1) + ":" + last2digits + "AM";
            }
            else{
                newtime = first2digits + ":" + last2digits + "AM";
            }
        }
        else{
            if (Integer.valueOf(first2digits) == 12){
                newtime = first2digits + ":" + last2digits + "PM";
            }

            else{
                newtime = (Integer.valueOf(first2digits) - 12) + ":" + last2digits + "PM";
            }
        }
        return newtime;
    }
}