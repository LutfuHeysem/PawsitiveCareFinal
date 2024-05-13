package com.example.pawsitive.acitvities;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;

public class FavouritesViewHolder extends RecyclerView.ViewHolder {

    public ImageView profileView, heartView;
    public TextView nameView, genderAgeView, locationView, priceView;
    public RatingBar review;
    public FavouritesViewHolder(@NonNull View itemView) {
        super(itemView);
        profileView = itemView.findViewById(R.id.profileImage);
        heartView = itemView.findViewById(R.id.filledHeart);
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

    public TextView getFavsNameView() {
        return nameView;
    }

    public TextView getFavsGenderAgeView() {
        return genderAgeView;
    }

    public TextView getFavsLocationView() {
        return locationView;
    }

    public TextView getFavsPriceView() {
        return priceView;
    }

    public RatingBar getFavsReview() {
        return review;
    }
}
