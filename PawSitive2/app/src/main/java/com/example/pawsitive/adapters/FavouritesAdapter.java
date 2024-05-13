package com.example.pawsitive.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.acitvities.FavouritesViewHolder;
import com.example.pawsitive.acitvities.MyViewHolder;
import com.example.pawsitive.classes.FavouriteJobs;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesViewHolder>{

    Context context;
    List<FavouriteJobs> favourites;
    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
