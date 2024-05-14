package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawsitive.R;
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private Button back, forget, login;

    private EditText emailEdit, passEdit;
    private String logEmail, logPass;

    private FirebaseAuth currAuth;
    private FirebaseUser currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        back = findViewById(R.id.button3);
        forget = findViewById(R.id.button5);
        login = findViewById(R.id.button4);

        emailEdit = findViewById(R.id.editTextText);
        passEdit = findViewById(R.id.editTextText5);

        currAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logEmail = emailEdit.getText().toString();
                logPass = passEdit.getText().toString();

                if(TextUtils.isEmpty(logEmail))
                    Toast.makeText(Login.this, "You must enter an email!", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(logPass))
                    Toast.makeText(Login.this, "You must enter a password!", Toast.LENGTH_SHORT).show();
                else {
                    currAuth.signInWithEmailAndPassword(logEmail, logPass)
                            .addOnSuccessListener(Login.this, new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    currUser = currAuth.getCurrentUser();
                                    if(currUser != null && currUser.isEmailVerified())
                                    {
                                    //TEMPORARY

                                                User user = new User(logEmail);
                                                Toast.makeText(Login.this, "Successful Login!", Toast.LENGTH_SHORT).show();

                                                Intent homeIntent = new Intent(Login.this, HomePage.class);
                                                Intent editPetIntent = new Intent(Login.this, AddEditPet.class).putExtra("PetName", "Venus");
                                                //WILL GET PET NAME WHEN EDITING
                                                Intent addPetIntent = new Intent(Login.this, AddEditPet.class);
                                                Intent chatIntent = new Intent(Login.this, UsersActivity.class);
                                                Intent reviewIntent = new Intent(Login.this, ReviewListActivity.class);
                                                Intent profileIntent = new Intent(Login.this, ProfilePage1.class);
                                                Intent filterIntent = new Intent(Login.this, Filtering.class);

                                                startActivity(addPetIntent);



                                    }
                                    else if(currUser != null && !currUser.isEmailVerified())
                                        Toast.makeText(Login.this, "Verify your email first!", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(Login.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(Login.this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}