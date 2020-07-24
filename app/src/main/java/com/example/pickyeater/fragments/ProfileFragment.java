package com.example.pickyeater.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pickyeater.R;
import com.parse.ParseUser;


public class ProfileFragment extends Fragment {

    private ImageView ivProfileFragPicture;
    private TextView tvFriendCount;
    private TextView tvFoodCount;


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

        tvFoodCount.setText("Restaurants: " + ParseUser.getCurrentUser().getList("restaurants").size());
        tvFriendCount.setText("Friends: " + ParseUser.getCurrentUser().getList("friends").size());
        Glide.with(getContext()).load(ParseUser.getCurrentUser().getParseFile("Picture").getUrl()).into(ivProfileFragPicture);
    }
}