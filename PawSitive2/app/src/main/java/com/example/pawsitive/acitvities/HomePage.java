package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.pawsitive.classes.User;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private List<FavouriteJobs> favouriteJobs;
    private List<Job> jobs;
    private HomePageDisplayAdapter homePageDisplayAdapter;

    ImageView homeIcon, favouritesIcon, addIcon, chatIcon, profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initializeImageViews();

        jobs = new ArrayList<>();

        Job jobNew = new Job();
        jobNew.experienceLevel = "Junior"; // Sample data, replace with actual data retrieval
        jobNew.gender = "Male"; // Sample data, replace with actual data retrieval
        jobNew.spokenLanguages = "English, Spanish"; // Sample data, replace with actual data retrieval
        jobNew.price = "20"; // Sample data, replace with actual data retrieval
        jobNew.location = "New York"; // Sample data, replace with actual data retrieval
        jobNew.email = "sample@example.com"; // Sample data, replace with actual data retrieval

        jobs.add(jobNew);


        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        homePageDisplayAdapter = new HomePageDisplayAdapter(getApplicationContext(), jobs);
        recyclerView.setAdapter(homePageDisplayAdapter);

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
//                homePageDisplayAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
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
}
