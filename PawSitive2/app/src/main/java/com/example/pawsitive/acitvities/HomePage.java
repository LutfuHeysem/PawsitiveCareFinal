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
import com.example.pawsitive.classes.FavouriteJobs;
import com.example.pawsitive.classes.Job;
import com.example.pawsitive.classes.User;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    List<FavouriteJobs> favouriteJobs;

    ImageView homeIcon,favouritesIcon,addIcon,chatIcon,profileIcon,favsBlankHeart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        initializeImageViews();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

       // this list is the one to be displayed
        List <Job> jobs= new ArrayList<Job>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println("adsd");
        recyclerView.setAdapter(new HomePageDisplayAdapter(getApplicationContext(),jobs));
        System.out.println("aaaududu");

        }

    public List<FavouriteJobs> getFavouriteJobs() {
        return favouriteJobs;
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
                Intent intent = new Intent(HomePage.this, AddEditPet.class);
                startActivity(intent);
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

        //changing the icon
        favsBlankHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favsBlankHeart.setImageResource(R.drawable.heart);
                FavouriteJobs newFav = new FavouriteJobs();
                favouriteJobs.add(newFav);
            }
        });



    };


}
