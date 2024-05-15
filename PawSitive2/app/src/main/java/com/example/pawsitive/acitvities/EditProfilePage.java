package com.example.pawsitive.acitvities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.pawsitive.classes.AddDialog;
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class EditProfilePage extends AppCompatActivity {

    ImageView profileImageView, homeButton, favoritesButton, addButton, chatButton;
    public final int GET_FROM_GALLERY = 3;
    TextView locationView, nameView, priceInfo, locationInfo, experienceInfo, languagesInfo;
    Bitmap profileImageBitmap;
    Button backButtonProfilePage, saveButton;
    private HashMap<String, Object> jobData, userData;
    String userEmail, profileImageStr;
    String name, location, gender;


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
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backButtonProfilePage = (Button) findViewById(R.id.backButtonProfilePage);
        saveButton = findViewById(R.id.saveButton);

        profileImageView = findViewById(R.id.profileImage);

        homeButton = findViewById(R.id.homeIcon);
        favoritesButton = findViewById(R.id.heart_icon);
        addButton = findViewById(R.id.add_icon);
        chatButton = findViewById(R.id.chat_icon);

        priceInfo = findViewById(R.id.priceInfo);
        locationInfo = findViewById(R.id.locationPropertiesInfo);
        experienceInfo = findViewById(R.id.experienceInfo);
        languagesInfo = findViewById(R.id.languagesInfo);
        locationView = findViewById(R.id.locationText);
        nameView = findViewById(R.id.profileUserName);

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
                    nameView.setText(name);

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

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                jobData = new HashMap<>();
                jobData.put("Price", priceInfo.getText().toString());
                jobData.put("Location Properties", locationInfo.getText().toString());
                jobData.put("Experience", experienceInfo.getText().toString().substring(experienceInfo.getText().toString().indexOf(",")+1).trim());
                jobData.put("Languages", languagesInfo.getText().toString());
                jobData.put("Gender", gender);
                jobData.put("Email", userEmail);
                jobData.put("Location", location);


                userData = new HashMap<>();
                userData.put("Name", nameView.getText().toString());
                userData.put("Location", locationView.getText().toString());


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                profileImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                profileImageStr = Base64.encodeToString(byteArray, Base64.DEFAULT);


                userData.put("Profile Photo", profileImageStr);


                db.collection("Jobs").document(userEmail).set(jobData).
                        addOnCompleteListener(EditProfilePage.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(EditProfilePage.this, "Job save successful", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(EditProfilePage.this, "Job save failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                db.collection("Users").document(userEmail).update(userData).
                        addOnCompleteListener(EditProfilePage.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(EditProfilePage.this, "User save successful", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(EditProfilePage.this, "User save failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                Intent intent = new Intent(EditProfilePage.this, ProfilePage1.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), HomePage.class);
//                startActivity(intent);
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

}