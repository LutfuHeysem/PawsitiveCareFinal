package com.example.pawsitive.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.util.Base64;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.classes.Job;
import com.example.pawsitive.classes.Review;
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

//        binding.getRoot().setOnClickListener(
//                v -> userListener.onUserClicked(job.getEmail())
//        );
//        byte[] decodedString = Base64.decode(job.getImage(), Base64.DEFAULT);

//        holder.profileImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

        // holder.heart.setImageResource(job.isFavorite() ? R.drawable.heart_filled : R.drawable.heart_outline);
        if (job.getImage() != null && !job.getImage().isEmpty()) {
            byte[] decodedString = Base64.decode(job.getImage(), Base64.DEFAULT);
            holder.profileImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

        }
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.heart.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                favouriteJobs.add(job);
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
        ImageView profileImage, heart;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            genderAndYear = itemView.findViewById(R.id.genderAndYear);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            profileImage = itemView.findViewById(R.id.profileImage);
            heart = itemView.findViewById(R.id.heart);
        }
    }

}
