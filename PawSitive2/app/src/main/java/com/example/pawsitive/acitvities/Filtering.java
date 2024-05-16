package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pawsitive.R;
import com.example.pawsitive.classes.Job;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Filtering extends AppCompatActivity {

    private String loc, lang, minCheck, maxCheck, expCheck, gender;
    private int min, max, exp;
    private Button save;
    private CheckBox male, female, other;
    private EditText editLoc, editMin, editMax, editExp, editLang;
    private FirebaseAuth mAuth;
    private static List<Job> originalJobList = new ArrayList<>();
    private static List<Job> jobAL = new ArrayList<>();
    private FirebaseFirestore fStore;

    public static List<Job> getFilteredJobs() {
        return jobAL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtering);

        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        editLoc = findViewById(R.id.editTextText3);
        editMin = findViewById(R.id.editTextText4);
        editMax = findViewById(R.id.editTextText6);
        editExp = findViewById(R.id.editTextText7);
        editLang = findViewById(R.id.editTextText8);

        save = findViewById(R.id.button8);
        male = findViewById(R.id.checkBox2);
        female = findViewById(R.id.checkBox3);
        other = findViewById(R.id.checkBox4);

        save.setOnClickListener(v -> {
            if (originalJobList.isEmpty()) {
                originalJobList.addAll(jobAL);  // Save a copy of the original unfiltered job list
            }

            jobAL.clear();
            jobAL.addAll(originalJobList);  // Reset jobAL to the original list

            minCheck = editMin.getText().toString();
            maxCheck = editMax.getText().toString();
            expCheck = editExp.getText().toString();
            gender = "";

            loc = editLoc.getText().toString().toUpperCase();
            lang = editLang.getText().toString().toUpperCase();

            if (!expCheck.isEmpty()) {
                exp = Integer.parseInt(expCheck);
            }

            filterLocation();
            filterPrice();
            filterExperience();
            filterGender();
            filterLanguage();
            filterGeneral();

            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            intent.putExtra("isFiltered", "filtered");
            startActivity(intent);
        });
    }

    protected void filterLocation() {
        if (!loc.isEmpty()) {
            jobAL = jobAL.stream().filter(str -> str.getLocation().equals(loc)).collect(Collectors.toList());
        }
    }

    protected void filterPrice() {
        if (minCheck.isEmpty())
            min = 0;
        if(maxCheck.isEmpty())
            max = Integer.MAX_VALUE;
        else if(!minCheck.isEmpty()){
            min = Integer.parseInt(minCheck);
            max = Integer.parseInt(maxCheck);
            jobAL = jobAL.stream()
                    .filter(str -> Integer.parseInt(str.getPrice()) >= min && Integer.parseInt(str.getPrice()) <= max)
                    .collect(Collectors.toList());
        }
    }

    protected void filterExperience() {
        if (!expCheck.isEmpty()) {
            jobAL = jobAL.stream().filter(str -> Integer.parseInt(str.getExperienceLevel()) >= exp).collect(Collectors.toList());
        }
    }

    protected void filterLanguage() {
        if (!lang.isEmpty()) {
            jobAL = jobAL.stream().filter(str -> str.getSpokenLanguages().contains(lang)).collect(Collectors.toList());
        }
    }

    protected void filterGender() {
        List<String> genderList = new ArrayList<>();
        if (male.isChecked()) {
            genderList.add("MALE");
        }
        if (female.isChecked()) {
            genderList.add("FEMALE");
        }
        if (other.isChecked()) {
            genderList.add("OTHER");
        }

        if (!genderList.isEmpty()) {
            jobAL = jobAL.stream().filter(str -> genderList.contains(str.getGender())).collect(Collectors.toList());
        }
    }

    protected void filterGeneral() {
        jobAL.sort(Comparator.comparing(Job::getRating).reversed()
                .thenComparing(Job::getExperienceLevel).reversed());
    }

    public static void setJobsArrayList(ArrayList<Job> jobs) {
        originalJobList.clear();
        originalJobList.addAll(jobs);
        jobAL.clear();
        jobAL.addAll(jobs);
    }
}
