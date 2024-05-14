package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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

import com.example.pawsitive.R;
import com.example.pawsitive.classes.Review;
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProfilePage2 extends AppCompatActivity {

    Button backButtonProfilePage2, reviewsButtonProfilePage2, myAnimalsButtonProfilePage2;
    private ArrayList<Review> reviewArrayListProfilePage2;
    TextView locationViewProfilePage2, nameViewProfilePage2, priceInfoProfilePage2, locationInfoProfilePage2,
            experienceInfoProfilePage2, languagesInfoProfilePage2;
    String userEmailProfilePage2, profileImageStrProfilePage2, nameProfilePage2, locationProfilePage2, genderProfilePage2;
    Bitmap profileImageBitmapProfilePage2;
    ImageView homeButtonProfilePage2, favoritesButtonProfilePage2, addButtonProfilePage2, chatButtonProfilePage2, profileImageProfilePage2;
    RatingBar rateBarProfilePage2;
    User owner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
            

        });

        priceInfoProfilePage2 = findViewById(R.id.priceInfoProfilePage2);
        locationInfoProfilePage2 = findViewById(R.id.locationPropertiesInfoProfilePage2);
        experienceInfoProfilePage2 = findViewById(R.id.experienceInfoProfilePage2);
        languagesInfoProfilePage2 = findViewById(R.id.languagesInfoProfilePage2);
        locationViewProfilePage2 = findViewById(R.id.locationTextProfilePage2);
        nameViewProfilePage2 = findViewById(R.id.profileUserNameProfilePage2);


        backButtonProfilePage2 = findViewById(R.id.backButtonProfilePage2);
        backButtonProfilePage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();

        String email = intent.getStringExtra("email");
        this.owner = new User(email);

        DocumentReference careTakerJobData = db.collection("Jobs").document(owner.getEmail());
        careTakerJobData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String price = documentSnapshot.getString("Price");
                String locationProperties = documentSnapshot.getString("Location Properties");
                String experienceLevel = documentSnapshot.getString("Experience");
                String spokenLanguages = documentSnapshot.getString("Languages");
                //date available

                priceInfoProfilePage2.setText(price);
                locationInfoProfilePage2.setText(locationProperties);
                experienceInfoProfilePage2.setText(experienceLevel);
                languagesInfoProfilePage2.setText(spokenLanguages);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failed to fetch job data from Firebase: " + e.getMessage());
            }
        });





        //?
        profileImageStrProfilePage2 = owner.getImage();
        byte[] decodedString = Base64.decode(profileImageStrProfilePage2, Base64.DEFAULT);
        profileImageBitmapProfilePage2 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        profileImageProfilePage2 = findViewById(R.id.profileImageProfilePage2);
        profileImageProfilePage2.setImageBitmap(profileImageBitmapProfilePage2);





    }
}