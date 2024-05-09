package com.example.pawsitive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;


public class AddEditPet extends AppCompatActivity {
    private FirebaseFirestore fStore;

    ImageView UploadImageView;
    Button UploadImageButton;


    private String petName, additionalNotes, type, gender;
    private int age, weight;
    private byte[] imageInBase64;
    private User owner;

    public final int GET_FROM_GALLERY = 3;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                UploadImageView.setImageBitmap(bitmap);
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_pet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinnerTypeSelector=findViewById(R.id.TypeSelector);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.PetTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTypeSelector.setAdapter(adapter);

        Spinner spinnerGenderSelector=findViewById(R.id.GenderSelector);
        ArrayAdapter<CharSequence>adapter2=ArrayAdapter.createFromResource(this, R.array.PetGenders, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerGenderSelector.setAdapter(adapter2);

        UploadImageButton = findViewById(R.id.UploadImageButton);
        UploadImageView = findViewById(R.id.UploadImageView);


        UploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        UploadImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //BASE 64 CONVERSION
                UploadImageView.buildDrawingCache();
                Bitmap photo = UploadImageView.getDrawingCache();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
                imageInBase64 = bos.toByteArray();
            }
        });
    }

}