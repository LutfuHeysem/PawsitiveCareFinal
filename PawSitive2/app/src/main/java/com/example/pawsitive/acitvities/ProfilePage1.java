package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pawsitive.R;
import com.example.pawsitive.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilePage1 extends AppCompatActivity {
    ImageView profileImage;
    TextView profileUserName;
    TextView stars;
    User owner;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
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
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        Button backButtonProfilePage = (Button) findViewById(R.id.backButtonProfilePage);
        System.out.println("2");
        backButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
    Intent intent = getIntent();

    String email = intent.getStringExtra("email");

    this.owner = new User(email);

        Button editButtonProfilePage = findViewById(R.id.editButtonProfile);

        editButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilePageEdit.class);
                startActivity(intent);
            }
        });
        System.out.println("3");
        profileImage = (ImageView) findViewById(R.id.profileImage);
        profileUserName = (TextView) findViewById(R.id.profileUserName);
        //will be done when we learn how to get data from firebase
        //profileUserName.setText(user.getUsername);
        System.out.println("4");
        stars = (TextView) findViewById(R.id.ratingStars);

        TextView location = (TextView) findViewById(R.id.locationText);
        location.setText(getString(R.string.location) + owner.getLocation());
        System.out.println("112");
                EditText allInfo = findViewById(R.id.allinfo);
        System.out.println("113");
        System.out.println(mUser);
        System.out.println(owner);

        if(owner.getEmail().equals(mUser.getEmail()))//dk how to do will research
        {
            allInfo.setFocusable(true);

        }
        else
        {
            allInfo.setFocusable(false);
        }
        System.out.println("12");
        //stars.setBackgroundResource(ReviewMain.getResource(ReviewMain.calculateStarAverage(owner)));
        System.out.println("5");


    }
    private void changeProfilePhoto(ImageView newPhoto)
    {
        Drawable profileIcon = newPhoto.getDrawable();
        profileImage.setImageDrawable(profileIcon);
    }
}