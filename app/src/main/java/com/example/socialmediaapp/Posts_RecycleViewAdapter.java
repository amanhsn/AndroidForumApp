package com.example.socialmediaapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Posts_RecycleViewAdapter extends RecyclerView.Adapter<Posts_RecycleViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Post> posts;

    public Posts_RecycleViewAdapter(Context context, ArrayList<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public Posts_RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerviewrow, parent, false);
        return new Posts_RecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Posts_RecycleViewAdapter.MyViewHolder holder, int position) {
        holder.userName.setText("Poster: " + posts.get(position).getUsername());
        holder.title.setText("Subject: " + posts.get(position).getTitle());
        holder.date.setText(posts.get(position).getDate());
        holder.content.setText(posts.get(position).getContent());
        holder.tags.setText("#" + posts.get(position).getTags());
        holder.profilePic.setImageResource(posts.get(position).getPicture());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView profilePic;
        TextView userName, title, date, content, tags;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.ProfilePic_IV);
            userName = itemView.findViewById(R.id.Username_TV);
            title = itemView.findViewById(R.id.Title_TV);
            date = itemView.findViewById(R.id.Date_TV);
            content = itemView.findViewById(R.id.Content_TV);
            tags = itemView.findViewById(R.id.Tags_TV);
        }
    }
}
