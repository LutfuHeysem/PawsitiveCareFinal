package com.example.pawsitive;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FilterOption extends AppCompatActivity {

    private String loc, lang, maleS, femaleS, otherS;
    private int min, max, exp;
    private Button save;
    private CheckBox male, female, other;
    private EditText editLoc, editMin, editMax, editExp, editLang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter_option);

        editLoc = findViewById(R.id.editTextText3);
        editMin = findViewById(R.id.editTextText4);
        editMax = findViewById(R.id.editTextText6);
        editExp = findViewById(R.id.editTextText7);
        editLang = findViewById(R.id.editTextText8);

        save = findViewById(R.id.button8);
        male = findViewById(R.id.checkBox2);
        female = findViewById(R.id.checkBox3);
        other = findViewById(R.id.checkBox4);

    }
}