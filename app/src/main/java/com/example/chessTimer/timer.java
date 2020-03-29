package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class timer extends AppCompatActivity {

    private static final long bottom_START_TIME = 600000;
    private static final long top_START_TIME = 600000;
    private Button topBtn;
    private Button bottomBtn;
    private Button resetBtn;
    private CountDownTimer top_countDownTimer;
    private CountDownTimer bottom_countDownTimer;
    private boolean isTopTimerRunning;
    private boolean isBottomTimerRunning;
    private long top_timeLeft = top_START_TIME;
    private long bottom_timeLeft = bottom_START_TIME;
    private int contaMosse;

/*  int wMin = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.minuteWhite")));
    int wSec = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.secondWhite")));
    int bMin = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.minuteBlack")));
    int bSec = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.secondBlack")));*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        isTopTimerRunning = false;
        isBottomTimerRunning = false;
        contaMosse = 0;

        resetBtn = findViewById(R.id.resetBtn);
        topBtn = findViewById(R.id.topBtn);
        bottomBtn = findViewById(R.id.bottomBtn);
        uptadeCountDownText("top");
        uptadeCountDownText("bottom");

        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer("top");
                startTimer("bottom");
                topBtn.setClickable(false);
                bottomBtn.setClickable(true);
            }
        });

        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer("bottom");
                startTimer("top");
                bottomBtn.setClickable(false);
                topBtn.setClickable(true);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void startTimer(String pos){

        if(pos.equals("top")) {

            top_countDownTimer = new CountDownTimer(top_timeLeft, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    top_timeLeft = millisUntilFinished;
                    uptadeCountDownText("top");
                }

                @Override
                public void onFinish() {
                    isTopTimerRunning = false;
                    topBtn.setText("A");
                }
            }.start();

            isTopTimerRunning = true;
        }
        else{
            bottom_countDownTimer = new CountDownTimer(bottom_timeLeft, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    bottom_timeLeft = millisUntilFinished;
                    uptadeCountDownText("bottom");
                }

                @Override
                public void onFinish() {
                    isBottomTimerRunning = false;
                    bottomBtn.setText("A");
                }
            }.start();

            isBottomTimerRunning = true;
        }
    }

    private void pauseTimer(String pos) {
        if(pos.equals("top") && isTopTimerRunning){
            top_countDownTimer.cancel();
            isTopTimerRunning = false;
        }
        else if(pos.equals("bottom") && isBottomTimerRunning){
            bottom_countDownTimer.cancel();
            isBottomTimerRunning = false;
        }
    }

    private void resetTimer() {
        pauseTimer("top");
        pauseTimer("bottom");

        bottom_timeLeft = bottom_START_TIME;
        top_timeLeft = bottom_START_TIME;

        uptadeCountDownText("top");
        uptadeCountDownText("bottom");

        isTopTimerRunning = false;
        isBottomTimerRunning= false;
    }

    private void uptadeCountDownText(String pos) {
        if(pos.equals("top")){
            int minutes = (int) top_timeLeft / 1000 / 60;
            int seconds = (int) top_timeLeft / 1000 % 60;

            String timeLeftOutput = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

            topBtn.setText(timeLeftOutput);
        }
        else{
            int minutes = (int) bottom_timeLeft / 1000 / 60;
            int seconds = (int) bottom_timeLeft / 1000 % 60;

            String timeLeftOutput = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

            bottomBtn.setText(timeLeftOutput);
        }
    }
}
