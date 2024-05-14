package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.adapters.MyPetsAdapter;
import com.example.pawsitive.classes.MyPetModel;
import com.example.pawsitive.databinding.ActivityMyPetsBinding;
import com.example.pawsitive.databinding.ActivityUsersBinding;
import com.example.pawsitive.listeners.PetsListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MyPets extends AppCompatActivity implements PetsListener {
    RecyclerView recyclerView;
    public ArrayList<MyPetModel> petList = new ArrayList<>();
    MyPetsAdapter petsAdapter;
    FirebaseFirestore fStore;
    FirebaseAuth auth;
    String imageStr = "", name = "", type = "", friendly = "", neutered = "", microchipped = "", food = "", walks = "", trained = "";
    Double age = (double) 0;
    Double weight = (double) 0;
    Button backButton, addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_pets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        displayItems();

        backButton = findViewById(R.id.backButton1);
        addButton = findViewById(R.id.addButton);

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage1.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddEditPet.class);
                startActivity(intent);
            }
        });

    }

    private void displayItems() {

        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        fStore.collection("Users").document(auth.getCurrentUser().getEmail()).collection("Pets").
                get().addOnCompleteListener(task ->{
                    if(task.isSuccessful() && task.getResult() != null){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            imageStr = document.getString("Image");
                            name = document.getString("Name");
                            type = document.getString("Type");
                            friendly = (Boolean.TRUE.equals(document.getBoolean("Friendly"))?"Friendly":"NotFriendly");
                            neutered = (Boolean.TRUE.equals(document.getBoolean("Neutered"))?"Neutered":"NotNeutered");
                            microchipped = (Boolean.TRUE.equals(document.getBoolean("Microchipped"))?"Microchipped":"NotMicrochipped");
                            trained = (Boolean.TRUE.equals(document.getBoolean("House Trained"))?"Trained":"NotTrained");
                            food = document.getString("Feeding Condition").equals("Prefer Lamb")?"Lamb":"Sea Food";
                            walks = document.getString("Number Of Walks");
                            age = document.getDouble("Age");
                            weight = document.getDouble("Weight");

                            petList.add(new MyPetModel(imageStr, name, type, friendly, neutered, microchipped,
                                    food, walks, age, weight, trained));

                            System.out.println(petList.size());
                        }
                        if(!petList.isEmpty()){
                            petsAdapter = new MyPetsAdapter(this, petList, this);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(petsAdapter);
                        }
                    }
                });

    }

    @Override
    public void onPetEditClicked(String petName) {
        Intent intent = new Intent(getApplicationContext(), AddEditPet.class);
        intent.putExtra("PetName", petName);
        startActivity(intent);
    }
}