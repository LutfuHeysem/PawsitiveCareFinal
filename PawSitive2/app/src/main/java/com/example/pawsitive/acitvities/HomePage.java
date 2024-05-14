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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.adapters.HomePageDisplayAdapter;
import com.example.pawsitive.classes.AddDialog;
import com.example.pawsitive.classes.MakeOfferDialog;
import com.example.pawsitive.classes.User;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

private boolean imageChanged = false;

    ImageView homeIcon,favouritesIcon,addIcon,chatIcon,profileIcon,favsBlankHeart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initializeImageViews();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        //List<User> users = new ArrayList<User>();
        List <Temp> users= new ArrayList<Temp>();
        users.add(new Temp("John", "Male", "23"));
        users.add(new Temp("John", "Male", "23"));
        users.add(new Temp("John", "Male", "23"));
        users.add(new Temp("John", "Male", "23"));
        users.add(new Temp("John", "Male", "23"));
        users.add(new Temp("John", "Male", "23"));
        users.add(new Temp("John", "Male", "23"));
        users.add(new Temp("John", "Male", "23"));



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println("adsd");
        recyclerView.setAdapter(new HomePageDisplayAdapter(getApplicationContext(),users));
        System.out.println("aaaududu");

        }
    private void initializeImageViews(){

        homeIcon = findViewById(R.id.homeIcon);
        favouritesIcon = findViewById(R.id.heart_icon);
        addIcon = findViewById(R.id.add_icon);
        chatIcon = findViewById(R.id.chat_icon);
        profileIcon = findViewById(R.id.profile_icon);
        favsBlankHeart = findViewById(R.id.heart);

        homeIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //handle click on home image
                //navigate to home page
                Intent intent = new Intent(HomePage.this, HomePage.class);
                startActivity(intent);
            }
        });

        favouritesIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //handle click on favourites image
                //navigate to favourite page
                Intent intent = new Intent(HomePage.this, FavouritesPage.class);
                startActivity(intent);
            }
        });

        addIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //handle click on add image
                //navigate to add/edit page
                AddDialog dialog = new AddDialog();
                dialog.show(getSupportFragmentManager(), "AddDialog");
            }
        });

        chatIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //handle click on chat image
                //navigate to chat page
                Intent intent = new Intent(HomePage.this, UsersActivity.class);
                startActivity(intent);
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //handle click on profile image
                //navigate to profile page
                Intent intent = new Intent(HomePage.this, ProfilePage1.class);
                intent.putExtra("email",User.getEmail());
                startActivity(intent);
            }
        });

        favsBlankHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favsBlankHeart.setImageResource(R.drawable.heart);
            }
        });

    };


}
