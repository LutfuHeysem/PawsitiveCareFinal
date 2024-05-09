package com.example.pawsitive;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPassword extends AppCompatActivity {

    private Button back, confirm;
    private EditText newPass, newPass2;
    private String newPassString, newPass2String;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        back = findViewById(R.id.button3);
        confirm = findViewById(R.id.button6);

        newPass = findViewById(R.id.editTextText);
        newPass2 = findViewById(R.id.editTextText5);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassword.this, Login.class);
                startActivity(intent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassString = newPass.getText().toString();
                newPass2String = newPass2.getText().toString();

                if(TextUtils.isEmpty(newPassString) || TextUtils.isEmpty(newPass2String))
                    Toast.makeText(ForgetPassword.this, "You must fill the all blanks!", Toast.LENGTH_SHORT).show();
                else if(!newPassString.equals(newPass2String))
                    Toast.makeText(ForgetPassword.this, "Both blanks must be same!", Toast.LENGTH_SHORT).show();
                    //else if()
                else {
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                if(mAuth.getCurrentUser().updatePassword(newPassString).isSuccessful())
                                    Toast.makeText(ForgetPassword.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(ForgetPassword.this, mAuth.getCurrentUser().updatePassword(newPassString).getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(ForgetPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}