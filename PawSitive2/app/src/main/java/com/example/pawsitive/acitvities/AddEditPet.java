package com.example.pawsitive.acitvities;

import android.util.Base64;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import java.util.HashMap;

import com.example.pawsitive.classes.Pet;
import com.example.pawsitive.R;
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class AddEditPet extends AppCompatActivity {
    private HashMap<String, Object> petData;
    private FirebaseFirestore fStore;
    private boolean isEdit = false;
    private String editingPetName;

    Button UploadImageButton, SaveButton, BackButton;
    ImageView UploadImageView;
    TextView Name, Age, Weight, Notes;
    CheckBox Neutered, Microchipped, Friendly, CanBeLeftAlone, HouseTrained;
    private boolean neutered, microchipped, friendly, canBeLeftAlone, houseTrained;
    Spinner spinnerNumberOfWalksSelector, spinnerFeedingConditionerSelector,
            spinnerEnergyLevelSelector, spinnerGenderSelector, spinnerTypeSelector;
    private String typeSelection, genderSelection, energyLevelSelection, feedingConditionSelection,
            numberOfWalksSelection, name, additionalNotes, imageStr;
    private int age, weight;
    private byte[] imageInBase64;
    private boolean imageClicked = false;
    private FirebaseAuth auth;
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
                imageClicked = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String petName = intent.getStringExtra("PetName");
        System.out.println(petName);
        editMode(petName);
        System.out.println(isEdit);

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
        BackButton = findViewById(R.id.BackButton);

        Name = findViewById(R.id.PetNameInput);
        Age = findViewById(R.id.PetAgeInput);
        Weight = findViewById(R.id.PetSizeInput);
        Notes = findViewById(R.id.AdditionalNotes);

        Neutered = findViewById(R.id.NeuteredCheck);
        Microchipped = findViewById(R.id.MicrochippedCheck);
        Friendly = findViewById(R.id.FriendlyCheck);
        CanBeLeftAlone = findViewById(R.id.LeftAloneCheck);
        HouseTrained = findViewById(R.id.HouseTrainedCheck);

        if(isEdit){
            try {
                auth = FirebaseAuth.getInstance();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference petData = db.collection("Users")
                        .document(auth.getCurrentUser().getEmail())
                        .collection("Pets").document(editingPetName);

                petData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        spinnerTypeSelector.setSelection(adapter.getPosition(documentSnapshot.getString("Type")));
                        spinnerGenderSelector.setSelection(adapter2.getPosition(documentSnapshot.getString("Gender")));
                        spinnerEnergyLevelSelector.setSelection(adapter3.getPosition(documentSnapshot.getString("Energy Level")));
                        spinnerFeedingConditionerSelector.setSelection(adapter4.getPosition(documentSnapshot.getString("Feeding Condition")));
                        spinnerNumberOfWalksSelector.setSelection(adapter5.getPosition(documentSnapshot.getString("Number Of Walks")));

                        String image64 = documentSnapshot.getString("Image");
                        imageStr = image64;
                        byte[] decodedString = Base64.decode(image64, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        //UploadImageView.setBackground(d);
                        UploadImageView.setImageBitmap(decodedByte);
                        UploadImageView.setVisibility(View.VISIBLE);

                        Notes.setText(documentSnapshot.getString("Additional Notes"));
                        Name.setText(editingPetName);

                        Age.setText(documentSnapshot.get("Age").toString());
                        Weight.setText(documentSnapshot.get("Weight").toString());

                        Neutered.setChecked(documentSnapshot.get("Neutered").toString().equals("true"));
                        Microchipped.setChecked(documentSnapshot.get("Microchipped").toString().equals("true"));
                        Friendly.setChecked(documentSnapshot.get("Friendly").toString().equals("true"));
                        CanBeLeftAlone.setChecked(documentSnapshot.get("Can Be Left Alone").toString().equals("true"));
                        HouseTrained.setChecked(documentSnapshot.get("House Trained").toString().equals("true"));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Failed to fetch pet data from Firebase: " + e.getMessage());
                    }
                });
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    typeSelection = spinnerTypeSelector.getSelectedItem().toString();
                    genderSelection = spinnerGenderSelector.getSelectedItem().toString();
                    energyLevelSelection = spinnerEnergyLevelSelector.getSelectedItem().toString();
                    feedingConditionSelection = spinnerFeedingConditionerSelector.getSelectedItem().toString();
                    numberOfWalksSelection = spinnerNumberOfWalksSelector.getSelectedItem().toString();

                    //imageInBase64

                    if(typeSelection.equals("Select Type") || genderSelection.equals("Select Gender") || energyLevelSelection.equals("Select Energy Level") ||
                            feedingConditionSelection.equals("Select Feeding Condition") || numberOfWalksSelection.equals("Select Number Of Walks") ||
                            Name.getText().toString().isEmpty() || Age.getText().toString().isEmpty() || Weight.getText().toString().isEmpty()){
                        Toast.makeText(AddEditPet.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(imageStr.isEmpty()){
                        Toast.makeText(AddEditPet.this, "Please upload image", Toast.LENGTH_SHORT).show();
                        return;
                    }

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
                            additionalNotes, age, weight, neutered, microchipped, friendly, canBeLeftAlone, houseTrained, imageStr);

                    petData = new HashMap<>();
                    petData.put("Name", name);
                    petData.put("Type", typeSelection);
                    petData.put("Energy Level", energyLevelSelection);
                    petData.put("Feeding Condition", feedingConditionSelection);
                    petData.put("Number Of Walks", numberOfWalksSelection);
                    petData.put("Age", age);
                    petData.put("Weight", weight);
                    petData.put("Neutered", neutered);
                    petData.put("Microchipped", microchipped);
                    petData.put("Friendly", friendly);
                    petData.put("Can Be Left Alone", canBeLeftAlone);
                    petData.put("House Trained", houseTrained);
                    petData.put("Image", imageStr);
                    petData.put("Additional Notes", additionalNotes);
                    petData.put("Gender", genderSelection);

                    System.out.println(auth.getCurrentUser().getEmail());
                    fStore = FirebaseFirestore.getInstance();
                    fStore.collection("Users").document(auth.getCurrentUser().getEmail())
                            .collection("Pets").document(name).set(petData)
                            .addOnCompleteListener(AddEditPet.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(AddEditPet.this, "Save successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MyPets.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(AddEditPet.this, "Save failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        });

        UploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Toast.makeText(AddEditPet.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    UploadImageView.buildDrawingCache();
                    Bitmap photo = UploadImageView.getDrawingCache();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    imageInBase64 = bos.toByteArray();
                    imageStr = Base64.encodeToString(imageInBase64, Base64.DEFAULT);
                }

            }
        });

        BackButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(AddEditPet.this, HomePage.class);
                startActivity(homePage);
            }
        });
    }

    public void editMode(String petName){
        isEdit = true;
        editingPetName = petName;
    }

}