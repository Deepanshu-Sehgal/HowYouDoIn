package com.dstechbuzz.howyoudoin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dstechbuzz.howyoudoin.Model.User;
import com.dstechbuzz.howyoudoin.R;

import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;


    public userAdapter(Context mContext, List<User> mUsers){
        this.mUsers =mUsers;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public userAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_items,parent,false);
        return new userAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull userAdapter.ViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.username.setText(user.getUsername());
        if (user.getImageUrl().equals("default")){
            holder.profile_image.setImageResource(R.drawable.profileimg);
        } else {
            Glide.with(mContext).load(user.getImageUrl()).into(holder.profile_image);
        }

    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

}
