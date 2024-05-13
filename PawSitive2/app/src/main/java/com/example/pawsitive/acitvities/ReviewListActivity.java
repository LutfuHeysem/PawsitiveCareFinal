package com.example.pawsitive.acitvities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private ArrayList<Review> reviewArrayList;
    private FirebaseFirestore fStore;
    String userNameEmail = User.getEmail();
    TextView userNameTextView;
//    TextView rateTextView = findViewById(R.id.userRateTextView);

    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        userNameTextView = findViewById(R.id.usernameTextView);

        fetchUserName(userNameEmail);
        System.out.println("username i printlieyr " + userName);

        //System.out.println(userNameTextView.getText());
        // Initialize Firestore

        fStore = FirebaseFirestore.getInstance();
        reviewArrayList = new ArrayList<>();
        System.out.println("burda varim");
        //userNameTextView.setText("Okay");
        System.out.println("burda varim 3141");
//        progressBar.setVisibility(View.GONE);
        // Initialize RecyclerView and its adapter
        recyclerView = findViewById(R.id.userRecylerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewAdapter = new ReviewAdapter(this, reviewArrayList);
        System.out.println("burda varim 3241");
        recyclerView.setAdapter(reviewAdapter);

        // Fetch reviews from Firestore
        fetchUserReviews(userNameEmail);
        System.out.println("ReviewAdapter: " + reviewArrayList.size() + " items");
    }
    private void fetchUserName(String userEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TaskCompletionSource<DocumentSnapshot> source = new TaskCompletionSource<>();
        Task<DocumentSnapshot> task = source.getTask();
        db.collection("Users")
                .whereEqualTo("Email", userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                userName = document.getString("Name");
                                System.out.println("BUraya gelmisem mi? " + userName);
                                userNameTextView.setText(userName);
                            }
                        } else {
                            source.setException(task.getException());
                        }
                    }
                });

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