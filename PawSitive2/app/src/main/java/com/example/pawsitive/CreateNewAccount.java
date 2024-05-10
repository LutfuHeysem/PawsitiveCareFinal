package com.example.pawsitive;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNewAccount extends AppCompatActivity {

    //instance variables
    private String name, pass, email, loc;
    private EditText editName, editEmail, editPass, editLoc;
    private Button back, signUp;
    private CheckBox privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        //Create Account Initialization
        back = findViewById(R.id.BackButton);
        signUp = findViewById(R.id.button);

        editName = (EditText) findViewById(R.id.name);
        editEmail = (EditText) findViewById(R.id.email);
        editPass = (EditText) findViewById(R.id.pass);
        editLoc = (EditText) findViewById(R.id.loc);

        privacy = (CheckBox) findViewById(R.id.checkBox);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                name = editName.getText().toString();
                pass = editPass.getText().toString();
                email = editEmail.getText().toString();
                loc = editLoc.getText().toString().toUpperCase();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(email) || TextUtils.isEmpty(loc))
                    Toast.makeText(CreateNewAccount.this, "Information parts cannot be empty!", Toast.LENGTH_SHORT).show();
                if(!privacy.isChecked())
                    Toast.makeText(CreateNewAccount.this, "You must accept privacy policy and terms to sign up!", Toast.LENGTH_SHORT).show();
                else
                {
                    Intent intent = new Intent(CreateNewAccount.this, CreditCardInfo.class);
                    intent.putExtra("name", name);
                    intent.putExtra("pass", pass);
                    intent.putExtra("loc", loc);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });
    }

    public String getMail() {return email;}
    public String getPassword() {return pass;}
    public String getLocation() {return loc;}
    public String getName() {return name;}



}















