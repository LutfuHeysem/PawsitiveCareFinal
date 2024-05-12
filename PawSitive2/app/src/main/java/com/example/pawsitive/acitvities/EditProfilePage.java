package com.example.pawsitive.acitvities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class EditProfilePage extends AppCompatActivity {

    ImageView profileImageView;
    public final int GET_FROM_GALLERY = 3;
    TextView locationView, nameView, priceInfo, locationInfo, experienceInfo, languagesInfo;
    Bitmap profileImageBitmap;
    Button backButtonProfilePage, editButtonProfilePage, calendarButton, saveButton;
    private HashMap<String, Object> jobData, userData;
    String userEmail, profileImageStr;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!profileImageView.isClickable())
            return;

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

        changeEditable(true);

        backButtonProfilePage = (Button) findViewById(R.id.backButtonProfilePage);
        saveButton = findViewById(R.id.saveButton);

        profileImageView = findViewById(R.id.profileImage);

        priceInfo = findViewById(R.id.priceInfo);
        locationInfo = findViewById(R.id.locationPropertiesInfo);
        experienceInfo = findViewById(R.id.experienceInfo);
        languagesInfo = findViewById(R.id.languagesInfo);
        locationView = findViewById(R.id.locationText);
        nameView = findViewById(R.id.profileUserName);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
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
                userData.put("Name", nameView.getText().toString().substring(0, nameView.getText().toString().indexOf("(")));
                userData.put("Location", locationView.getText().toString());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                profileImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                profileImageStr = Base64.encodeToString(byteArray, Base64.DEFAULT);

                userData.put("Profile Photo", profileImageStr);

                db.collection("Jobs").document(userEmail).update(jobData).
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

    }

    private void changeEditable(boolean trueOrFalse){
        System.out.println("changeEditable " + trueOrFalse);
        profileImageView.setFocusable(trueOrFalse);
        profileImageView.setClickable(trueOrFalse);
        System.out.println(profileImageView.isClickable());

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
}