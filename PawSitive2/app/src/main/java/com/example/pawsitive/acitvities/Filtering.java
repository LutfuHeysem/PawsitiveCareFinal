package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pawsitive.R;
import com.example.pawsitive.acitvities.ProfilePage1;
import com.example.pawsitive.classes.Job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import java.util.stream.Collectors;

public class Filtering extends AppCompatActivity {

    private String loc, lang,minCheck, maxCheck, expCheck, gender;
    private int min, max, exp;
    private Button save;
    private CheckBox male, female, other;
    private EditText editLoc, editMin, editMax, editExp, editLang;
    private static List<Job> jobAL;

    //private List <Job> noSorted = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filtering);

        editLoc = findViewById(R.id.editTextText3);
        editMin = findViewById(R.id.editTextText4);
        editMax = findViewById(R.id.editTextText6);
        editExp = findViewById(R.id.editTextText7);
        editLang = findViewById(R.id.editTextText8);

        save = findViewById(R.id.button8);
        male = findViewById(R.id.checkBox2);
        female = findViewById(R.id.checkBox3);
        other = findViewById(R.id.checkBox4);

        System.out.println("twmp önce");
        Job temp = new Job();

        System.out.println(temp.getJobList().size());
        System.out.println(jobAL.size());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                minCheck = editMin.getText().toString();
                maxCheck = editMax.getText().toString();
                expCheck = editExp.getText().toString();
                gender = "";

                loc = editLoc.getText().toString();
                lang = editLang.getText().toString().toUpperCase();

                if(!expCheck.isEmpty())
                    exp = Integer.parseInt(expCheck);

//                FilterLocation();
//                FilterPrice();
//                FilterExperience();
//                FilterGender();
//                FilterLanguage();
                System.out.println("for öncesi");
                for(int i = 0; i < jobAL.size(); i++)
                {
                    System.out.println(jobAL.get(i));
                }
                startActivity(new Intent(Filtering.this, ResetPassword.class));
            }
        });
    }
    protected void FilterLocation(){
        if(loc.isEmpty()) {return;}
        jobAL = jobAL.stream().filter(str -> !str.getLocation().equals(loc))
                .collect(Collectors.toList());
    }

    protected void FilterPrice(){
        if(min > max) {return;}
        if(minCheck.isEmpty() && maxCheck.isEmpty()){
            min = 0;
            max = Integer.MAX_VALUE;
        }
        else
        {
            min = Integer.parseInt(minCheck);
            max = Integer.parseInt(maxCheck);
            jobAL = jobAL.stream().filter(str -> Integer.parseInt(str.getPrice()) >= min && Integer.parseInt(str.getPrice()) <= max)
                    .collect(Collectors.toList());
        }
    }
    protected void FilterExperience(){
        if(expCheck.isEmpty() && exp < 0) { exp = 0;}
        else
        {
            jobAL = jobAL.stream().filter(str -> Integer.parseInt(str.getExperienceLevel()) >= exp)
                    .collect(Collectors.toList());
        }
    }

    protected void FilterLanguage(){
        if(lang.isEmpty()) { lang = "";}
        else
        {
            jobAL = jobAL.stream().filter(str -> lang.contains(str.getSpokenLanguages()))
                    .collect(Collectors.toList());
        }
    }

    protected void FilterGender(){
        if(male.isChecked()) { gender += "MALE";}
        if(female.isChecked()) { gender += "FEMALE";}
        if(other.isChecked()) { gender += "OTHER";}

        jobAL = jobAL.stream().filter(str -> gender.contains(str.getGender()))
                .collect(Collectors.toList());
    }

    protected void FilterRate() {
        Collections.sort(jobAL, Comparator.comparing(Job::getRating));
    }

}

