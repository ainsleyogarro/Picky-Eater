package com.example.pickyeater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private Context context;
    private List<ParseUser> friends;

    public FriendAdapter(Context context, List<ParseUser> friends){
        this.context = context;
        this.friends = friends;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        return new FriendAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(friends.get(position));

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfilePicture;
        TextView tvUsername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePic);
            tvUsername = itemView.findViewById(R.id.tvUsername);
        }

        public void bind(ParseUser parseUser) {

            tvUsername.setText(parseUser.getUsername());
            if (parseUser.getParseFile("Picture") != null){
                Glide.with(context).load(parseUser.getParseFile("Picture")).into(ivProfilePicture);
                ivProfilePicture.setVisibility(View.VISIBLE);
            }
            else {
                ivProfilePicture.setVisibility(View.GONE);
            }
        }
    }
}
