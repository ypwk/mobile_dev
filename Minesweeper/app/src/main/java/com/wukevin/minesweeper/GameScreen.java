package com.wukevin.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.health.SystemHealthManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameScreen extends AppCompatActivity {
    TableLayout GameTable;
    Chronometer TimerText;
    TextView FlagText;
    TextView InfoText;
    Button ResetButton;
    Button ClickStateButton;

    int width = 10;
    int height = 8;
    int numberOfFlags = 10;
    int flagsLeft = 10;
    long elapsedTime = 0;

    boolean ClickState = false; //false is dig, true is flag
    boolean clicked = false;
    ImageButton[][] gameButtons;
    int[][] data;
    int[][] mineCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        GameTable = findViewById(R.id.game_table);
        TimerText = findViewById(R.id.TimerText);
        FlagText = findViewById(R.id.FlagText);
        InfoText = findViewById(R.id.info_text);
        ResetButton = findViewById(R.id.ResetButton);
        ClickStateButton = findViewById(R.id.change_state_button);

        TimerText.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long minutes = ((elapsedTime - TimerText.getBase())/1000) / 60;
                long seconds = ((elapsedTime - TimerText.getBase())/1000) % 60;
                elapsedTime = elapsedTime + 1000;
                String minuteText = "" + minutes;
                if(minutes < 10)
                {
                    minuteText = "0" + minutes;
                }

                String secondText = "" + seconds;
                if(seconds < 10)
                {
                    secondText= "0" + seconds;
                }
                TimerText.setText(minuteText + ":" + secondText);
            }
        });
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
                gameButtons[a][b].setImageResource(R.drawable.unclicked);
                tr.addView(gameButtons[a][b]);
                /*
                    11: user clicked but empty
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
                            if(!clicked)
                            {
                                generatePuzzle(posR, posC);
                                floodFillHelper(posR, posC);
                                clicked = true;
                            }
                            else if(data[posR][posC] == 9)
                            {
                                GameOver();
                            }
                            else
                            {
                                switch(mineCount[posR][posC])
                                {
                                    case 0:
                                        floodFillHelper(posR, posC);
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
                                data[posR][posC] = mineCount[posR][posC];
                            }
                        }
                        else
                        {
                            if(flagsLeft > 0)
                            {
                                if(data[posR][posC] == 0)
                                {
                                    data[posR][posC] = 10;
                                    gameButtons[posR][posC].setImageResource(R.drawable.unclicked);
                                    flagsLeft++;
                                    String flagtexttext = "Flags Left: " + flagsLeft;
                                    FlagText.setText(flagtexttext);
                                }
                                else if(!(1 <= data[posR][posC] && 8 >= data[posR][posC])){
                                    data[posR][posC] = 0;
                                    gameButtons[posR][posC].setImageResource(R.drawable.flag);
                                    flagsLeft--;
                                    String flagtexttext = "Flags Left: " + flagsLeft;
                                    FlagText.setText(flagtexttext);
                                }
                                InfoText.setText(" ");
                            }
                            else
                            {
                                InfoText.setText("You're out of flags :(");
                                if(data[posR][posC] == 0)
                                {
                                    data[posR][posC] = 10;
                                    gameButtons[posR][posC].setImageResource(R.drawable.unclicked);
                                    flagsLeft++;
                                    String flagtexttext = "Flags Left: " + flagsLeft;
                                    FlagText.setText(flagtexttext);
                                }
                            }
                        }
                        if(checkForWin())
                        {
                            youWin();
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
        FlagText.setText("Flags Left: " + flagsLeft);
    }

    public void GameOver()
    {
        TimerText.stop();
        System.out.println("Game Over!!!!");
        InfoText.setText("You Lose!!");
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

    public void youWin()
    {
        TimerText.stop();
        System.out.println("You won!!!!");
        InfoText.setText("You win!!!!");
        for(int a = 0; a < width; a++)
        {
            for(int b = 0; b < height; b++)
            {
                gameButtons[a][b].setClickable(false);
                gameButtons[a][b].setBackgroundColor(Color.GREEN);
                if(data[a][b] == 9)
                {
                    gameButtons[a][b].setImageResource(R.drawable.flag);
                }
            }
        }
    }

    public boolean checkForWin()
    {
        boolean done = true;
        int r = 0;
        int c = 0;
        while(done && c < height)
        {
            System.out.println("Checking: " + r + ", " + c + "; Data: " + data[r][c]);
            if(data[r][c] == 10)
            {
                done = false;
            }
            else
            {
                done = true;
            }
            if(r == width-1)
            {
                c++;
                r = 0;
            }
            else
            {
                r++;
            }
        }
        /*for(int a = 0; a < width; a++)
        {
            for(int b = 0; b < height; b++)
            {
                gameButtons[a][b].setBackgroundColor(Color.WHITE);
            }
        }*/
        System.out.println("what" + done);
        return done;
    }

    public void NewGame()
    {
        elapsedTime = SystemClock.elapsedRealtime();
        TimerText.start();
        InfoText.setText(" ");
        clicked = false;
        TimerText.setBase(SystemClock.elapsedRealtime());
        data = new int[width][height];
        mineCount = new int[width][height];
        flagsLeft = 10;
        FlagText.setText("Flags Left: " + flagsLeft);
        for(int a = 0; a < width; a++)
        {
            for(int b = 0; b < height; b++)
            {
                data[a][b] = 10;
                mineCount[a][b] = 0;
                if(gameButtons != null)
                {
                    gameButtons[a][b].setBackgroundColor(Color.WHITE);
                    gameButtons[a][b].setClickable(true);
                    gameButtons[a][b].setImageResource(R.drawable.unclicked);
                }
            }
        }
        drawMines();
    }

    public void generatePuzzle(int r, int c)
    {
        int tempNumberOfFlags = numberOfFlags;
        while(tempNumberOfFlags > 0)
        {
            int tempR = (int)(Math.random() * width);
            int tempC = (int)(Math.random() * height);
            if(data[tempR][tempC] == 10 && (tempR > r + 1 || tempR < r - 1) && (tempC > c + 1 || tempC < c - 1)) {
                data[tempR][tempC] = 9;
                tempNumberOfFlags--;
            }
        }
        refactorEmpty();
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
                        gameButtons[a][b].setImageResource(R.drawable.unclicked);
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
                    mineCount[posR][posC] = tempMineCount;
                }
            }
        }
    }

    public void floodFillHelper(int r, int c)
    {
        System.out.println("Flood filling at: " + r + ", " + c + ", Data is: " + data[r][c]);
        if(data[r][c] != 10)
        {
            return;
        }
        else if(mineCount[r][c] == 0)
        {
            data[r][c] = 11;
            gameButtons[r][c].setImageResource(R.drawable.empty);
            if(r > 0)
            {
                floodFillHelper(r-1, c);
            }
            if(r < width - 1)
            {
                floodFillHelper(r+1, c);
            }
            if(c > 0)
            {
                floodFillHelper(r, c-1);
            }
            if(c < height - 1)
            {
                floodFillHelper(r, c+1);
            }
        }
        else
        {
            switch(mineCount[r][c])
            {
                case 1:
                    gameButtons[r][c].setImageResource(R.drawable.one);
                    break;
                case 2:
                    gameButtons[r][c].setImageResource(R.drawable.two);
                    break;
                case 3:
                    gameButtons[r][c].setImageResource(R.drawable.three);
                    break;
                case 4:
                    gameButtons[r][c].setImageResource(R.drawable.four);
                    break;
                case 5:
                    gameButtons[r][c].setImageResource(R.drawable.five);
                    break;
                case 6:
                    gameButtons[r][c].setImageResource(R.drawable.six);
                    break;
                case 7:
                    gameButtons[r][c].setImageResource(R.drawable.seven);
                    break;
                case 8:
                    gameButtons[r][c].setImageResource(R.drawable.eight);
                    break;
            }
            gameButtons[r][c].setClickable(false);
            data[r][c] = mineCount[r][c];
        }
    }
}
