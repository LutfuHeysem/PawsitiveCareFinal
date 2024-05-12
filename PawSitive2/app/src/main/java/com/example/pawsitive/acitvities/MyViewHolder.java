package com.example.pawsitive.acitvities;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public ImageView profileView, heartView;
    public TextView nameView, genderAgeView, locationView, priceView;
    public RatingBar review;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        profileView = itemView.findViewById(R.id.profileImage);
        heartView = itemView.findViewById(R.id.heart);
        nameView = itemView.findViewById(R.id.name);
        genderAgeView = itemView.findViewById(R.id.genderAndYear);
        locationView = itemView.findViewById(R.id.location);
        priceView = itemView.findViewById(R.id.price);
        review = itemView.findViewById(R.id.rating_bar);
    }

    public ImageView getProfileView() {
        return profileView;
    }

    public ImageView getHeartView() {
        return heartView;
    }

    public TextView getNameView() {
        return nameView;
    }

    public TextView getGenderAgeView() {
        return genderAgeView;
    }

    public TextView getLocationView() {
        return locationView;
    }

    public TextView getPriceView() {
        return priceView;
    }

    public RatingBar getReview() {
        return review;
    }
}
