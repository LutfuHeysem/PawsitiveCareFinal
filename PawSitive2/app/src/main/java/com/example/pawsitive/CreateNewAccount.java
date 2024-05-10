package com.example.pawsitive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class CreateNewAccount extends AppCompatActivity {

    //instance variables
    private String name, pass, email, loc, imageStr;
    private EditText editName, editEmail, editPass, editLoc;
    private Button back, signUp, saveImage;
    private ImageView profilePhot;
    private boolean imageClicked, saveClicked = false;
    private CheckBox privacy;
    private byte[] imageInBase64;
    public final int GET_FROM_GALLERY = 3;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                profilePhot.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        //Create Account Initialization
        back = findViewById(R.id.BackButton);
        signUp = findViewById(R.id.button);
        saveImage = findViewById(R.id.UploadPP);

        editName = (EditText) findViewById(R.id.name);
        editEmail = (EditText) findViewById(R.id.email);
        editPass = (EditText) findViewById(R.id.pass);
        editLoc = (EditText) findViewById(R.id.loc);
        profilePhot = (ImageView) findViewById(R.id.imageView5);

        privacy = (CheckBox) findViewById(R.id.checkBox);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });
        profilePhot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClicked = true;
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        saveImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(!imageClicked){
                    Toast.makeText(CreateNewAccount.this, "Select an image first", Toast.LENGTH_SHORT).show();
                }
                else{
                    //BASE 64 CONVERSION
                    saveClicked = true;
                    profilePhot.buildDrawingCache();
                    Bitmap photo = profilePhot.getDrawingCache();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    imageInBase64 = bos.toByteArray();
                    imageStr = Base64.getEncoder().encodeToString(imageInBase64);
                    Toast.makeText(CreateNewAccount.this, "Save successful", Toast.LENGTH_SHORT).show();

                }
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
                if(!saveClicked)
                    Toast.makeText(CreateNewAccount.this, "You must save the image you uploaded!", Toast.LENGTH_SHORT).show();
                else
                {
                    Intent intent = new Intent(CreateNewAccount.this, CreditCardInfo.class);
                    intent.putExtra("name", name);
                    intent.putExtra("loc", loc);
                    intent.putExtra("email", email);
                    intent.putExtra("imageStr", imageStr);
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















