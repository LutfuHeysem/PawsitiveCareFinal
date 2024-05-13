package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.adapters.ReviewAdapter;
import com.example.pawsitive.classes.Review;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {

    private ImageView backButton;
    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private ArrayList<Review> reviewArrayList;
    private FirebaseFirestore fStore;
    private TextView userNameTextView;
    private TextView rateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        String userNameEmail = getIntent().getStringExtra("User Email");

        backButton = findViewById(R.id.imageBack);
        userNameTextView = findViewById(R.id.usernameTextView);
        rateTextView = findViewById(R.id.userRateTextView);
        recyclerView = findViewById(R.id.userRecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewArrayList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this, reviewArrayList);
        recyclerView.setAdapter(reviewAdapter);

        fStore = FirebaseFirestore.getInstance();

        fetchUserName(userNameEmail);
        fetchUserReviews(userNameEmail);
    }

    private void fetchUserName(String userEmail) {
        fStore.collection("Users")
                .whereEqualTo("Email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        String userName = document.getString("Name");
                        userNameTextView.setText(userName);
                    }
                });
    }

    private void fetchUserReviews(String userEmail) {
        fStore.collection("Reviews")
                .whereEqualTo("CareTaker", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Review review = new Review();
                            review.comment = document.getString("Comment");
                            review.star = document.getDouble("Star").floatValue();
                            reviewArrayList.add(review);
                        }
                        if (!reviewArrayList.isEmpty()) {
                            reviewAdapter.notifyDataSetChanged();
                            rateTextView.setText(calculateStarAverage() + " / 5");
                        }
                    } else {
                        Log.d("ReviewListActivity", "Error getting reviews: ", task.getException());
                        Toast.makeText(ReviewListActivity.this, "Error getting reviews", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public float calculateStarAverage()
    {
        if(reviewArrayList.isEmpty())
        {
            return 0;
        }
        float sumOfStars = 0;
        for(Review value : reviewArrayList)
        {
            sumOfStars += value.getStar();
        }
        float sumOfStarsTimesTwo = 2 * sumOfStars;
        float averageStarsTimesTwo = sumOfStarsTimesTwo / reviewArrayList.size();
        float averageStarsTimesTwoRounded = Math.round(averageStarsTimesTwo);
        return  averageStarsTimesTwoRounded/2;
    }


    private void backPressed(){
        Intent intent = new Intent(ReviewListActivity.this, ProfilePage1.class);
        startActivity(intent);
    }
    private void setListener() {
        backButton.setOnClickListener(v -> backPressed());
    }

}
