package com.wukevin.quizapptoo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int amountOfSleeps;
    Button sleepButton;
    ImageView vegetalImage;
    TextView titleText;
    String something;
    EditText somethingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sleepButton = findViewById(R.id.Sleep_Button);
        vegetalImage = findViewById(R.id.Vegetal_Image);
        titleText = findViewById(R.id.Title);
        somethingText = findViewById(R.id.Something_Text);
        amountOfSleeps = 0;

        sleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    amountOfSleeps++;
                    titleText.setText("i have " + amountOfSleeps + " " + something);
                    vegetalImage.requestLayout();
                    vegetalImage.getLayoutParams().height = vegetalImage.getLayoutParams().height + 10;
                    System.out.println("I did a " + something + ". The image height is at " + vegetalImage.getLayoutParams().height);
            }
        });

        somethingText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() == null)
                {
                    something = "sleep";
                    titleText.setText("i have " + amountOfSleeps + " " + something);
                    sleepButton.setText(something);
                }
                else {
                    something = s.toString();
                    titleText.setText("i have " + amountOfSleeps + " " + something);
                    sleepButton.setText(something);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
    }
}
