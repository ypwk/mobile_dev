package com.wukevin.lab04_viewdemonstrations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Class[] AllClasses;
    Button StartButton01;
    Button StartButton02;
    Button StartButton03;
    Button StartButton04;
    Button StartButton05;
    Button StartButton06;
    Button StartButton07;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartButton01 = findViewById(R.id.ViewButton01);
        StartButton02 = findViewById(R.id.ViewButton02);
        StartButton03 = findViewById(R.id.ViewButton03);
        StartButton04 = findViewById(R.id.ViewButton04);
        StartButton05 = findViewById(R.id.ViewButton05);
        StartButton06 = findViewById(R.id.ViewButton06);
        StartButton07 = findViewById(R.id.ViewButton07);
        StartButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass(v, DemoActivity01.class);
            }
        });
        StartButton02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass(v, DemoActivity02.class);
            }
        });
        StartButton03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass(v, DemoActivity03.class);
            }
        });
        StartButton04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass(v, DemoActivity04.class);
            }
        });
        StartButton05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass(v, DemoActivity05.class);
            }
        });
        StartButton06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass(v, DemoActivity06.class);
            }
        });
        StartButton07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass(v, DemoActivity07.class);
            }
        });
    }

    public void StartClass(View v, Class c)
    {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
