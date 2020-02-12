package com.wukevin.fourbuttoncounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ColorStateListInflaterCompat;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }
}
