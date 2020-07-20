package com.example.pickyeater.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.pickyeater.FriendAdapter;
import com.example.pickyeater.R;
import com.example.pickyeater.RestaurantsAdapter;
import com.example.pickyeater.models.Restaurant;

import java.util.ArrayList;


public class FriendsFragment extends Fragment {
    private static final String TAG = "FriendsFragment";
    private ArrayList<Restaurant> friends;
    private RecyclerView rvFriend;
    private EditText etFriends;
    private Button btnAdd;
    private FriendAdapter adapter;


    public FriendsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etFriends = view.findViewById(R.id.etFriends);
        btnAdd =  view.findViewById(R.id.btnFriend);
        rvFriend = view.findViewById(R.id.rvFriends);


    }
}