package com.example.pawsitive.acitvities;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pawsitive.R;
import com.example.pawsitive.classes.Review;
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public static void getReviews(String email, OnSuccessListener<ArrayList<Review>> successListener, OnFailureListener failureListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Reviews")
                .whereEqualTo("CareTaker", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Float> stars = new ArrayList<>();
                    ArrayList<Review> reviewTemp = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Review rev = new Review();
                        rev.star = documentSnapshot.getDouble("Star").floatValue();
                        stars.add(rev.star);
                        rev.caretaker = documentSnapshot.getString("CareTaker");
                        rev.petOwner = documentSnapshot.getString("PetOwner");
                        rev.comment = documentSnapshot.getString("Comment");
                        reviewTemp.add(rev);
                    }
                    User.stars = stars;
                    successListener.onSuccess(reviewTemp);  // Notify listener with reviews
                })
                .addOnFailureListener(e -> {
                    // Handle failures, log error, show error message, etc.
                    Log.e("User", "Error getting reviews: " + e.getMessage());
                });
    }

}