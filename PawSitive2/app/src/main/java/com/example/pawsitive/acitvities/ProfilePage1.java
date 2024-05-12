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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pawsitive.R;
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class ProfilePage1 extends AppCompatActivity {
    ImageView profileImageView;
    private HashMap<String, Object> jobData, userData;

    TextView locationView, nameView, priceInfo, locationInfo, experienceInfo, languagesInfo;
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

        priceInfo = findViewById(R.id.priceInfo);
        locationInfo = findViewById(R.id.locationPropertiesInfo);
        experienceInfo = findViewById(R.id.experienceInfo);
        languagesInfo = findViewById(R.id.languagesInfo);
        locationView = findViewById(R.id.locationText);
        nameView = findViewById(R.id.profileUserName);

        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            userEmail = User.getEmail();
            DocumentReference userData = db.collection("Users").document(userEmail);
            System.out.println(userEmail);

            userData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    System.out.println("saaa");

                    name = documentSnapshot.getString("Name");
                    gender = documentSnapshot.getString("Gender");
                    System.out.println("sa");

                    profileImageStr = documentSnapshot.getString("Profile Photo");
                    byte[] decodedString = Base64.decode(profileImageStr, Base64.DEFAULT);
                    profileImageBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    System.out.println("sa1");


                    location = documentSnapshot.getString("Location");

                    locationView.setText(location);
                    String nameAndGender = name + "(" + gender + ")";
                    nameView.setText(nameAndGender);
                    System.out.println("sa2");

                    profileImageView.setImageBitmap(profileImageBitmap);
                    profileImageView.setVisibility(View.VISIBLE);

                    try{
                        DocumentReference careTakerJobData = db.collection("User").document(userEmail).collection("Jobs").document(userEmail);

                        careTakerJobData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                System.out.println("sa5");

                                String price = "Price: " + documentSnapshot.getString("Price");
                                String locationProperties = "Location Properties: " + documentSnapshot.getString("Properties");
                                String experienceLevel = "Experience: " + documentSnapshot.getString("Experience");
                                String spokenLanguages = "Languages: " + documentSnapshot.getString("Languages");
                                //date available
                                System.out.println("sa6");

                                priceInfo.setText(price);
                                locationInfo.setText(locationProperties);
                                experienceInfo.setText(experienceLevel);
                                languagesInfo.setText(spokenLanguages);

                                System.out.println("sa7");

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
        editButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileImageView.setClickable(true);
                nameView.setClickable(true);
                nameView.setEditableFactory(new Editable.Factory());

                locationView.setClickable(true);
                locationView.setEditableFactory(new Editable.Factory());

                priceInfo.setClickable(true);
                priceInfo.setEditableFactory(new Editable.Factory());

                locationInfo.setClickable(true);
                locationInfo.setEditableFactory(new Editable.Factory());

                experienceInfo.setClickable(true);
                experienceInfo.setEditableFactory(new Editable.Factory());

                languagesInfo.setClickable(true);
                languagesInfo.setEditableFactory(new Editable.Factory());

                editButtonProfilePage.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.VISIBLE);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                userEmail = User.getEmail();

                jobData = new HashMap<>();
                jobData.put("Price", priceInfo.getText().toString());
                jobData.put("Location Properties", locationInfo.getText().toString());
                jobData.put("Experience", experienceInfo.getText().toString());
                jobData.put("Languages", languagesInfo.getText().toString());

                userData = new HashMap<>();
                userData.put("Name", nameView.getText().toString());
                userData.put("Location", locationView.getText().toString());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                profileImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                profileImageStr = Base64.encodeToString(byteArray, Base64.DEFAULT);

                userData.put("Profile Photo", profileImageStr);

                db.collection("Jobs").document(userEmail).update(jobData).
                        addOnCompleteListener(ProfilePage1.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(ProfilePage1.this, "Job save successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ProfilePage1.this, "Job save failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                db.collection("Users").document(userEmail).update(userData).
                        addOnCompleteListener(ProfilePage1.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(ProfilePage1.this, "User save successful", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ProfilePage1.this, "User save failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                       });
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