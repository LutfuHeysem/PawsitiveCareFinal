package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.adapters.HomePageDisplayAdapter;
import com.example.pawsitive.classes.AddDialog;
import com.example.pawsitive.classes.FavouriteJobs;
import com.example.pawsitive.classes.Job;

import com.example.pawsitive.classes.Review;
import com.example.pawsitive.classes.User;
import com.example.pawsitive.classes.UserForChat;
import com.example.pawsitive.listeners.UserListener;
import com.example.pawsitive.utilities.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;



public class HomePage extends AppCompatActivity implements UserListener {
    RecyclerView recyclerView;
    String isFiltered, location;
    private List<FavouriteJobs> favouriteJobs;
    private List<Job> jobs = new ArrayList<>();
    private HomePageDisplayAdapter homePageDisplayAdapter;
    ImageView homeIcon, favouritesIcon, addIcon, chatIcon, profileIcon, heartIcon;
    ImageView filterButton;
    private EditText search;
    private Button searchSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent().getStringExtra("email") != null){
            Intent chatIntent = new Intent(HomePage.this, UsersActivity.class);
            startActivity(chatIntent);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);
        initializeImageViews();

        search = findViewById(R.id.locSearch);
        searchSave = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup search functionality
        isFiltered = "notFiltered";
        Intent intent = getIntent();
        if(intent.getStringExtra("isFiltered") != null)
            isFiltered = intent.getStringExtra("isFiltered");
        if(!isFiltered.equals("filtered"))
            fetchUserJobs();
        else{
            homePageDisplayAdapter = new HomePageDisplayAdapter(getApplicationContext(), Filtering.getFilteredJobs(), this);
            recyclerView.setAdapter(homePageDisplayAdapter);
        }
        searchSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = search.getText().toString();
                filterJobsByLocation(location);
            }
        });
    }

    private void filterJobsByLocation(String location) {
        List<Job> filteredJobs = new ArrayList<>();
        for (Job job : jobs) {
            if (job.getLocation().equalsIgnoreCase(location))
                filteredJobs.add(job);
        }

        homePageDisplayAdapter = new HomePageDisplayAdapter(getApplicationContext(), filteredJobs, this);
        recyclerView.setAdapter(homePageDisplayAdapter);
    }

    public List<FavouriteJobs> getFavouriteJobs() {
        return favouriteJobs;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    private void initializeImageViews() {
        homeIcon = findViewById(R.id.homeIcon);
        favouritesIcon = findViewById(R.id.heart_icon);
        addIcon = findViewById(R.id.add_icon);
        chatIcon = findViewById(R.id.chat_icon);
        profileIcon = findViewById(R.id.profile_icon);
        filterButton = findViewById(R.id.filter);

        filterButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, Filtering.class);
            startActivity(intent);
        });

        favouritesIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, FavouritesPage.class);
            startActivity(intent);
        });

        addIcon.setOnClickListener(v -> {
            AddDialog dialog = new AddDialog();
            dialog.show(getSupportFragmentManager(), "AddDialog");
        });

        chatIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, UsersActivity.class);
            startActivity(intent);
        });

        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, ProfilePage1.class);
            intent.putExtra("email", User.getEmail());
            startActivity(intent);
        });
    }



    private FirebaseFirestore fStore;
    public String userName, imageStr;
    public float userRating;
    public Job jobNew = new Job();
    public void fetchUserJobs() {

        fStore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        jobs = new ArrayList<>();
        fStore.collection("Jobs").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    try {
                        if(document.getString("Email").equals(auth.getCurrentUser().getEmail()))
                            continue;
                        Job jobNew = new Job();
                        jobNew.experienceLevel = document.getString("Experience");
                        jobNew.gender = document.getString("Gender").toUpperCase();
                        jobNew.spokenLanguages = document.getString("Languages").toUpperCase();
                        jobNew.price = document.getString("Price");
                        jobNew.location = document.getString("Location").toUpperCase();
                        jobNew.email = document.getId();
                        getUserData(jobNew.email, jobNew);
                    } catch (Exception e) {
                        Log.e("Exception", "Error while parsing job document: " + e.getMessage());
                    }
                }
            } else {
                Log.e("Error", "Error getting job documents: ", task.getException());
            }
        });


    }

    private void getUserData(String email, Job jobNew) {
        fStore.collection("Users").document(email).get().addOnSuccessListener(documentSnapshot -> {
            jobNew.userName = documentSnapshot.getString("Name");
            jobNew.imageStr = documentSnapshot.getString("Profile Photo");
            fetchUserReviews(email, jobNew);
        });
    }
    private ArrayList<Review> reviewArrayList;
    private void fetchUserReviews(String email, Job jobNew) {
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
                        userRating = 0.0f;
                        if(!reviewArrayList.isEmpty()) userRating = calculateStarAverage(reviewArrayList);
                        jobNew.userRating = userRating;
                        userRating = 0.0f;
                        if(!reviewArrayList.isEmpty()) userRating = calculateStarAverage(reviewArrayList);
                        jobNew.userRating = userRating;

                        jobs.add(jobNew);
                        Filtering.setJobsArrayList((ArrayList<Job>) jobs);
                        homePageDisplayAdapter = new HomePageDisplayAdapter(getApplicationContext(), jobs, this);
                        recyclerView.setAdapter(homePageDisplayAdapter);
                        homePageDisplayAdapter = new HomePageDisplayAdapter(getApplicationContext(), jobs, this);
                        recyclerView.setAdapter(homePageDisplayAdapter);

                    } else {
                        Log.d("ReviewListActivity", "Error getting reviews: ", task.getException());
                    }
                });
    }

    public float calculateStarAverage(ArrayList<Review> reviewArrayList){
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

    @Override
    public void onUserClicked(UserForChat user) {

    }

    @Override
    public void onUserClicked(String user) {
        Intent intent = new Intent(getApplicationContext(), ProfilePage2.class);
        intent.putExtra("User Email", user);
        startActivity(intent);
    }
}