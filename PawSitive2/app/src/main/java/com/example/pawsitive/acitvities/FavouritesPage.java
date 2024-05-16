package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.adapters.FavouritesAdapter;
import com.example.pawsitive.adapters.HomePageDisplayAdapter;
import com.example.pawsitive.classes.AddDialog;
import com.example.pawsitive.classes.Job;
import com.example.pawsitive.classes.Review;
import com.example.pawsitive.classes.User;
import com.example.pawsitive.classes.UserForChat;
import com.example.pawsitive.listeners.UserListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavouritesPage extends AppCompatActivity implements UserListener {
    RecyclerView recyclerView;
    private FavouritesAdapter favouritesAdapter;
    private List<Job> jobs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourites_page);

        initializeImageViews();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.favsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchUserJobs();

        favouritesAdapter = new FavouritesAdapter(getApplicationContext(), jobs, this);
        recyclerView.setAdapter(favouritesAdapter);

    }

    private FirebaseFirestore fStore;
    public String userName, imageStr;
    public float userRating;
    public Job jobNew = new Job();
    public void fetchUserJobs() {

        fStore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        jobs = new ArrayList<>();
        fStore.collection("Users").document(auth.getCurrentUser().getEmail()).
                collection("FavouriteJobs").
                get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    try {
                        fStore.collection("Jobs").
                                document(document.getString("email")).
                                get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Job jobNew = new Job();
                                        jobNew.experienceLevel = documentSnapshot.getString("Experience");
                                        jobNew.gender = documentSnapshot.getString("Gender").toUpperCase();
                                        jobNew.spokenLanguages = documentSnapshot.getString("Languages").toUpperCase();
                                        jobNew.price = documentSnapshot.getString("Price");
                                        jobNew.location = documentSnapshot.getString("Location").toUpperCase();
                                        jobNew.location = documentSnapshot.getString("Location Properties").toUpperCase();
                                        jobNew.email = documentSnapshot.getId();
                                        getUserData(jobNew.email, jobNew);
                                    }
                                });
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
                        favouritesAdapter = new FavouritesAdapter(getApplicationContext(), jobs, this);
                        recyclerView.setAdapter(favouritesAdapter);

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

    private void initializeImageViews(){
        ImageView homeIcon = findViewById(R.id.homeIcon);
        ImageView favouritesIcon = findViewById(R.id.heart_icon);
        ImageView addIcon = findViewById(R.id.add_icon);
        ImageView chatIcon = findViewById(R.id.chat_icon);
        ImageView profileIcon = findViewById(R.id.profile_icon);

        homeIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //handle click on home image
                //navigate to home page
                Intent intent = new Intent(FavouritesPage.this, HomePage.class);
                startActivity(intent);
            }
        });
        
        addIcon.setOnClickListener(v -> {
            AddDialog dialog = new AddDialog();
            dialog.show(getSupportFragmentManager(), "AddDialog");
        });

        chatIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //handle click on chat image
                //navigate to chat page
                Intent intent = new Intent(FavouritesPage.this, UsersActivity.class);
                startActivity(intent);
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //handle click on profile image
                //navigate to profile page
                Intent intent = new Intent(FavouritesPage.this, ProfilePage1.class);
                intent.putExtra("email", User.getEmail());
                startActivity(intent);
            }
        });
    };

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