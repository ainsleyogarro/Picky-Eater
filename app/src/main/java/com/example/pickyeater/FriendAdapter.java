package com.example.pickyeater;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private Context context;
    private List<ParseUser> friends;
    private final static String TAG = "FriendAdapter";
    PopupWindow popUp;

    public FriendAdapter(Context context, List<ParseUser> friends){
        this.context = context;
        this.friends = friends;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupRemoveView = inflater.inflate(R.layout.removefriend_popup, null);

        popUp = new PopupWindow(popupRemoveView, 1000, 1000, true);
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

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePic);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra("user", Parcels.wrap(friends.get(getAdapterPosition())));
                    context.startActivity(intent);
                }
            });
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_MOVE && !popUp.isShowing()){
                        startPopUp();


                        //popUp.setContentView(View.inflate(context, R.layout.removefriend_popup, itemView.getRootView().g);
                        //Intent i = new Intent(context, RemoveFriend.class);
                        //context.startActivity(i);
                        //Toast.makeText(context, "Ta", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }

                @RequiresApi(api = Build.VERSION_CODES.M)
                private void startPopUp() {



                    popUp.setFocusable(true);
                    popUp.setOutsideTouchable(false);
                    popUp.showAsDropDown(itemView,0,0);


                    Button btnCancel = popUp.getContentView().findViewById(R.id.btnPopUpFriendCancel);
                    Button btnRemove = popUp.getContentView().findViewById(R.id.btnPopupFriendRemove);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            popUp.dismiss();
                        }
                    });

                    btnRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            friends.remove(getAdapterPosition());
                            ParseUser.getCurrentUser().put("friends", friends);
                            popUp.dismiss();
                        }
                    });
                }
            });




        }

        public void bind(ParseUser parseUser) {

            tvUsername.setText(parseUser.getUsername());
            if (parseUser.getParseFile("Picture") != null){
                Glide.with(context).load(parseUser.getParseFile("Picture").getUrl()).into(ivProfilePicture);
                Log.i(TAG, parseUser.getParseFile("Picture").getUrl());
                ivProfilePicture.setVisibility(View.VISIBLE);
            }
            else {
                ivProfilePicture.setVisibility(View.GONE);
            }
        }
    }


}
