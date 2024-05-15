package com.example.pawsitive.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.acitvities.ProfilePage2;
import com.example.pawsitive.classes.Job;
import com.example.pawsitive.databinding.ItemContainerChatBinding;
import com.example.pawsitive.listeners.UserListener;

import java.util.List;

public class HomePageDisplayAdapter extends RecyclerView.Adapter<HomePageDisplayAdapter.JobViewHolder> {

    private Context context;
    private List<Job> jobList, favouriteJobs;
    private final UserListener userListener;

    ItemContainerChatBinding binding;

    public HomePageDisplayAdapter(Context context, List<Job> jobList, UserListener userListener) {
        this.context = context;
        this.userListener = userListener;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_view, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.name.setText(job.getName());
        holder.genderAndYear.setText(job.getGender() + ", " + job.getExperienceLevel());
        holder.location.setText(job.getLocation());
        holder.ratingBar.setRating(job.getRating());
        holder.price.setText(job.getPrice() + " $");
        holder.heart.setVisibility(View.VISIBLE);
        holder.heartClicked.setVisibility(View.GONE);

        if (job.getImage() != null && !job.getImage().isEmpty()) {
            byte[] decodedString = Base64.decode(job.getImage(), Base64.DEFAULT);
            holder.profileImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.heartClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userListener.onUserClicked(job.getEmail());
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class JobViewHolder extends RecyclerView.ViewHolder {
        TextView name, genderAndYear, location, price;
        RatingBar ratingBar;
        ImageView profileImage;
        Button heart, heartClicked;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            genderAndYear = itemView.findViewById(R.id.genderAndYear);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            profileImage = itemView.findViewById(R.id.profileImage);
            heart = itemView.findViewById(R.id.heart);
            heartClicked = itemView.findViewById(R.id.heartClicked);
        }
    }
}
