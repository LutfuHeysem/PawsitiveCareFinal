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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CreditCardInfo extends AppCompatActivity {
    private Button back, confirm;
    private String[] date = new String[2];
    private int month, year;
    private String nameCard, number, exp, cvv, nameC, passC, locC, mailC, imageStrC;
    private EditText editNameCard, editNum, editCvv, editExp;
    private FirebaseAuth auth;
    private HashMap<String, Object> userData;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_info);

        back = (Button) findViewById(R.id.backBut);
        confirm = (Button) findViewById(R.id.confirmBut);

        CreateNewAccount data = new CreateNewAccount();

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        editNameCard = (EditText) findViewById(R.id.editTextNam);
        editCvv = (EditText) findViewById(R.id.editTextCVV);
        editNum = (EditText) findViewById(R.id.editTextNumber);
        editExp = (EditText) findViewById(R.id.editTextExp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreditCardInfo.this, CreateNewAccount.class);
                startActivity(intent);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                nameC = intent.getStringExtra("name");
                passC = intent.getStringExtra("pass");
                locC = intent.getStringExtra("loc");
                mailC = intent.getStringExtra("email");
                imageStrC = intent.getStringExtra("imageStr");

                nameCard = editNameCard.getText().toString();
                exp = editExp.getText().toString();
                number = editNum.getText().toString();
                cvv = editCvv.getText().toString();

                date = exp.split("/");

                month = Integer.parseInt(date[0]);
                year = Integer.parseInt(date[1]);

                if(TextUtils.isEmpty(nameCard) || TextUtils.isEmpty(exp) || TextUtils.isEmpty(number) || TextUtils.isEmpty(String.valueOf(cvv))) {
                    Toast.makeText(CreditCardInfo.this, "You need to fill all the information", Toast.LENGTH_SHORT).show();
                }
                else if (exp.length() != 7 || cvv.length() != 3 || number.length() != 16){
                    Toast.makeText(CreditCardInfo.this, "Please check your inputs' digit numbers", Toast.LENGTH_SHORT).show();
                }
                else if(month > 12 || year < 2024) {
                    Toast.makeText(CreditCardInfo.this, "Check your Expiration Date!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth.createUserWithEmailAndPassword(mailC, passC).addOnCompleteListener(CreditCardInfo.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(CreditCardInfo.this, "Verification email has sent!", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                            userData = new HashMap<>();
                                            userData.put("Name", nameC);
                                            userData.put("Email", mailC);
                                            userData.put("Location", locC);
                                            userData.put("Name On The Credit Card", nameCard);
                                            userData.put("Card Number", number);
                                            userData.put("CVV", cvv);
                                            userData.put("Expiration Date", exp);
                                            userData.put("Profile Photo", imageStrC);

                                            fStore.collection("Users").document(auth.getCurrentUser().getEmail())
                                                    .set(userData).addOnCompleteListener(CreditCardInfo.this, new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful())
                                                            {
                                                                Toast.makeText(CreditCardInfo.this, "Successful Sign Up", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(CreditCardInfo.this, Login.class);
                                                                startActivity(intent);
                                                            }
                                                            else
                                                                Toast.makeText(CreditCardInfo.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                    }
                                    else
                                        Toast.makeText(CreditCardInfo.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}