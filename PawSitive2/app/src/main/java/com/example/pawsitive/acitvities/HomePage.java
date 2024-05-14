package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;



public class HomePage extends AppCompatActivity implements UserListener {
    RecyclerView recyclerView;
    String isFiltered;
    private List<FavouriteJobs> favouriteJobs;
    private List<Job> tempJobs;
    private List<Job> jobs = new ArrayList<>();
    private HomePageDisplayAdapter homePageDisplayAdapter;
    ImageView homeIcon, favouritesIcon, addIcon, chatIcon, profileIcon, heartIcon;
    ImageView filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);
        initializeImageViews();

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Setup search functionality
        isFiltered = "notFiltered";
        System.out.println("isFiltered: " + isFiltered);
        Intent intent = getIntent();
        if(intent.getStringExtra("isFiltered") != null)
            isFiltered = intent.getStringExtra("isFiltered");
        System.out.println("isFiltered: " + isFiltered);
        if(!isFiltered.equals("filtered"))
            fetchUserJobs();

        else{
            homePageDisplayAdapter = new HomePageDisplayAdapter(getApplicationContext(), Filtering.getFilteredJobs(), this);
            recyclerView.setAdapter(homePageDisplayAdapter);
        }

        // Setup search functionality
//        SearchView searchView = findViewById(R.id.search);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                homePageDisplayAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
    }

    public List<FavouriteJobs> getFavouriteJobs() {
        return favouriteJobs;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    private void initializeImageViews() {
        System.out.println("biurdadsfa");
        homeIcon = findViewById(R.id.homeIcon);
        favouritesIcon = findViewById(R.id.heart_icon);
        addIcon = findViewById(R.id.add_icon);
        chatIcon = findViewById(R.id.chat_icon);
        profileIcon = findViewById(R.id.profile_icon);
        heartIcon = findViewById(R.id.heart);
        filterButton = findViewById(R.id.filter);

        filterButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, Filtering.class);
            startActivity(intent);
        });


        homeIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, HomePage.class);
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
        jobs = new ArrayList<>();
        fStore.collection("Jobs").get().addOnCompleteListener(task -> {
            System.out.println("burdayim ben - inside onCompleteListener"); // This will run when the query completes
            if (task.isSuccessful() && task.getResult() != null) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    try {
                        System.out.println("burdayim ben - processing document"); // This will run for each document
                        Job jobNew = new Job();
                        jobNew.experienceLevel = document.getString("Experience");
                        jobNew.gender = document.getString("Gender").toUpperCase();
                        jobNew.spokenLanguages = document.getString("Languages").toUpperCase();
                        jobNew.price = document.getString("Price");
                        jobNew.location = document.getString("Location").toUpperCase();
                        System.out.println("bura gelmisem?adsfasfadsf");
                        jobNew.email = document.getId();
                        System.out.println("email budu" + jobNew.email);

                        getUserData(jobNew.email, jobNew);


                    } catch (Exception e) {
                        Log.e("Exception", "Error while parsing job document: " + e.getMessage());
                    }

                }


            } else {
                Log.e("Error", "Error getting job documents: ", task.getException());
            }
        });

        System.out.println("burdayim ben - after get() call"); // This will run immediately after initiating the get() call

    }

    private void getUserData(String email, Job jobNew) {
        System.out.println("burdayamaaaaa");
        fStore.collection("Users").document(email).get().addOnSuccessListener(documentSnapshot -> {
            System.out.println("bura gir baxim");
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
                        System.out.println("bura gir gorum");
                        reviewArrayList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Review review = new Review();
                            review.comment = document.getString("Comment");
                            review.star = document.getDouble("Star").floatValue();
                            reviewArrayList.add(review);
                        }
                        userRating = 0.0f;
                        if(!reviewArrayList.isEmpty()) userRating = calculateStarAverage(reviewArrayList);
                        System.out.println("bura gircen mi " + userRating);
                        jobNew.userRating = userRating;
                        System.out.println("bakalim " + userRating);

                        jobs.add(jobNew);
                        tempJobs = jobs;

                        System.out.println("buraya geliyom");
                        Filtering.setJobsArrayList((ArrayList<Job>) jobs);
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
//        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
        finish();
    }
}