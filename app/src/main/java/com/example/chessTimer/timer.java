package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class timer extends AppCompatActivity {
    int wMin = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.minuteWhite")));
    int wSec = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.secondWhite")));
    int bMin = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.minuteBlack")));
    int bSec = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.secondBlack")));

    private final long W_START_TIME = (wMin * 60) + wSec;
    private final long B_START_TIME = (bMin * 60) + bSec;

    Button B_top_btn;
    Button W_bottom_btn;

    private CountDownTimer B_countDownTimer;
    private CountDownTimer W_countDownTimer;

    private boolean B_timerRunning;
    private boolean W_timerRunning;

    private long B_timeLeft = B_START_TIME;
    private long W_timeLeft = W_START_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        B_top_btn = findViewById(R.id.topBtn);
        W_bottom_btn = findViewById(R.id.bottomBtn);

        B_top_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                B_timerRunning=true;
                W_timerRunning=false;
                if(B_timerRunning) {
                    pauseTimer("black_top");
                    startTimer("white_bottom");
                }
                else {
                    startTimer("black_top");
                    pauseTimer("white_bottom");
                }
            }
        });
    }

    private void startTimer(String color){
        if(color.equals("white_bottom")) {
            W_countDownTimer = new CountDownTimer(W_timeLeft, 1000) {
                @Override
                public void onTick(long W_millisUntilFinished) {
                    W_timeLeft = W_millisUntilFinished;
                    uptadeBtnText("white_bottom");
                }

                @Override
                public void onFinish() {

                }
            }.start();

            W_timerRunning = true;
        }else {
            B_countDownTimer = new CountDownTimer(B_timeLeft, 1000) {
                @Override
                public void onTick(long B_millisUntilFinished) {
                    B_timeLeft = B_millisUntilFinished;
                    uptadeBtnText("black_top");
                }

                @Override
                public void onFinish() {
                    B_timerRunning = false;
                    W_timerRunning = false;
                }
            }.start();

            B_timerRunning = true;
            W_timerRunning = false;
        }
    }

    private void pauseTimer(String color){
        if(color.equals("white_bottom")){
            W_countDownTimer.cancel();
            W_timerRunning=false;
        }
        else{
            B_countDownTimer.cancel();
            B_timerRunning=false;
        }
    }

    private void uptadeBtnText(String color){
        if(color.equals("white_bottom")){
            int min = (int) W_timeLeft / 1000 / 60;
            int sec = (int) W_timeLeft / 1000 % 60;
            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
            W_bottom_btn.setText(timeLeftFormatted);
        }else{
            int min = (int) B_timeLeft / 1000 / 60;
            int sec = (int) B_timeLeft / 1000 % 60;
            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
            B_top_btn.setText(timeLeftFormatted);
        }
    }
}
