package com.wukevin.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;

public class Game_Screen extends AppCompatActivity {

    TextView TimerText;
    TextView ScoreText;
    TextView GamePrompt;
    EditText GameInput;

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
                displayText = "What is " + firstNum + " + " + secondNum + "?";
                tempAns = firstNum + secondNum;
                break;

            case 1:
                displayText = "What is |" + firstNum + " - " + secondNum + "|?";
                tempAns = Math.abs(firstNum - secondNum);
                break;

            case 2:
                displayText = "What is " + firstNum + " * " + secondNum + "?";
                tempAns = firstNum * secondNum;
                break;
        }
        answer = tempAns + "";
        System.out.println("First: " + firstNum + ", Second: " + secondNum + ", Answer: " + answer);
        GamePrompt.setText(displayText);
    }
    public void incrementScore(){
        currentScore++;
        ScoreText.setText("Your score is " + currentScore);
    }
}
