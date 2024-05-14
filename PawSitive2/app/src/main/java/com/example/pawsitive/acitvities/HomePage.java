package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<FavouriteJobs> favouriteJobs;
    private List<Job> jobs = new ArrayList<>();
    private HomePageDisplayAdapter homePageDisplayAdapter;

    ImageView homeIcon, favouritesIcon, addIcon, chatIcon, profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initializeImageViews();

        Job jobNew = new Job();
        jobNew.experienceLevel = "Junior"; // Sample data, replace with actual data retrieval
        jobNew.gender = "Male"; // Sample data, replace with actual data retrieval
        jobNew.spokenLanguages = "English, Spanish"; // Sample data, replace with actual data retrieval
        jobNew.price = "20"; // Sample data, replace with actual data retrieval
        jobNew.location = "New York"; // Sample data, replace with actual data retrieval
        jobNew.email = "sample@example.com"; // Sample data, replace with actual data retrieval

        jobs.add(jobNew);


        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Setup search functionality
        fetchUserJobs();




//        // Setup search functionality
//        SearchView searchView = findViewById(R.id.search);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                homePageDisplayAdapter.getFilter().filter(newText);
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
        homeIcon = findViewById(R.id.homeIcon);
        favouritesIcon = findViewById(R.id.heart_icon);
        addIcon = findViewById(R.id.add_icon);
        chatIcon = findViewById(R.id.chat_icon);
        profileIcon = findViewById(R.id.profile_icon);

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


                        jobNew.experienceLevel = document.getString("Experience");
                        jobNew.gender = document.getString("Gender").toUpperCase();
                        jobNew.spokenLanguages = document.getString("Languages").toUpperCase();
                        jobNew.price = document.getString("Price");
                        jobNew.location = document.getString("Location Properties").toUpperCase();
                        System.out.println("bura gelmisem?adsfasfadsf");
                        jobNew.email = document.getId();

                        getUserData(jobNew.email);


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

    private void getUserData(String email) {
        System.out.println("burdayamaaaaa");
        fStore.collection("Users").document(email).get().addOnSuccessListener(documentSnapshot -> {
            System.out.println("bura gir baxim");
            userName = documentSnapshot.getString("Name");
            imageStr = documentSnapshot.getString("Profile Photo");
            fetchUserReviews(email);
        });
    }
    private ArrayList<Review> reviewArrayList;
    private void fetchUserReviews(String email) {
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
                        if (!reviewArrayList.isEmpty()) {

                            userRating = calculateStarAverage(reviewArrayList);
                            System.out.println("bura gircen mi " + userRating);
                            jobNew.userRating = userRating;
                            System.out.println("bakalim " + userRating);
                            jobNew.userName = userName;
                            jobNew.imageStr = imageStr;
                            jobs.add(jobNew);
                            if(!jobs.isEmpty()) {
                                System.out.println("buraya geliyom");
                                homePageDisplayAdapter = new HomePageDisplayAdapter(getApplicationContext(), jobs);
                                recyclerView.setAdapter(homePageDisplayAdapter);

                            }
                        }

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

}
