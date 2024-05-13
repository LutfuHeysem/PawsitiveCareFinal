package com.example.pawsitive.acitvities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.adapters.ReviewAdapter;
import com.example.pawsitive.classes.Review;
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private ArrayList<Review> reviewArrayList;
    private FirebaseFirestore fStore;
//    TextView userNameTextView = findViewById(R.id.usernameTextView);
//    TextView rateTextView = findViewById(R.id.userRateTextView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        // Initialize Firestore

        fStore = FirebaseFirestore.getInstance();
        reviewArrayList = new ArrayList<>();
        System.out.println("burda varim");

        System.out.println("burda varim 3141");
//        progressBar.setVisibility(View.GONE);
        // Initialize RecyclerView and its adapter
        recyclerView = findViewById(R.id.userRecylerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewAdapter = new ReviewAdapter(this, reviewArrayList);
        System.out.println("burda varim 3241");
        recyclerView.setAdapter(reviewAdapter);

        // Fetch reviews from Firestore
        fetchUserReviews(User.getEmail());
        System.out.println("ReviewAdapter: " + reviewArrayList.size() + " items");
    }

    private void fetchUserReviews(String userEmail) {
        System.out.println("Fetching reviews for user: " + userEmail);
        fStore.collection("Reviews")
                .whereEqualTo("CareTaker", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Reviews fetch successful");
                        //reviewArrayList.clear(); // Clear existing data before adding new data
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convert each document to a Review object and add it to the ArrayList
                            Review review = new Review();
                            review.comment = document.getString("Comment");
                            review.star = document.getDouble("Star").floatValue();

                            reviewArrayList.add(review);
                            System.out.println("Added review: " + review.toString());
                            System.out.println("ReviewAdapter: " + reviewArrayList.size() + " items");
                        }
                        System.out.println("ReviewAdapter: " + reviewArrayList.get(0).getComment() + " items");
                        // Notify the adapter that data set has changed
                        reviewAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("ReviewListActivity", "Error getting reviews: ", task.getException());
                        Toast.makeText(ReviewListActivity.this, "Error getting reviews", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
