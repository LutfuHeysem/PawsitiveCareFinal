package com.example.pawsitive;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPassword extends AppCompatActivity {

    private Button back, confirm;
    private EditText mail;
    private String mailString;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        back = findViewById(R.id.button3);
        confirm = findViewById(R.id.button6);

        mail = findViewById(R.id.mail);

        mAuth = FirebaseAuth.getInstance();

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
                mailString = mail.getText().toString().trim();
                if (!TextUtils.isEmpty(mailString))
                    ResetPassword();
                else
                    Toast.makeText(ForgetPassword.this, "Enter the email that verification will be sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ResetPassword()
    {;
        mAuth.sendPasswordResetEmail(mailString).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgetPassword.this, "Reset Password link has been sent!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ForgetPassword.this, Login.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
