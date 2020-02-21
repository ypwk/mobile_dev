package com.wukevin.lab05_lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView OnCreate;
    TextView OnStart;
    TextView OnResume;
    TextView OnPause;
    TextView OnStop;
    TextView OnRestart;
    TextView OnDestroy;

    SharedPreferences prefObj;
    SharedPreferences.Editor prefEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefObj = getApplicationContext().getSharedPreferences("LifecycleAppPref", 0);
        prefEditor = prefObj.edit();

        OnCreate = findViewById(R.id.oncreate);
        OnStart = findViewById(R.id.onstart);
        OnResume = findViewById(R.id.onresume);
        OnPause = findViewById(R.id.onpause);
        OnStop = findViewById(R.id.onstop);
        OnRestart = findViewById(R.id.onrestart);
        OnDestroy = findViewById(R.id.ondestroy);

        String temp = prefObj.getString("onstart", null);
        if(temp != null)
        {
            String tempText = "onStart(): " + temp;
            OnStart.setText(tempText);
        }
        temp = prefObj.getString("oncreate", null);
        if(temp != null)
        {
            String tempText = "onCreate(): " + temp;
            OnCreate.setText(tempText);
        }
        temp = prefObj.getString("onresume", null);
        if(temp != null)
        {
            String tempText = "onResume(): " + temp;
            OnResume.setText(tempText);
        }
        temp = prefObj.getString("onpause", null);
        if(temp != null)
        {
            String tempText = "onPause(): " + temp;
            OnPause.setText(tempText);
        }
        temp = prefObj.getString("onstop", null);
        if(temp != null)
        {
            String tempText = "onStop(): " + temp;
            OnStop.setText(tempText);
        }
        temp = prefObj.getString("onrestart", null);
        if(temp != null)
        {
            String tempText = "onRestart(): " + temp;
            OnRestart.setText(tempText);
        }
        temp = prefObj.getString("ondestroy", null);
        if(temp != null)
        {
            String tempText = "onDestroy(): " + temp;
            OnDestroy.setText(tempText);
        }
        String yesString = OnCreate.getText().subSequence(OnCreate.getText().length() - 1, OnCreate.getText().length()).toString();
        int newNumber = Integer.parseInt(yesString) + 1;
        String tempText = "onCreate(): " + newNumber + "";
        OnCreate.setText(tempText);
    }

    @Override
    protected void onStart() {
        String yesString = OnStart.getText().subSequence(OnStart.getText().length() - 1, OnStart.getText().length()).toString();
        int newNumber = Integer.parseInt(yesString) + 1;
        String tempText = "onStart(): " + newNumber + "";
        OnStart.setText(tempText);
        super.onStart();
    }

    @Override
    protected void onResume() {
        String yesString = OnResume.getText().subSequence(OnResume.getText().length() - 1, OnResume.getText().length()).toString();
        int newNumber = Integer.parseInt(yesString) + 1;
        String tempText = "onResume(): " + newNumber + "";
        OnResume.setText(tempText);
        super.onResume();
    }

    @Override
    protected void onPause() {
        String yesString = OnPause.getText().subSequence(OnPause.getText().length() - 1, OnPause.getText().length()).toString();
        int newNumber = Integer.parseInt(yesString) + 1;
        String tempText = "onPause(): " + newNumber + "";
        OnPause.setText(tempText);
        super.onPause();
    }

    @Override
    protected void onStop() {
        String yesString = OnStop.getText().subSequence(OnStop.getText().length() - 1, OnStop.getText().length()).toString();
        int newNumber = Integer.parseInt(yesString) + 1;
        String tempText = "onStop(): " + newNumber + "";
        OnStop.setText(tempText);
        super.onStop();
    }

    @Override
    protected void onRestart() {
        String yesString = OnRestart.getText().subSequence(OnRestart.getText().length() - 1, OnRestart.getText().length()).toString();
        int newNumber = Integer.parseInt(yesString) + 1;
        String tempText = "onRestart() " + newNumber + "";
        OnRestart.setText(tempText);
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        String yesString = OnDestroy.getText().subSequence(OnDestroy.getText().length() - 1, OnDestroy.getText().length()).toString();
        int newNumber = Integer.parseInt(yesString) + 1;
        String tempText = "onDestroy() " + newNumber + "";
        OnDestroy.setText(tempText);
        prefEditor.putString("onstart", OnStart.getText().subSequence(OnStart.getText().length() - 1, OnStart.getText().length()).toString());
        prefEditor.putString("oncreate", OnCreate.getText().subSequence(OnCreate.getText().length() - 1, OnCreate.getText().length()).toString());
        prefEditor.putString("onresume", OnResume.getText().subSequence(OnResume.getText().length() - 1, OnResume.getText().length()).toString());
        prefEditor.putString("onpause", OnPause.getText().subSequence(OnPause.getText().length() - 1, OnPause.getText().length()).toString());
        prefEditor.putString("onstop", OnStop.getText().subSequence(OnStop.getText().length() - 1, OnStop.getText().length()).toString());
        prefEditor.putString("onrestart", OnRestart.getText().subSequence(OnRestart.getText().length() - 1, OnRestart.getText().length()).toString());
        prefEditor.putString("ondestroy", OnDestroy.getText().subSequence(OnDestroy.getText().length() - 1, OnDestroy.getText().length()).toString());
        prefEditor.commit();
        super.onDestroy();
    }
}
