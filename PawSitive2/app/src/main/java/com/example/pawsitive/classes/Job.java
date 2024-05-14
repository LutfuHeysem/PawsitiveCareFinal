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

    public float getUserRating(){
        return userRating;
    }

    public List<Job> getJobList() {
        return jobArrayList;
    }
}
