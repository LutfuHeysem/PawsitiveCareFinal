package com.example.pawsitive.acitvities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pawsitive.R;

import java.util.ArrayList;

public class ProfilePage2 extends AppCompatActivity {

    Button backButtonProfilePage2, reviewsButtonProfilePage2, myAnimalsButtonProfilePage2;
    private ArrayList<Review> reviewArrayListProfilePage2;
    TextView locationViewProfilePage2, nameViewProfilePage2, priceInfoProfilePage2, locationInfoProfilePage2,
            experienceInfoProfilePage2, languagesInfoProfilePage2;
    String userEmailProfilePage2, profileImageStrProfilePage2, nameProfilePage2, locationProfilePage2, genderProfilePage2;
    Bitmap profileImageBitmapProfilePage2;
    ImageView homeButtonProfilePage2, favoritesButtonProfilePage2, addButtonProfilePage2, chatButtonProfilePage2;
    RatingBar rateBarProfilePage2;
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
    }
}