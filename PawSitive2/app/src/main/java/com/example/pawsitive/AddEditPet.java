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
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;


public class AddEditPet extends AppCompatActivity {
    private FirebaseFirestore fStore;
    Button UploadImageButton, SaveButton;
    ImageView UploadImageView;
    TextView Name, Age, Weight, Notes;
    CheckBox Neutered, Microchipped, Friendly, CanBeLeftAlone, HouseTrained;
    private boolean neutered, microchipped, friendly, canBeLeftAlone, houseTrained;
    Spinner spinnerNumberOfWalksSelector, spinnerFeedingConditionerSelector,
            spinnerEnergyLevelSelector, spinnerGenderSelector, spinnerTypeSelector;
    private String typeSelection, genderSelection, energyLevelSelection, feedingConditionSelection,
            numberOfWalksSelection, name, additionalNotes, type, gender;
    private int age, weight;
    private byte[] imageInBase64;
    private boolean imageClicked = false;
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

        spinnerTypeSelector = findViewById(R.id.TypeSelector);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.PetTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTypeSelector.setAdapter(adapter);

        spinnerGenderSelector = findViewById(R.id.GenderSelector);
        ArrayAdapter<CharSequence>adapter2=ArrayAdapter.createFromResource(this, R.array.PetGenders, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerGenderSelector.setAdapter(adapter2);

        spinnerEnergyLevelSelector = findViewById(R.id.EnergyLevelSelector);
        ArrayAdapter<CharSequence>adapter3=ArrayAdapter.createFromResource(this, R.array.EnergyLevels, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEnergyLevelSelector.setAdapter(adapter3);

        spinnerFeedingConditionerSelector = findViewById(R.id.FeedingConditionsSpinner);
        ArrayAdapter<CharSequence>adapter4=ArrayAdapter.createFromResource(this, R.array.FeedingCondition, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerFeedingConditionerSelector.setAdapter(adapter4);

        spinnerNumberOfWalksSelector = findViewById(R.id.NumberOfWalksSpinner);
        ArrayAdapter<CharSequence>adapter5=ArrayAdapter.createFromResource(this, R.array.NumberOfWalks, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerNumberOfWalksSelector.setAdapter(adapter5);

        UploadImageView = findViewById(R.id.UploadImageView);

        UploadImageButton = findViewById(R.id.UploadImageButton);
        SaveButton = findViewById(R.id.SaveButton);

        Name = findViewById(R.id.PetNameInput);
        Age = findViewById(R.id.PetAgeInput);
        Weight = findViewById(R.id.PetSizeInput);
        Notes = findViewById(R.id.AdditionalNotes);

        Neutered = findViewById(R.id.NeuteredCheck);
        Microchipped = findViewById(R.id.MicrochippedCheck);
        Friendly = findViewById(R.id.FriendlyCheck);
        CanBeLeftAlone = findViewById(R.id.LeftAloneCheck);
        HouseTrained = findViewById(R.id.HouseTrainedCheck);

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSelection = spinnerTypeSelector.getSelectedItem().toString();
                genderSelection = spinnerGenderSelector.getSelectedItem().toString();
                energyLevelSelection = spinnerEnergyLevelSelector.getSelectedItem().toString();
                feedingConditionSelection = spinnerFeedingConditionerSelector.getSelectedItem().toString();
                numberOfWalksSelection = spinnerNumberOfWalksSelector.getSelectedItem().toString();

                //imageInBase64

                name = Name.getText().toString();
                additionalNotes = Notes.getText().toString();

                age = Integer.parseInt(Age.getText().toString());
                weight = Integer.parseInt(Weight.getText().toString());

                neutered = Neutered.isChecked();
                microchipped = Microchipped.isChecked();
                friendly = Friendly.isChecked();
                canBeLeftAlone = CanBeLeftAlone.isChecked();
                houseTrained = HouseTrained.isChecked();

                Pet newPet = new Pet(name, typeSelection, energyLevelSelection, feedingConditionSelection, numberOfWalksSelection,
                        additionalNotes, age, weight, neutered, microchipped, friendly, canBeLeftAlone, houseTrained, imageInBase64);



            }
        });

        UploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClicked = true;
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        UploadImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(!imageClicked){
                    Toast.makeText(AddEditPet.this, "Select an image first", Toast.LENGTH_SHORT).show();
                }
                else{
                    //BASE 64 CONVERSION
                    UploadImageView.buildDrawingCache();
                    Bitmap photo = UploadImageView.getDrawingCache();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    imageInBase64 = bos.toByteArray();
                }

            }
        });
    }

}