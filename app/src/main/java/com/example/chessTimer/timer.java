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

    private static final long START_TIME = 600000;
    private Button topBtn;
    private Button resetBtn;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    private long timeLeft = START_TIME;

/*    int wMin = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.minuteWhite")));
    int wSec = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.secondWhite")));
    int bMin = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.minuteBlack")));
    int bSec = Integer.parseInt(Objects.requireNonNull(getIntent().getExtras().getString("com.example.chessTimer.secondBlack")));*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        resetBtn = findViewById(R.id.resetBtn);
        topBtn = findViewById(R.id.topBtn);

        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTimerRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        uptadeCountDownText();
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                uptadeCountDownText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                topBtn.setText("A");
            }
        }.start();

        isTimerRunning = true;
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
    }

    private void resetTimer() {
        pauseTimer();
        timeLeft = START_TIME;
        uptadeCountDownText();
        isTimerRunning = false;
    }

    private void uptadeCountDownText() {
        int minutes = (int) timeLeft / 1000 / 60;
        int seconds = (int) timeLeft / 1000 % 60;

        String timeLeftOutput = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        topBtn.setText(timeLeftOutput);
    }
}
