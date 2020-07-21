package com.example.pickyeater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private ImageView ivRestaurant;
    private TextView tvTitle;
    private TextView tvAddress;
    private RatingBar rbRestaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Retrieve restaurant ID from click
        getIntent().getStringExtra("id");

    }
}