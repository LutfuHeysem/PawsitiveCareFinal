package com.example.pawsitive.classes;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
    private FirebaseFirestore fStore;
    private ArrayList<Job> jobArrayList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fStore = FirebaseFirestore.getInstance();
        jobArrayList = new ArrayList<>();

        fetchUserJobs();
    }

    private void fetchUserJobs() {
        fStore.collection("Jobs")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Job jobNew = new Job();
                            jobNew.location = document.getString("Location").toUpperCase();
                            jobNew.gender = document.getString("Gender").toUpperCase();
                            jobNew.spokenLanguages = document.getString("Languages").toUpperCase();
                            jobNew.price = document.getString("Price");
                            jobNew.email = document.getString("Email");
                            jobNew.experienceLevel = document.getString("Experience");

                            jobArrayList.add(jobNew);
                        }
                    } else {
                        Log.d("ReviewListActivity", "Error getting job features: ", task.getException());
                    }
                });
    }
    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<Job> getJobArrayList(){return jobArrayList;}

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

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDates() {
        return dates;
    }
}
