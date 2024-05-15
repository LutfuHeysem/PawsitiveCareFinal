package com.example.pawsitive.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.acitvities.FavouritesViewHolder;
import com.example.pawsitive.classes.FavouriteJobs;
import com.example.pawsitive.classes.Job;
import com.example.pawsitive.listeners.UserListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesViewHolder>{

    Context context;
    private List<Job> jobList;
    private final UserListener userListener;



    public FavouritesAdapter(Context context, List<Job> jobs, UserListener userListener) {
        this.context = context;
        this.jobList = jobs;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouritesViewHolder(LayoutInflater.from(context).inflate(R.layout.user_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {
        Job job = jobList.get(position);

        holder.nameView.setText(jobList.get(position).getName());
        holder.genderAgeView.setText(jobList.get(position).getGender());
        holder.locationView.setText(jobList.get(position).getLocation());
        holder.priceView.setText(jobList.get(position).getPrice());
        String profileImage = jobList.get(position).getImage();
        byte[] decodedString = Base64.decode(profileImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.profileView.setImageBitmap(decodedByte);
        holder.heartView.setVisibility(View.GONE);
        holder.clickedHeartView.setVisibility(View.VISIBLE);
        holder.review.setRating(jobList.get(position).getRating());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userListener.onUserClicked(job.getEmail());
            }
        });
        holder.clickedHeartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                holder.heartView.setVisibility(View.VISIBLE);
                holder.clickedHeartView.setVisibility(View.GONE);
                fStore.collection("Users").document(auth.getCurrentUser().getEmail()).
                        collection("FavouriteJobs").document(job.email).delete();

            }
        });
        holder.heartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                holder.heartView.setVisibility(View.GONE);
                holder.clickedHeartView.setVisibility(View.VISIBLE);
                fStore.collection("Users").document(auth.getCurrentUser().getEmail()).
                        collection("FavouriteJobs").document(job.email).set(job);
            }
        });

    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }
}
