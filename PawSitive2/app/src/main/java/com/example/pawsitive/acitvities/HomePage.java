package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.adapters.HomePageDisplayAdapter;
import com.example.pawsitive.classes.FavouriteJobs;
import com.example.pawsitive.classes.Job;

import com.example.pawsitive.classes.AddDialog;
import com.example.pawsitive.classes.User;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {


    List<FavouriteJobs> favouriteJobs;
    List <Job> jobs;
    //ArrayAdapter<Job> adapter;

private boolean imageChanged = false;

    ImageView homeIcon,favouritesIcon,addIcon,chatIcon,profileIcon,favsBlankHeart;
    //SearchView searchView;
    //ListView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initializeImageViews();

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);


       // this list is the one to be displayed
        jobs= new ArrayList<Job>();



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println("adsd");
        recyclerView.setAdapter(new HomePageDisplayAdapter(getApplicationContext(),jobs));
        System.out.println("aaaududu");


        // implementation for searching job / searchbar
       /* searchView = findViewById(R.id.search);
        myListView = findViewById(R.id.listView);
        myListView.setVisibility(View.GONE);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,jobs);

        myListView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myListView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(newText);
                return false;
            }
        });*/

        }


    public List<FavouriteJobs> getFavouriteJobs() {
        return favouriteJobs;
    }
    public List<Job> getJobs(){return jobs;}


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
