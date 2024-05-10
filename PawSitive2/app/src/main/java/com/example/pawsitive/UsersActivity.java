package com.example.pawsitive;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pawsitive.adapters.UsersAdapter;
import com.example.pawsitive.databinding.ActivityUsersBinding;
import com.example.pawsitive.listeners.UserListener;
import com.example.pawsitive.utilities.Constants;
import com.example.pawsitive.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
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
        System.out.println("qwer");
        getUsers();

    }
    private void setListener(){
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void getUsers() {
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if(task.isSuccessful() && task.getResult() != null){

                        System.out.println(task.getResult());

                        List<UserForChat> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
//                            if(currentUserId.equals(queryDocumentSnapshot.getId())){
//                                continue;
//                            }
                            System.out.println(queryDocumentSnapshot.getString(Constants.KEY_NAME));
                            UserForChat user = new UserForChat();
                            user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.token = queryDocumentSnapshot.getString("img");
                            users.add(user);
                        }
                        if(!users.isEmpty()){
                            UsersAdapter usersAdapter = new UsersAdapter(users, this);
                            binding.userRecylerView.setVisibility(View.VISIBLE);
                            binding.userRecylerView.setAdapter(usersAdapter);
                        }else{
                            showErrorMessage();
                        }
                    }

                    else{
                        showErrorMessage();
                    }

                });

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
        finish();
    }
}