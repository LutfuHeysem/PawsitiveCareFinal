package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.adapters.ActiveJobsAdapter;
import com.example.pawsitive.adapters.MyPetsAdapter;
import com.example.pawsitive.classes.ActiveJobModel;
import com.example.pawsitive.classes.AddDialog;
import com.example.pawsitive.classes.Review;
import com.example.pawsitive.classes.User;
import com.example.pawsitive.listeners.ActiveJobListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfilePage2 extends AppCompatActivity implements ActiveJobListener {
    ImageView profileImageView;
    RecyclerView recyclerView;

    ActiveJobsAdapter activeJobsAdapter;
    private ArrayList<Review> reviewArrayList;

    public ArrayList<ActiveJobModel> activeJobs = new ArrayList<>();

    TextView locationView, nameView, priceInfo, locationInfo, experienceInfo, languagesInfo;
    String userEmail, profileImageStr, name, location, gender;
    Bitmap profileImageBitmap;
    Button backButtonProfilePage, reviewsButton, myAnimalsButton, chatStartButton;
    ImageView homeButton, favoritesButton, addButton, chatButton, profileButton;
    RatingBar rateBar;
    private User owner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page2);

        backButtonProfilePage = (Button) findViewById(R.id.backButtonProfilePage2);
        reviewsButton = findViewById(R.id.reviewsButtonProfilePage2);
        chatStartButton = findViewById(R.id.startChatButton);
        homeButton = findViewById(R.id.homeIcon);
        favoritesButton = findViewById(R.id.heart_icon);
        addButton = findViewById(R.id.add_icon);
        chatButton = findViewById(R.id.chat_icon);
        profileButton = findViewById(R.id.profile_icon);

        profileImageView = findViewById(R.id.profileImageProfilePage2);

        Intent intent = getIntent();
        userEmail= intent.getStringExtra("User Email");

        priceInfo = findViewById(R.id.priceInfoProfilePage2);
        locationInfo = findViewById(R.id.locationPropertiesInfoProfilePage2);
        experienceInfo = findViewById(R.id.experienceInfoProfilePage2);
        languagesInfo = findViewById(R.id.languagesInfoProfilePage2);
        locationView = findViewById(R.id.locationTextProfilePage2);
        nameView = findViewById(R.id.profileUserNameProfilePage2);

        rateBar = findViewById(R.id.ratingBar2ProfilePage2);
        changeEditable(false);

        fetchUserReviews(userEmail);
        fetchActiveJobs(userEmail);

        recyclerView = findViewById(R.id.ActiveJobsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();


            DocumentReference userData = db.collection("Users").document(userEmail);

            userData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    name = documentSnapshot.getString("Name");
                    gender = documentSnapshot.getString("Gender");

                    profileImageStr = documentSnapshot.getString("Profile Photo");
                    byte[] decodedString = Base64.decode(profileImageStr, Base64.DEFAULT);
                    profileImageBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                    location = documentSnapshot.getString("Location");

                    locationView.setText(location);
                    String nameAndGender = name + " (" + gender + ")";
                    nameView.setText(nameAndGender);

                    profileImageView.setImageBitmap(profileImageBitmap);
                    profileImageView.setVisibility(View.VISIBLE);

                    try{
                        DocumentReference careTakerJobData = db.collection("Jobs").document(userEmail);

                        careTakerJobData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                String price = documentSnapshot.getString("Price");
                                String locationProperties = documentSnapshot.getString("Location Properties");
                                String experienceLevel = documentSnapshot.getString("Experience");
                                String spokenLanguages = documentSnapshot.getString("Languages");
                                //date available

                                priceInfo.setText(price);
                                locationInfo.setText(locationProperties);
                                experienceInfo.setText(experienceLevel);
                                languagesInfo.setText(spokenLanguages);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Failed to fetch job data from Firebase: " + e.getMessage());
                            }
                        });
                    }catch(Exception e){
                        priceInfo.setText("");
                        locationInfo.setText("");
                        experienceInfo.setText("");
                        languagesInfo.setText("");
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Failed to fetch user data from Firebase: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


        backButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });


        String email = intent.getStringExtra("email");

        this.owner = new User(email);

        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewListActivity.class);
                intent.putExtra("User Email", userEmail);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavouritesPage.class);
                startActivity(intent);
            }
        });
        chatStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserToChat(userEmail);
                Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog dialog = new AddDialog();
                dialog.show(getSupportFragmentManager(), "AddDialog");
            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
                startActivity(intent);
            }
        });
    }

    private void changeEditable(boolean trueOrFalse){
        profileImageView.setFocusable(trueOrFalse);
        profileImageView.setClickable(trueOrFalse);

        priceInfo.setFocusable(trueOrFalse);
        priceInfo.setClickable(trueOrFalse);

        locationInfo.setFocusable(trueOrFalse);
        locationInfo.setClickable(trueOrFalse);

        experienceInfo.setFocusable(trueOrFalse);
        experienceInfo.setClickable(trueOrFalse);

        languagesInfo.setFocusable(trueOrFalse);
        languagesInfo.setClickable(trueOrFalse);

        locationView.setFocusable(trueOrFalse);
        locationView.setClickable(trueOrFalse);

        nameView.setFocusable(trueOrFalse);
        nameView.setClickable(trueOrFalse);
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
                            rateBar.setRating(calculateStarAverage());
                        }
                    } else {
                        Log.d("ReviewListActivity", "Error getting reviews: ", task.getException());
                    }
                });
    }


    private void fetchActiveJobs(String email){
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        fStore.collection("Users").document(email).collection("AcceptedOffers")
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){

                            String startDate = document.getString("startDate");
                            String endDate = document.getString("endDate");
                            System.out.println(startDate);
                            activeJobs.add(new ActiveJobModel(startDate, endDate));
                        }
                        if(!activeJobs.isEmpty()){
                            activeJobsAdapter = new ActiveJobsAdapter(getApplicationContext(), activeJobs, this, email);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(activeJobsAdapter);
                        }
                    }
                });

    }


    @Override
    public void onChatClicked() {
        addUserToChat(userEmail);
        Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
        startActivity(intent);
    }


    // THIS METHOD WILL BE CALLED WHEN THE USER CLICKS THE START CHAT BUTTON IN PROFILE PAGE

    public void addUserToChat(String email){ // parameter will be the email of the user clicked
        try {
            User.addUserToChatPage(email);
            FirebaseAuth auth = FirebaseAuth.getInstance();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            HashMap<String, String> userData = new HashMap<>();
            userData.put("email", email);
            db.collection("Users").document(auth.getCurrentUser().getEmail())
                    .collection("UsersForChat").document(email).set(userData);

            HashMap<String, String> userDataReceiver = new HashMap<>();
            userDataReceiver.put("email", auth.getCurrentUser().getEmail());
            db.collection("Users").document(email)
                    .collection("UsersForChat").document(auth.getCurrentUser().getEmail()).set(userDataReceiver);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}