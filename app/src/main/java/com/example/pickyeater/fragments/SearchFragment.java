package com.example.pickyeater.fragments;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.pickyeater.R;
import com.example.pickyeater.RestaurantsAdapter;
import com.example.pickyeater.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Headers;




public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    private ArrayList<Restaurant> restaurants;
    private RecyclerView rvSearch;
    private EditText etSearch;
    private Button btnSearch;
    private RestaurantsAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        restaurants = new ArrayList<>();
        adapter = new RestaurantsAdapter(getContext(), restaurants);
        rvSearch = view.findViewById(R.id.rvRestaurantsSearch);

        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearch.setAdapter(adapter);

        etSearch = view.findViewById(R.id.etSearch);
        btnSearch = view.findViewById(R.id.btnSearch);


        // Searches on Enter Key
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    if ((i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_DPAD_CENTER) && !etSearch.getText().toString().isEmpty()){
                        fillView(etSearch.getText().toString());
                        etSearch.setText("");
                        ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
                        return true;
                    }
                }

                return false;
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etSearch.getText().toString().isEmpty()){
                    fillView(etSearch.getText().toString());
                    etSearch.setText("");
                    ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

    }

    // Fills recycler view with restaurants
    public void fillView(String location){
            AsyncHttpClient client = new AsyncHttpClient();


            RequestHeaders authorization = new RequestHeaders();
            authorization.put("Authorization", "Bearer " + getResources().getString(R.string.rest_secret));

            adapter.clear();
            String apiUrl = "https://api.yelp.com/v3/businesses/search";
            RequestParams params = new RequestParams();

            params.put("location", location);
            params.put("categories", "restaurant");
            final JSONArray results = new JSONArray();


            // Client call to yelp api
            client.get(apiUrl,authorization, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    JSONObject jsonObject = json.jsonObject;
                    try{
                        JSONArray results = jsonObject.getJSONArray("businesses");

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject rest = results.getJSONObject(i);
                            Restaurant restaurant = new Restaurant(rest);
                            restaurants.add(restaurant);
                        }
                        adapter.notifyDataSetChanged();



                    } catch (JSONException e) {
                        Log.e(TAG, "Hit json exception",e);
                    }
                }
                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.i(TAG, throwable.toString() + response);

                }
            });
        }
}






