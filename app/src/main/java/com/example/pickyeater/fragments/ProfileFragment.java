package com.example.pickyeater.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pickyeater.CaptureActivity;
import com.example.pickyeater.LoginActivity;
import com.example.pickyeater.MainActivity;
import com.example.pickyeater.R;
import com.parse.ParseUser;


public class ProfileFragment extends Fragment {

    private ImageView ivProfileFragPicture;
    private TextView tvFriendCount;
    private TextView tvFoodCount;
    private Button btnSignOut;
    private Button btnCapture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivProfileFragPicture = view.findViewById(R.id.ivProfileFragPic);
        tvFriendCount = view.findViewById(R.id.tvFriendsAmount);
        tvFoodCount = view.findViewById(R.id.tvFoodAmount);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        btnCapture = view.findViewById(R.id.btnCapture);

        tvFoodCount.setText("Restaurants: " + ParseUser.getCurrentUser().getList("restaurants").size());
        tvFriendCount.setText("Friends: " + ParseUser.getCurrentUser().getList("friends").size());
        Glide.with(getContext()).load(ParseUser.getCurrentUser().getParseFile("Picture").getUrl()).into(ivProfileFragPicture);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                goLogoutActivity();
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), CaptureActivity.class);
                startActivity(i);
            }
        });
    }

    private void goLogoutActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }
}