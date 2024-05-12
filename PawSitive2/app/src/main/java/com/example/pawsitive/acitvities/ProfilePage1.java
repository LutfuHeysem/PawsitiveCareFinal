package com.example.pawsitive.acitvities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pawsitive.R;
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfilePage1 extends AppCompatActivity {
    ImageView profileImageView;
    TextView allInfoView, locationView, nameView;
    String userEmail, profileImageStr, name, location, gender;
    String careTakerInfo;
    Bitmap profileImageBitmap;
    Button backButtonProfilePage, editButtonProfilePage, calendarButton, saveButton;
    public final int GET_FROM_GALLERY = 3;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                profileImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("123");
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
        calendarButton = findViewById(R.id.EditCalendarButton);
        saveButton = findViewById(R.id.saveButton);

        profileImageView = findViewById(R.id.profileImage);

        allInfoView = findViewById(R.id.allInfo);
        locationView = findViewById(R.id.locationText);
        nameView = findViewById(R.id.profileUserName);

        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            userEmail = User.getEmail();
            DocumentReference userData = db.collection("Users").document(userEmail);

            userData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    name = documentSnapshot.getString("Name");
                    gender = documentSnapshot.getString("Gender");

                    profileImageStr = documentSnapshot.getString("Image");
                    byte[] decodedString = Base64.decode(profileImageStr, Base64.DEFAULT);
                    profileImageBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    location = documentSnapshot.getString("Location");

                    locationView.setText(location);
                    String nameAndGender = name + "(" + gender + ")";
                    nameView.setText(nameAndGender);

                    profileImageView.setImageBitmap(profileImageBitmap);
                    profileImageView.setVisibility(View.VISIBLE);

                    try{
                        DocumentReference careTakerJobData = db.collection("User").document(userEmail).collection("Jobs").document(userEmail);

                        careTakerJobData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String price = "Price: " + documentSnapshot.getString("Price");
                                String location = "Location: " + documentSnapshot.getString("Location");
                                String locationProperties = "Location Properties: " + documentSnapshot.getString("Properties");
                                String experienceLevel = "Experience: " + documentSnapshot.getString("Experience");
                                String spokenLanguages = "Languages: " + documentSnapshot.getString("Languages");
                                //date available

                                careTakerInfo = price + "\n" + location + "\n" + locationProperties + "\n" + experienceLevel + "\n" +
                                                spokenLanguages;

                                allInfoView.setText(careTakerInfo);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Failed to fetch job data from Firebase: " + e.getMessage());
                            }
                        });
                    }catch(Exception e){
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
        editButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileImageView.setClickable(true);
                nameView.setClickable(true);
                nameView.setEditableFactory(new Editable.Factory());

                locationView.setClickable(true);
                locationView.setEditableFactory(new Editable.Factory());

                allInfoView.setClickable(true);
                allInfoView.setEditableFactory(new Editable.Factory());

                editButtonProfilePage.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.VISIBLE);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarEdit.class);
                startActivity(intent);
            }
        });
    }
}