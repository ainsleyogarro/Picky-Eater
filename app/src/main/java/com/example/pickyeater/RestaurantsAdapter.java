package com.example.pickyeater;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pickyeater.models.Restaurant;

import org.parceler.Parcels;

import java.util.List;

import kotlin.jvm.internal.MagicApiIntrinsics;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    Context context;
    List<Restaurant> restaurants;

    public RestaurantsAdapter(Context context, List<Restaurant> restaurants){
        this.context = context;
        this.restaurants = restaurants;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(restaurants.get(position));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public void clear(){

        restaurants.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Restaurant> list) {
        restaurants.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivRestaurant;
        TextView tvTitle;
        TextView tvAddress;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            ivRestaurant = itemView.findViewById(R.id.ivRestaurant);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    //intent.putExtra("id", restaurants.get(getAdapterPosition()).getId());
                    intent.putExtra("restaurant", Parcels.wrap(restaurants.get(getAdapterPosition())));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intent);

                }
            });
        }

        public void bind(Restaurant restaurant) {

            tvTitle.setText(restaurant.getTitle());
            tvAddress.setText(restaurant.getAddress());
            if (!restaurant.getImageUrl().equals("")){
                Glide.with(context).load(restaurant.getImageUrl()).into(ivRestaurant);
                ivRestaurant.setVisibility(View.VISIBLE);
            }
            else {
                ivRestaurant.setVisibility(View.GONE);
            }
        }
    }
}
