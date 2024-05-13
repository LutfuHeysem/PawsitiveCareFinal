package com.example.pawsitive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.classes.Review;
import com.example.pawsitive.databinding.ItemContainerChatBinding;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Review> reviewArrayList;

    public ReviewAdapter(Context context, ArrayList<Review> reviewArrayList) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
        System.out.println("ReviewAdapter: " + reviewArrayList.size() + " items");
//        System.out.println(reviewArrayList.get(0).getComment());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_container_review, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewArrayList.get(position);

        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RatingBar ratingBar;
        private TextView textViewComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            textViewComment = itemView.findViewById(R.id.text_review);
        }

        public void bind(Review review) {
            System.out.println("Rating: " + review.getStar() + ", Comment: " + review.getComment());
            ratingBar.setRating(review.getStar());
            textViewComment.setText("Comment: " + review.getComment());
        }

    }
}
