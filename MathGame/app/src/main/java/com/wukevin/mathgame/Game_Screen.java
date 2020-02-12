package com.wukevin.mathgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;

public class Game_Screen extends AppCompatActivity {

    TextView TimerText;
    TextView ScoreText;
    TextView GamePrompt;
    EditText GameInput;
    View BackgroundView;
    String answer;
    int currentScore;
    boolean gameOver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__screen);

        TimerText = findViewById(R.id.TimerText);
        ScoreText = findViewById(R.id.ScoreText);
        GamePrompt = findViewById(R.id.GamePrompt);
        GameInput = findViewById(R.id.GameInput);
        BackgroundView = findViewById(R.id.BackgroundView);
        BackgroundView.setTranslationZ(-2);
        GameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(gameOver == false) {
                    System.out.println("Text Changed! " + s.toString());
                    if (s.toString().equals(answer)) {
                        incrementScore();
                        makeNewProblem();
                        GameInput.setText("");
                        BackgroundView.setBackgroundColor(Color.GREEN);
                    }
                    else if(s.toString().length() >= answer.length()){
                        BackgroundView.setBackgroundColor(Color.RED);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int)(millisUntilFinished / 1000);
                int minutesLeft = (int)(secondsLeft / 60);
                secondsLeft = secondsLeft % 60;
                String secondsLeftString;
                if(secondsLeft < 10){
                    secondsLeftString = "0" + secondsLeft;
                }
                else
                {
                    secondsLeftString = secondsLeft + "";
                }
                String minutesLeftString;
                if(minutesLeft < 10){
                    minutesLeftString = "0" + minutesLeft;
                }
                else
                {
                    minutesLeftString = minutesLeft + "";
                }
                TimerText.setText("Time Left: " + minutesLeftString + ":" + secondsLeftString);
            }

            @Override
            public void onFinish() {
                GameInput.setTextIsSelectable(false);
                GameInput.setInputType(0);
                GamePrompt.setText("Game Over!");
                System.out.println("GAME OVER");
                gameOver = true;
            }
        }.start();
        new CountDownTimer(33000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                ColorDrawable drawable = (ColorDrawable) BackgroundView.getBackground();
                String hexColor = String.format("#%06X", (0xFFFFFF & drawable.getColor()));
                int percent = 1;
                int initialRComp = Integer.parseInt(hexColor.substring(1,3),16);
                int initialGComp = Integer.parseInt(hexColor.substring(3,5),16);
                int initialBComp = Integer.parseInt(hexColor.substring(5,7),16);
                initialRComp = initialRComp * (100 - percent) / 100;
                initialGComp = initialGComp * (100 - percent) / 100;
                initialBComp = initialBComp * (100 - percent) / 100;
                initialRComp = (initialRComp<255)?initialRComp:255;
                initialGComp = (initialGComp<255)?initialGComp:255;
                initialBComp = (initialBComp<255)?initialBComp:255;
                String rStringComp = Integer.toHexString(initialRComp) + "";
                String gStringComp = Integer.toHexString(initialGComp) + "";
                String bStringComp = Integer.toHexString(initialBComp) + "";
                String RR = ((rStringComp.length()==1)?"0"+rStringComp:rStringComp);
                String GG = ((gStringComp.length()==1)?"0"+gStringComp:gStringComp);
                String BB = ((bStringComp.length()==1)?"0"+bStringComp:bStringComp);
                hexColor = "#"+RR+GG+BB;
                BackgroundView.setBackgroundColor(Color.parseColor(hexColor));
            }
            @Override
            public void onFinish() {
            }
        }.start();

        gameOver = false;
        makeNewProblem();
    }
    public void makeNewProblem(){
        int firstNum = (int) (Math.random() * 25);
        int secondNum = (int) (Math.random() * 25);
        int operator = (int) (Math.random() * 3);
        String displayText = "";
        int tempAns = -42;
        switch (operator) {
            case 0:
                displayText = "What is " + firstNum + " " + getResources().getStringArray(R.array.operators_array)[operator] + " " + secondNum + "?";
                tempAns = firstNum + secondNum;
                break;

            case 1:
                displayText = "What is |" + firstNum + " " + getResources().getStringArray(R.array.operators_array)[operator] + " " + secondNum + "|?";
                tempAns = Math.abs(firstNum - secondNum);
                break;

            case 2:
                displayText = "What is " + firstNum +  " " + getResources().getStringArray(R.array.operators_array)[operator] + " " + secondNum + "?";
                tempAns = firstNum * secondNum;
                break;
        }
        answer = tempAns + "";
        System.out.println("First: " + firstNum + ", Second: " + secondNum + ", Answer: " + answer);
        GamePrompt.setText(displayText);
        BackgroundView.setBackgroundColor(Color.WHITE);
    }
    public void incrementScore(){
        currentScore++;
        ScoreText.setText("Your score is " + currentScore);
    }
}
