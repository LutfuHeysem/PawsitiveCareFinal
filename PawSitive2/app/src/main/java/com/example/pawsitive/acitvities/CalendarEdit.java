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

public class CalendarEdit extends AppCompatActivity {

    static boolean[][] isRed =
            {{false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar_edit);
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
            public void onClick(View v)
            {
                if(isRed[0][0])
                {
                    textView11.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView11.setBackgroundColor(Color.WHITE);
                }
                isRed[0][0] = !isRed[0][0];
            }
        });
        textView12 =  findViewById(R.id.editable1_2);textView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (isRed[0][1]) {
                    textView12.setBackgroundColor(Color.RED);
                } else {
                    textView12.setBackgroundColor(Color.WHITE);
                }
                isRed[0][1] = !isRed[0][1];
            }
        });
        textView13 =  findViewById(R.id.editable1_3);textView13.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    if(isRed[0][2]){
                        textView13.setBackgroundColor(Color.RED);
                    }else {
                        textView13.setBackgroundColor(Color.WHITE);
                    }
                    isRed[0][2] = !isRed[0][2];
            }
        });
        textView14 =  findViewById(R.id.editable1_4);textView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[0][3])
                {
                    textView14.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView14.setBackgroundColor(Color.WHITE);
                }
                isRed[0][3] = !isRed[0][3];
            }
        });
        textView15 =  findViewById(R.id.editable1_5);textView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[0][4])
                {
                    textView15.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView15.setBackgroundColor(Color.WHITE);
                }
                isRed[0][4] = !isRed[0][4];
            }
        });
        textView16 =  findViewById(R.id.editable1_6);textView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[0][5])
                {
                    textView16.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView16.setBackgroundColor(Color.WHITE);
                }
                isRed[0][5] = !isRed[0][5];
            }
        });
        textView17 =  findViewById(R.id.editable1_7);textView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[0][6])
                {
                    textView17.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView17.setBackgroundColor(Color.WHITE);
                }
                isRed[0][6] = !isRed[0][6];
            }
        });

        textView21 =  findViewById(R.id.editable2_1);textView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[1][0])
                {
                    textView21.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView21.setBackgroundColor(Color.WHITE);
                }
                isRed[1][0] = !isRed[1][0];
            }
        });
        textView22 =  findViewById(R.id.editable2_2);textView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[1][1])
                {
                    textView22.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView22.setBackgroundColor(Color.WHITE);
                }
                isRed[1][1] = !isRed[1][1];
            }
        });
        textView23 =  findViewById(R.id.editable2_3);textView23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[1][2])
                {
                    textView23.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView23.setBackgroundColor(Color.WHITE);
                }
                isRed[1][2] = !isRed[1][2];
            }
        });
        textView24 =  findViewById(R.id.editable2_4);textView24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[1][3])
                {
                    textView24.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView24.setBackgroundColor(Color.WHITE);
                }
                isRed[1][3] = !isRed[1][3];
            }
        });
        textView25 =  findViewById(R.id.editable2_5);textView25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[1][4])
                {
                    textView25.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView25.setBackgroundColor(Color.WHITE);
                }
                isRed[1][4] = !isRed[1][4];
            }
        });
        textView26 =  findViewById(R.id.editable2_6);textView26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[1][5])
                {
                    textView26.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView26.setBackgroundColor(Color.WHITE);
                }
                isRed[1][5] = !isRed[1][5];
            }
        });
        textView27 =  findViewById(R.id.editable2_7);textView27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[1][6])
                {
                    textView27.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView27.setBackgroundColor(Color.WHITE);
                }
                isRed[1][6] = !isRed[1][6];
            }
        });

        textView31 =  findViewById(R.id.editable3_1);textView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isRed[2][0])
                {
                    textView31.setBackgroundColor(Color.RED);
                }
                else
                {
                    textView31.setBackgroundColor(Color.WHITE);
                }
                isRed[2][0] = !isRed[2][0];
            }
        });
        textView32 =  findViewById(R.id.editable3_2);textView32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView32);
            }
        });
        textView33 =  findViewById(R.id.editable3_3);textView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView33);
            }
        });
        textView34 =  findViewById(R.id.editable3_4);textView24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView34);
            }
        });
        textView35 =  findViewById(R.id.editable3_5);textView25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView35);
            }
        });
        textView36 =  findViewById(R.id.editable3_6);textView36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView36);
            }
        });
        textView37 =  findViewById(R.id.editable3_7);textView37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView37);
            }
        });

        textView41 =  findViewById(R.id.editable4_1);textView41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView41);
            }
        });
        textView42 =  findViewById(R.id.editable4_2);textView42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView42);
            }
        });
        textView43 =  findViewById(R.id.editable4_3);textView43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView43);
            }
        });
        textView44 =  findViewById(R.id.editable4_4);textView44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView44);
            }
        });
        textView45 =  findViewById(R.id.editable4_5);textView45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView45);
            }
        });
        textView46 =  findViewById(R.id.editable4_6);textView46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView46);
            }
        });
        textView47 =  findViewById(R.id.editable4_7);textView47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView47);
            }
        });

        textView51 =  findViewById(R.id.editable5_1);textView51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView51);
            }
        });
        textView52 =  findViewById(R.id.editable5_2);textView52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView52);
            }
        });
        textView53 =  findViewById(R.id.editable5_3);textView53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView53);
            }
        });
        textView54 =  findViewById(R.id.editable5_4);textView54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView54);
            }
        });
        textView55 =  findViewById(R.id.editable5_5);textView55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView55);
            }
        });
        textView56 =  findViewById(R.id.editable5_6);textView56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView56);
            }
        });
        textView57 =  findViewById(R.id.editable5_7);textView57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(textView57);
            }
        });

        TextView[][] redBoxesArray =
                {{textView11,textView12,textView13,textView14,textView15,textView16,textView17},
                        {textView21,textView22,textView23,textView24,textView25,textView26,textView27},
                        {textView31,textView32,textView33,textView34,textView35,textView36,textView37},
                        {textView41,textView42,textView43,textView44,textView45,textView46,textView47},
                        {textView51,textView52,textView53,textView54,textView55,textView56,textView57}
                };
        for(int i = 0; i < redBoxesArray.length; i++)
        {
            for (int j = 0; j < redBoxesArray[0].length; j++) {
                redBoxesArray[i][j].setText(" ");
            }
        }
    }

}