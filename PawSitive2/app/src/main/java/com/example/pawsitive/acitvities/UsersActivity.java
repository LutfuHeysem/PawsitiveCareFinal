package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawsitive.classes.Pet;
import com.example.pawsitive.classes.User;
import com.example.pawsitive.classes.UserForChat;
import com.example.pawsitive.adapters.UsersAdapter;
import com.example.pawsitive.databinding.ActivityUsersBinding;
import com.example.pawsitive.listeners.UserListener;
import com.example.pawsitive.utilities.Constants;
import com.example.pawsitive.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements UserListener {

    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListener();
        getUsers();

    }

    private void backPressed(){
        Intent intent = new Intent(UsersActivity.this, HomePage.class);
        startActivity(intent);
        finish();
    }
    private void setListener(){
        binding.imageBack.setOnClickListener(v -> backPressed());
    }

    private void getUsers() {
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
            try {
                db.collection("Users")
                        .get()
                        .addOnCompleteListener(task -> {
                            loading(false);
                            String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                            if (task.isSuccessful() && task.getResult() != null) {

                                List<UserForChat> users = new ArrayList<>();
                                UserForChat user;
                                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
    //                            if(currentUserId.equals(queryDocumentSnapshot.getId())){
    //                                continue;
    //                            }

                                    user = new UserForChat();
                                    user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                                    user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                                    //user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                                    user.img = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                                    user.id = queryDocumentSnapshot.getId();
                                    if (User.searchInsidefChat(user.email)) {
                                        users.add(user);
                                    }
                                }
                                if (!users.isEmpty()) {
                                    UsersAdapter usersAdapter = new UsersAdapter(users, this);
                                    binding.userRecylerView.setVisibility(View.VISIBLE);
                                    binding.userRecylerView.setAdapter(usersAdapter);
                                } else {
                                    showErrorMessage();
                                }
                            } else {
                                showErrorMessage();
                            }

                        });
            }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    private void showErrorMessage(){
        binding.errorMessage.setText(String.format("%s", "No user available"));
        binding.errorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUserClicked(UserForChat user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
    }

    @Override
    public void onUserClicked(String user) {

    }


}