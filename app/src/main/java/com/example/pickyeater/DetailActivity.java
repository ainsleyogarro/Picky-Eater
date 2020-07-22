package com.example.pickyeater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.pickyeater.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {
    private static final String REST_CONSUMER_SECRET = "SBsaWidRTfoXuVxS04YZE74ExQNX_cmIgNZTz2CH01W_BonLGCy1B0QTCTbkI_6aqvOWk4rG1hEpn2fbPWTHK3JrzleQAkQxcjni5HH48VaGAsga3LZuDkJ4rrMMX3Yx";
    private ImageView ivRestaurant;
    private TextView tvTitle;
    private TextView tvAddress;
    private RatingBar rbRestaurant;
    private TextView tvHours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       ivRestaurant = findViewById(R.id.ivRestaurantDetail);
       tvTitle = findViewById(R.id.tvTitleDetail);
       tvAddress = findViewById(R.id.tvAddressDetail);
       rbRestaurant = findViewById(R.id.rbRestaurant);
       tvHours = findViewById(R.id.tvHours);

        AsyncHttpClient client = new AsyncHttpClient();

        RequestHeaders authorization = new RequestHeaders();
        authorization.put("Authorization", "Bearer " + REST_CONSUMER_SECRET);

        Restaurant currrentRestaurant = Parcels.unwrap(getIntent().getParcelableExtra("restaurant"));

        String apiUrl = "https://api.yelp.com/v3/businesses/" +  currrentRestaurant.getId();
        RequestParams params = new RequestParams();

        client.get(apiUrl, authorization, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject restaurant = json.jsonObject;
                try {
                    tvTitle.setText(restaurant.getString("name"));
                    tvAddress.setText(restaurant.getJSONObject("location").getString("display_address"));
                    rbRestaurant.setNumStars(((int) restaurant.getDouble("rating")));
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

            }
        });
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