package com.wukevin.fourbuttoncounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ColorStateListInflaterCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView InstructionText;
    Button ButtonTopLeft;
    Button ButtonTopRight;
    Button ButtonBottomLeft;
    Button ButtonBottomRight;

    SharedPreferences prefObj;
    SharedPreferences.Editor prefEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefObj = getApplicationContext().getSharedPreferences("mathGamePref", 0);
        prefEditor = prefObj.edit();
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button temp = (Button) v;
                int newNumber = Integer.parseInt(temp.getText().toString()) + 1;
                temp.setText(newNumber + "");
            }
        };
        InstructionText = findViewById(R.id.InstructionText);
        ButtonTopLeft = findViewById(R.id.ButtonTopLeft);
        ButtonTopRight = findViewById(R.id.ButtonTopRight);
        ButtonBottomLeft = findViewById(R.id.ButtonBottomLeft);
        ButtonBottomRight = findViewById(R.id.ButtonBottomRight);

        ButtonTopLeft.setOnClickListener(buttonClickListener);
        ButtonTopRight.setOnClickListener(buttonClickListener);
        ButtonBottomLeft.setOnClickListener(buttonClickListener);
        ButtonBottomRight.setOnClickListener(buttonClickListener);

        String temp = prefObj.getString("top_left", null);
        System.out.println(temp);
        if(temp != null)
        {
            ButtonTopLeft.setText(temp);
        }
        temp = prefObj.getString("top_right", null);
        if(temp != null)
        {
            ButtonTopRight.setText(temp);
        }
        temp = prefObj.getString("bottom_left", null);
        if(temp != null)
        {
            ButtonBottomLeft.setText(temp);
        }
        temp = prefObj.getString("bottom_right", null);
        if(temp != null)
        {
            ButtonBottomRight.setText(temp);
        }
    }

    protected void onDestroy(){
        prefEditor.putString("top_left", ButtonTopLeft.getText().toString());
        prefEditor.putString("top_right", ButtonTopRight.getText().toString());
        prefEditor.putString("bottom_left", ButtonBottomLeft.getText().toString());
        prefEditor.putString("bottom_right", ButtonBottomRight.getText().toString());
        prefEditor.commit();

        super.onDestroy();
    }
}
