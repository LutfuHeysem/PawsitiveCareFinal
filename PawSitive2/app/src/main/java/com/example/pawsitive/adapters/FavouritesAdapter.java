package com.example.pawsitive.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.acitvities.FavouritesViewHolder;
import com.example.pawsitive.classes.FavouriteJobs;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesViewHolder>{

    Context context;
    List<FavouriteJobs> favourites;

    public FavouritesAdapter(Context context, List<FavouriteJobs> favourites) {
        this.context = context;
        this.favourites = favourites;
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouritesViewHolder(LayoutInflater.from(context).inflate(R.layout.favourites_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {
        holder.nameView.setText(favourites.get(position).getName());
        holder.genderAgeView.setText(favourites.get(position).getGender());
        holder.locationView.setText(favourites.get(position).getLocation());
        holder.priceView.setText(favourites.get(position).getPrice());
        String profileImage = favourites.get(position).getImage();
        byte[] decodedString = Base64.decode(profileImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.profileView.setImageBitmap(decodedByte);
        holder.heartView.setImageResource(R.drawable.heart);
        holder.review.setRating(favourites.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }
}
