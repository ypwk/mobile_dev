package com.wukevin.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameScreen extends AppCompatActivity {
    TableLayout GameTable;
    TextView TimerText;
    TextView FlagText;

    int width = 8;
    int height = 10;
    int numberOfFlags = 10;

    boolean ClickState = false; //false is dig, true is flag
    boolean clicked = false;
    Button[][] gameButtons;
    int[][] data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        GameTable = findViewById(R.id.game_table);
        TimerText = findViewById(R.id.TimerText);
        FlagText = findViewById(R.id.FlagText);

        gameButtons = new Button[width][height];
        for(int a = 0; a < width; a++)
        {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            /* Create a Button to be the row-content. */
            for(int b = 0; b < height; b++) {
                gameButtons[a][b] = new Button(this);
                gameButtons[a][b].setText("g");
                gameButtons[a][b].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                gameButtons[a][b].setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                gameButtons[a][b].setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                tr.addView(gameButtons[a][b]);
            }
            GameTable.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
        /*
            10: empty
            9: has mine
            0: user flagged
            1 - 8: user clicked
         */
        data = new int[width][height];
        for(int a = 0; a < width; a++)
        {
            for(int b = 0; b < height; b++)
            {
                gameButtons[a][b].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int posR = 0;
                        int posC = 0;
                        for(int a = 0; a < width; a++)
                        {
                            for(int b = 0; b < height; b++)
                            {
                                if(gameButtons[a][b].equals(v))
                                {
                                    posR = a;
                                    posC = b;
                                }
                            }
                        }
                        if(!ClickState)
                        {
                            if(!clicked && data[posR][posC] == 9)
                            {
                                boolean found = false;
                                while(!found)
                                {
                                    int tempR = (int)(Math.random() * width);
                                    int tempC = (int)(Math.random() * height);
                                    if(data[tempR][tempC] == 10)
                                    {
                                        data[tempR][tempC] = 9;
                                        found = true;
                                    }
                                }
                                data[posR][posC] = 10;
                            }
                            if(data[posR][posC] == 9)
                            {
                                GameOver();
                            }
                            else
                            {
                                data[posR][posC] = 2;
                            }
                            System.out.println("R: " + posR + ", C: " + posC + " was clicked.");
                        }

                    }
                });
                data[a][b] = 10;
            }
        }
        int tempNumberOfFlags = numberOfFlags;
        while(tempNumberOfFlags > 0)
        {
            int tempR = (int)(Math.random() * width);
            int tempC = (int)(Math.random() * height);
            if(data[tempR][tempC] == 10)
            {
                data[tempR][tempC] = 9;
                tempNumberOfFlags--;
            }
        }
    }

    public void GameOver()
    {
        for(int a = 0; a < width; a++)
        {
            for(int b = 0; b < height; b++)
            {
                gameButtons[a][b].setClickable(false);
                gameButtons[a][b].setBackgroundColor(Color.RED);
            }
        }
        System.out.println("Game Over!!!!");
    }
}
