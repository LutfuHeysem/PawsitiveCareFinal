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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pawsitive.R;
import com.example.pawsitive.classes.AddDialog;
import com.example.pawsitive.classes.Review;
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class ProfilePage1 extends AppCompatActivity {
    ImageView profileImageView;

    private ArrayList<Review> reviewArrayList;

    TextView locationView, nameView, priceInfo, locationInfo, experienceInfo, languagesInfo;
    String userEmail, profileImageStr, name, location, gender;
    Bitmap profileImageBitmap;
    Button backButtonProfilePage, editButtonProfilePage, reviewsButton, myAnimalsButton;
    ImageView homeButton, favoritesButton, addButton, chatButton, profileButton;
    RatingBar rateBar;
    public final int GET_FROM_GALLERY = 3;
    private User owner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userEmail = User.getEmail();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backButtonProfilePage = (Button) findViewById(R.id.backButtonProfilePage);
        editButtonProfilePage = findViewById(R.id.editButtonProfile);
        reviewsButton = findViewById(R.id.reviewsButton);

        homeButton = findViewById(R.id.homeIcon);
        favoritesButton = findViewById(R.id.heart_icon);
        addButton = findViewById(R.id.add_icon);
        chatButton = findViewById(R.id.chat_icon);
        profileButton = findViewById(R.id.profile_icon);
        myAnimalsButton = findViewById(R.id.myAnimalButton);

        profileImageView = findViewById(R.id.profileImage);

        priceInfo = findViewById(R.id.priceInfo);
        locationInfo = findViewById(R.id.locationPropertiesInfo);
        experienceInfo = findViewById(R.id.experienceInfo);
        languagesInfo = findViewById(R.id.languagesInfo);
        locationView = findViewById(R.id.locationText);
        nameView = findViewById(R.id.profileUserName);

        rateBar = findViewById(R.id.ratingBar2);
        changeEditable(false);

        fetchUserReviews(userEmail);

        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();

            userEmail = auth.getCurrentUser().getEmail();
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

        myAnimalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPets.class);
                startActivity(intent);
            }
        });



    Intent intent = getIntent();

    String email = intent.getStringExtra("email");

    this.owner = new User(email);

        editButtonProfilePage = findViewById(R.id.editButtonProfile);

        editButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePage1.this, EditProfilePage.class);
                startActivity(intent);
            }
        });

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



}