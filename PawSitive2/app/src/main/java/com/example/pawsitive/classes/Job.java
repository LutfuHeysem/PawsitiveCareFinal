package com.example.pawsitive.classes;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Job {
    public String price, location, locationProperties, experienceLevel, spokenLanguages, dates, gender, email;
    public float rating;
    public String userName, imageStr;
    public float userRating;
    private FirebaseFirestore fStore;
    private List<Job> jobArrayList;
    private List<Review> reviewArrayList;

    public Job() {
        fStore = FirebaseFirestore.getInstance();
        fetchUserJobs();
    }

    private void fetchUserJobs() {
        jobArrayList = new ArrayList<>();
        fStore.collection("Jobs")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                Job jobNew = new Job();
                                jobNew.experienceLevel = document.getString("Experience");
                                jobNew.gender = document.getString("Gender").toUpperCase();
                                jobNew.spokenLanguages = document.getString("Languages").toUpperCase();
                                jobNew.price = document.getString("Price");
                                jobNew.location = document.getString("Location").toUpperCase();
                                jobNew.email = document.getString("Email");

                                jobArrayList.add(jobNew);
                                getUserData(jobNew.email);
                            } catch (Exception e) {
                                Log.e("Exception", "Error while parsing job document: " + e.getMessage());
                            }
                        }
                    } else {
                        Log.e("Error", "Error getting job documents: ", task.getException());
                    }
                });
    }

    private void getUserData(String email) {
        fStore.collection("Users").document(email).get().addOnSuccessListener(documentSnapshot -> {
            userName = documentSnapshot.getString("Name");
            imageStr = documentSnapshot.getString("Profile Photo");
            fetchUserReviews(email);
        });
    }

    private void fetchUserReviews(String email) {
        fStore.collection("Reviews")
                .whereEqualTo("CareTaker", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        reviewArrayList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Review review = new Review();
                            review.comment = document.getString("Comment");
                            review.star = document.getDouble("Star").floatValue();
                            reviewArrayList.add(review);
                        }
                        if (!reviewArrayList.isEmpty()) {
                            userRating = calculateStarAverage();
                        }
                    } else {
                        Log.d("ReviewListActivity", "Error getting reviews: ", task.getException());
                    }
                });
    }

    public float calculateStarAverage() {
        if (reviewArrayList.isEmpty()) {
            return 0;
        }
        float sumOfStars = 0;
        for (Review value : reviewArrayList) {
            sumOfStars += value.getStar();
        }
        return Math.round((sumOfStars / reviewArrayList.size()) * 2) / 2.0f;
    }

    // Getters and setters
    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public String getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getGender() {
        return gender;
    }

    public String getImage() {
        return imageStr;
    }

    public String getName() {
        return userName;
    }

    public float getRating() {
        return rating;
    }

    public List<Job> getJobList() {
        return jobArrayList;
    }
}
