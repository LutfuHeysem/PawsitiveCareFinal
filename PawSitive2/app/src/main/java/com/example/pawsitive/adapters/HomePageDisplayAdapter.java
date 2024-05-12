package com.example.pawsitive.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import com.example.pawsitive.R;
import com.example.pawsitive.acitvities.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

import com.example.pawsitive.classes.User;

public class HomePageDisplayAdapter extends RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List<User> users;

    public HomePageDisplayAdapter(List<User> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.user_view,parent,false));
    }

    //public ImageView profileView, heartView;
    //    public TextView nameView, genderAgeView, locationView, priceView;
    //    public RatingBar review;
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(users.get(position).getName());
        holder.genderAgeView.setText(users.get(position).getGender() + " " + users.get(position).getAge());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
