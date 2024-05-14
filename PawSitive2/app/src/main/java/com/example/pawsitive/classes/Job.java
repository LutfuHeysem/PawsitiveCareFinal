package com.example.pawsitive.classes;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.View;

import com.example.pawsitive.acitvities.Filtering;
import com.example.pawsitive.acitvities.ProfilePage1;
import com.example.pawsitive.adapters.UsersAdapter;
import com.example.pawsitive.utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pawsitive.R;
import com.example.pawsitive.acitvities.ReviewListActivity;
import com.example.pawsitive.adapters.ReviewAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
public class Job extends AppCompatActivity {
    private String price, location, locationProperties, experienceLevel, spokenLanguages, dates, gender, email;
    float rating;
    String userName, imageStr;
    float userRating;
    private FirebaseFirestore fStore;
    private List<Job> jobArrayList;
    private ArrayList<Review> reviewArrayList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public Job (){
        fStore = FirebaseFirestore.getInstance();
        fetchUserJobs();
    }

    private void fetchUserJobs() {
        jobArrayList = new ArrayList<>();
        fStore.collection("Jobs")
                .get()
                .addOnCompleteListener(task -> {
                    System.out.println("taskkkkkkkkk");
                    if (task.isSuccessful()) {
                        System.out.println("success");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                Job jobNew = new Job();
                                jobNew.experienceLevel = document.getString("Experience");
                                jobNew.gender = document.getString("Gender").toUpperCase();
                                jobNew.spokenLanguages = document.getString("Languages").toUpperCase();
                                jobNew.price = document.getString("Price");
                                System.out.println("databse de olanlar");
                                jobNew.location = document.getString("Location").toUpperCase();
                                jobNew.email = document.getString("Email");
                                System.out.println("olmayanlar");

                                System.out.println("getuserdata öncesi");
                                getUserData();
                                System.out.println("add öncesi");
                                jobArrayList.add(jobNew);
                                System.out.println("AL e eklendi!");
                                for (int i = 0; i < jobArrayList.size(); i++) {
                                    System.out.println(jobArrayList.get(i));
                                }
                            } catch (Exception e) {
                                Log.e("Exception", "Error while parsing job document: " + e.getMessage());
                            }
                        }
                    } else {
                        Log.e("Error", "Error getting job documents: ", task.getException());
                    }
                });

    }

    private void getUserData(){
        fStore = FirebaseFirestore.getInstance();

        fStore.collection("Users").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userName = documentSnapshot.getString("Name");
                imageStr = documentSnapshot.getString("Profile Photo");
                fetchUserReviews(email);
            }
        });
    }
    public float calculateStarAverage(){
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
        return averageStarsTimesTwoRounded / 2;
    }

    private void fetchUserReviews(String email) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        reviewArrayList = new ArrayList<>();
        fStore.collection("Reviews")
                .whereEqualTo("CareTaker", email)
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
                            userRating = calculateStarAverage();
                        }
                    } else {
                        Log.d("ReviewListActivity", "Error getting reviews: ", task.getException());
                    }
                });
    }


    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }
    public String getEmail(){return email;}

    public String getLocationProperties() {
        return locationProperties;
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

    public void setPrice(String price) {
        this.price = price;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLocationProperties(String locationProperties) {
        this.locationProperties = locationProperties;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public void setSpokenLanguages(String spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }
    public List<Job> getJobList(){
        return jobArrayList;
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
}
