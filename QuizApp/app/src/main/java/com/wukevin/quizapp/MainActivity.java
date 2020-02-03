package com.wukevin.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button submitButton;
    EditText submitButton_EditText;
    TextView submitButton_TextView;
    protected void onCreate(Bundle savedInstanceState) {
        //setup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //actual kevin code
        submitButton = findViewById(R.id.clickButton);
        submitButton_EditText = findViewById(R.id.responseEditText);
        submitButton_TextView = findViewById(R.id.textBox);
        //listeners
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(submitButton_EditText.getText());
                submitButton_TextView.setText(submitButton_TextView.getText().toString() + " " + submitButton_EditText.getText().toString());
                Log.i("submitButton", "yeeeeeeeeeee");
            }
        });
        submitButton_EditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false)
                {
                    if(submitButton_EditText.getText().toString().equals("TJ"))
                    {
                        submitButton_TextView.setText("TJ is Great!!");
                        submitButton_EditText.setText("");
                        submitButton_EditText.setHint("thas a good name lol");
                    }
                }
            }
        });
    }
}
