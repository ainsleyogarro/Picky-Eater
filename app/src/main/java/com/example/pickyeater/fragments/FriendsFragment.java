package com.example.pickyeater.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pickyeater.FriendAdapter;
import com.example.pickyeater.R;
import com.example.pickyeater.RestaurantsAdapter;
import com.example.pickyeater.models.Restaurant;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class FriendsFragment extends Fragment {
    private static final String TAG = "FriendsFragment";
    private List<ParseUser> friends;
    private ArrayList<String> usernames;
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


        friends = ParseUser.getCurrentUser().getList("friends");
        if (friends == null){
            friends = new ArrayList<>();
            ParseUser.getCurrentUser().put("friends",friends);
            ParseUser.getCurrentUser().saveInBackground();
        }
        adapter = new FriendAdapter(getContext(), friends);

        rvFriend.setAdapter(adapter);
        rvFriend.setLayoutManager(new LinearLayoutManager(getContext()));

        usernames = new ArrayList<>();

        // Creates lists of usernames of friends
        for (ParseUser user:friends){
            try {
                usernames.add(user.fetchIfNeeded().getUsername());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etFriends.getText().toString().isEmpty() ){
                    try {
                        ParseUser friend = QueryUser(etFriends.getText().toString());
                        addUser(friend);
                        Log.i(TAG,friends.size() + "");
                        etFriends.setText("");
                        //friends.add(friend);
                        //usernames.add(friend.getUsername());
                    } catch (ParseException e) {
                        Log.e(TAG, "Issue with adding friend", e);
                        Toast.makeText(getContext(), "User cannot be found", Toast.LENGTH_SHORT);
                    }


                }
            }
        });



    }

    public ParseUser QueryUser(final String username) throws ParseException {
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("username", username);
        return query.getFirst();
    }

    // Adds user to friends list
    public void addUser(ParseUser user){
      if (usernames.contains(user.getUsername()))  {
          Toast.makeText(getContext(), "User is already a friend", Toast.LENGTH_SHORT).show();
          return;
      }
      usernames.add(user.getUsername());
      friends.add(user);
      ParseUser.getCurrentUser().put("friends",friends);
      ParseUser.getCurrentUser().saveInBackground();


    }
}