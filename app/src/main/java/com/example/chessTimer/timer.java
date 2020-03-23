package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class timer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
    }

    public void top_timer(View v)
    {


        Button btn = (Button) findViewById(R.id.topBtn);
        btn.setText("Text changed by button click");
    }

    public void bottom_timer(View v)
    {



        Button btn = (Button) findViewById(R.id.bottomBtn);
        btn.setText("Text changed by button click");
    }
}
