package com.example.pickyeater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.ParseUser;

import org.parceler.Parcels;

public class ProfileActivity extends AppCompatActivity {

    private ImageView ivProfilePic;
    private Button btnFusion;
    private RecyclerView rvFriendRestaurantList;
    private RestaurantsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ParseUser profile = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        ivProfilePic = findViewById(R.id.ivProfileActivityPic);
        btnFusion = findViewById(R.id.btnFusion);
        rvFriendRestaurantList = findViewById(R.id.rvProfileLists);


    }
}