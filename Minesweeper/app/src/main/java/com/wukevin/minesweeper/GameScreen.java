package com.wukevin.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameScreen extends AppCompatActivity {
    TableLayout GameTable;
    TextView TimerText;
    TextView FlagText;
    Button ResetButton;
    Button ClickStateButton;

    int width = 10;
    int height = 8;
    int numberOfFlags = 10;
    int flagsLeft = 10;

    boolean ClickState = false; //false is dig, true is flag
    boolean clicked = false;
    ImageButton[][] gameButtons;
    int[][] data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        GameTable = findViewById(R.id.game_table);
        TimerText = findViewById(R.id.TimerText);
        FlagText = findViewById(R.id.FlagText);
        ResetButton = findViewById(R.id.ResetButton);
        ClickStateButton = findViewById(R.id.change_state_button);

        NewGame();
        gameButtons = new ImageButton[width][height];
        for(int a = 0; a < width; a++)
        {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screen_width = displayMetrics.widthPixels;
            int buttonSize = (int)(screen_width/height);
            for(int b = 0; b < height; b++) {
                gameButtons[a][b] = new ImageButton(this);
                gameButtons[a][b].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                ViewGroup.LayoutParams params = gameButtons[a][b].getLayoutParams();
                params.height = buttonSize;
                params.width = buttonSize;
                gameButtons[a][b].setLayoutParams(params);
                gameButtons[a][b].setAdjustViewBounds(true);
                gameButtons[a][b].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                gameButtons[a][b].setPadding(4,4,4,4);
                tr.addView(gameButtons[a][b]);
                if(data[a][b] == 9)
                {
                    gameButtons[a][b].setImageResource(R.drawable.mine);
                }
                /*
                    10: empty
                    9: has mine
                    0: user flagged
                    1 - 8: user clicked
                */
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
                            System.out.println("R: " + posR + ", C: " + posC + " was clicked. " + "Data: " + data[posR][posC]);
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
                                clicked = true;
                                drawMines();
                                refactorEmpty();
                                switch(data[posR][posC])
                                {
                                    case 0:
                                        break;
                                    case 1:
                                        gameButtons[posR][posC].setImageResource(R.drawable.one);
                                        break;
                                    case 2:
                                        gameButtons[posR][posC].setImageResource(R.drawable.two);
                                        break;
                                    case 3:
                                        gameButtons[posR][posC].setImageResource(R.drawable.three);
                                        break;
                                    case 4:
                                        gameButtons[posR][posC].setImageResource(R.drawable.four);
                                        break;
                                    case 5:
                                        gameButtons[posR][posC].setImageResource(R.drawable.five);
                                        break;
                                    case 6:
                                        gameButtons[posR][posC].setImageResource(R.drawable.six);
                                        break;
                                    case 7:
                                        gameButtons[posR][posC].setImageResource(R.drawable.seven);
                                        break;
                                    case 8:
                                        gameButtons[posR][posC].setImageResource(R.drawable.eight);
                                        break;
                                }
                            }
                            if(data[posR][posC] == 9)
                            {
                                GameOver();
                            }
                            else
                            {
                                clicked = true;
                                switch (data[posR][posC])
                                {
                                    case 0:
                                        break;
                                    case 1:
                                        gameButtons[posR][posC].setImageResource(R.drawable.one);
                                        break;
                                    case 2:
                                        gameButtons[posR][posC].setImageResource(R.drawable.two);
                                        break;
                                    case 3:
                                        gameButtons[posR][posC].setImageResource(R.drawable.three);
                                        break;
                                    case 4:
                                        gameButtons[posR][posC].setImageResource(R.drawable.four);
                                        break;
                                    case 5:
                                        gameButtons[posR][posC].setImageResource(R.drawable.five);
                                        break;
                                    case 6:
                                        gameButtons[posR][posC].setImageResource(R.drawable.six);
                                        break;
                                    case 7:
                                        gameButtons[posR][posC].setImageResource(R.drawable.seven);
                                        break;
                                    case 8:
                                        gameButtons[posR][posC].setImageResource(R.drawable.eight);
                                        break;
                                }
                                gameButtons[posR][posC].setClickable(false);
                            }
                        }
                        else
                        {
                            data[posR][posC] = 0;
                            gameButtons[posR][posC].setImageResource(R.drawable.flag);
                            flagsLeft--;
                            FlagText.setText("Flags Left: " + flagsLeft);
                        }
                    }
                });
            }
            GameTable.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewGame();
            }
        });
        ClickStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ClickState)
                {
                    ClickState = false;
                    ClickStateButton.setText("dig");
                }
                else
                {
                    ClickState = true;
                    ClickStateButton.setText("flag");
                }
            }
        });
    }

    public void GameOver()
    {
        System.out.println("Game Over!!!!");
        for(int a = 0; a < width; a++)
        {
            for(int b = 0; b < height; b++)
            {
                gameButtons[a][b].setClickable(false);
                gameButtons[a][b].setBackgroundColor(Color.RED);
                if(data[a][b] == 9)
                {
                    gameButtons[a][b].setImageResource(R.drawable.mine);
                }
            }
        }
    }
    public void NewGame()
    {
        data = new int[width][height];
        for(int a = 0; a < width; a++)
        {
            for(int b = 0; b < height; b++)
            {
                data[a][b] = 10;
            }
        }
        int tempNumberOfFlags = numberOfFlags;
        while(tempNumberOfFlags > 0)
        {
            int tempR = (int)(Math.random() * width);
            int tempC = (int)(Math.random() * height);
            if(data[tempR][tempC] == 10) {
                data[tempR][tempC] = 9;
                tempNumberOfFlags--;
                System.out.println("Mines left to place: " + tempNumberOfFlags);
                for (int a = 0; a < width; a++)
                {
                    for(int b = 0; b < height; b++)
                    {
                        System.out.print("[" + data[a][b] + "]");
                    }
                    System.out.println();
                }
            }
        }
        refactorEmpty();
        drawMines();
    }
    public void drawMines()
    {
        if(gameButtons != null){
            for(int a = 0; a < width; a++)
            {
                for(int b = 0; b < height; b++)
                {
                    if(data[a][b] == 9)
                    {
                        gameButtons[a][b].setImageResource(R.drawable.mine);
                    }
                    else{
                        gameButtons[a][b].setImageResource(android.R.color.transparent);
                    }
                }
            }
        }
    }
    public void refactorEmpty()
    {
        for(int posR = 0; posR < width; posR++)
        {
            for(int posC = 0; posC < height; posC++)
            {
                if(data[posR][posC] != 9)
                {
                    int tempMineCount = 0;
                    if(posR > 0)
                    {
                        if(data[posR-1][posC] == 9)
                            tempMineCount++;
                    }
                    if(posR < width - 1)
                    {
                        if(data[posR+1][posC] == 9)
                            tempMineCount++;
                    }
                    if(posC > 0)
                    {
                        if(data[posR][posC-1] == 9)
                            tempMineCount++;
                    }
                    if(posC < height - 1)
                    {
                        if(data[posR][posC+1] == 9)
                            tempMineCount++;
                    }
                    if(posR > 0 & posC > 0)
                    {
                        if(data[posR-1][posC-1] == 9)
                            tempMineCount++;
                    }
                    if(posR > 0 & posC < height - 1)
                    {
                        if(data[posR-1][posC+1] == 9)
                            tempMineCount++;
                    }
                    if(posR < width - 1 & posC > 0)
                    {
                        if(data[posR+1][posC-1] == 9)
                            tempMineCount++;
                    }
                    if(posR < width - 1 & posC < height - 1)
                    {
                        if(data[posR+1][posC+1] == 9)
                            tempMineCount++;
                    }
                    data[posR][posC] = tempMineCount;
                }
            }
        }
    }
}
