package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pawsitive.R;
import com.example.pawsitive.classes.AddDialog;
import com.example.pawsitive.classes.User;

public class FavouritesPage extends AppCompatActivity {

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

        favouritesIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //handle click on favourites image
                //navigate to favourite page
                Intent intent = new Intent(FavouritesPage.this, FavouritesPage.class);
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
}