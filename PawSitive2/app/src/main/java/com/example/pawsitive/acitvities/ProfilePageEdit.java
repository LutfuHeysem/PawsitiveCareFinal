package com.example.pawsitive.acitvities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pawsitive.R;

public class ProfilePageEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView textView11,textView12,textView13,textView14,textView15,textView16,textView17;
        TextView textView21,textView22,textView23,textView24,textView25,textView26,textView27;
        TextView textView31,textView32,textView33,textView34,textView35,textView36,textView37;
        TextView textView41,textView42,textView43,textView44,textView45,textView46,textView47;
        TextView textView51,textView52,textView53,textView54,textView55,textView56,textView57;

        textView11 =  findViewById(R.id.editable1_1);textView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView11.setBackgroundColor(Color.RED);
            }
        });
        textView12 =  findViewById(R.id.editable1_2);textView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView12.setBackgroundColor(Color.RED);
            }
        });
        textView13 =  findViewById(R.id.editable1_3);textView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView13.setBackgroundColor(Color.RED);
            }
        });
        textView14 =  findViewById(R.id.editable1_4);textView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView14.setBackgroundColor(Color.RED);
            }
        });
        textView15 =  findViewById(R.id.editable1_5);textView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView15.setBackgroundColor(Color.RED);
            }
        });
        textView16 =  findViewById(R.id.editable1_6);textView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView16.setBackgroundColor(Color.RED);
            }
        });
        textView17 =  findViewById(R.id.editable1_7);textView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView17.setBackgroundColor(Color.RED);
            }
        });

        textView21 =  findViewById(R.id.editable2_1);textView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView21.setBackgroundColor(Color.RED);
            }
        });
        textView22 =  findViewById(R.id.editable2_2);textView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView22.setBackgroundColor(Color.RED);
            }
        });
        textView23 =  findViewById(R.id.editable2_3);textView23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView23.setBackgroundColor(Color.RED);
            }
        });
        textView24 =  findViewById(R.id.editable2_4);textView24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView24.setBackgroundColor(Color.RED);
            }
        });
        textView25 =  findViewById(R.id.editable2_5);textView25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView25.setBackgroundColor(Color.RED);
            }
        });
        textView26 =  findViewById(R.id.editable2_6);textView26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView26.setBackgroundColor(Color.RED);
            }
        });
        textView27 =  findViewById(R.id.editable2_7);textView27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView27.setBackgroundColor(Color.RED);
            }
        });

        textView31 =  findViewById(R.id.editable3_1);textView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView31.setBackgroundColor(Color.RED);
            }
        });
        textView32 =  findViewById(R.id.editable3_2);textView32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView32.setBackgroundColor(Color.RED);
            }
        });
        textView33 =  findViewById(R.id.editable3_3);textView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView33.setBackgroundColor(Color.RED);
            }
        });
        textView34 =  findViewById(R.id.editable3_4);textView24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView34.setBackgroundColor(Color.RED);
            }
        });
        textView35 =  findViewById(R.id.editable3_5);textView25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView35.setBackgroundColor(Color.RED);
            }
        });
        textView36 =  findViewById(R.id.editable3_6);textView36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView36.setBackgroundColor(Color.RED);
            }
        });
        textView37 =  findViewById(R.id.editable3_7);textView37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView37.setBackgroundColor(Color.RED);
            }
        });

        textView41 =  findViewById(R.id.editable4_1);textView41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView41.setBackgroundColor(Color.RED);
            }
        });
        textView42 =  findViewById(R.id.editable4_2);textView42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView42.setBackgroundColor(Color.RED);
            }
        });
        textView43 =  findViewById(R.id.editable4_3);textView43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView43.setBackgroundColor(Color.RED);
            }
        });
        textView44 =  findViewById(R.id.editable4_4);textView44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView44.setBackgroundColor(Color.RED);
            }
        });
        textView45 =  findViewById(R.id.editable4_5);textView45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView45.setBackgroundColor(Color.RED);
            }
        });
        textView46 =  findViewById(R.id.editable4_6);textView46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView46.setBackgroundColor(Color.RED);
            }
        });
        textView47 =  findViewById(R.id.editable4_7);textView47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView47.setBackgroundColor(Color.RED);
            }
        });

        textView51 =  findViewById(R.id.editable5_1);textView51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView51.setBackgroundColor(Color.RED);
            }
        });
        textView52 =  findViewById(R.id.editable5_2);textView52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView52.setBackgroundColor(Color.RED);
            }
        });
        textView53 =  findViewById(R.id.editable5_3);textView53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView53.setBackgroundColor(Color.RED);
            }
        });
        textView54 =  findViewById(R.id.editable5_4);textView54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView54.setBackgroundColor(Color.RED);
            }
        });
        textView55 =  findViewById(R.id.editable5_5);textView55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView55.setBackgroundColor(Color.RED);
            }
        });
        textView56 =  findViewById(R.id.editable5_6);textView56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView56.setBackgroundColor(Color.RED);
            }
        });
        textView57 =  findViewById(R.id.editable5_7);textView57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView57.setBackgroundColor(Color.RED);
            }
        });


    }
}