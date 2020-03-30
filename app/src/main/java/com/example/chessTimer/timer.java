package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import java.util.Locale;
import java.util.Objects;

public class timer extends AppCompatActivity {

    private static final long bottom_START_TIME = 600000;
    private static final long top_START_TIME = 600000;
    private Button topBtn;
    private Button bottomBtn;
    private Button resetBtn;
    private CountDownTimer top_countDownTimer;
    private CountDownTimer bottom_countDownTimer;
    private long top_timeLeft = top_START_TIME;
    private long bottom_timeLeft = bottom_START_TIME;
    private int contaMosse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //int bottomMin = Integer.parseInt(getIntent().getExtras().getString("com.example.chessTimer.minuteWhite"));
/*      int bottomSec = Integer.parseInt(getIntent().getExtras().getString("com.example.chessTimer.secondWhite"));
        int topMin = Integer.parseInt(getIntent().getExtras().getString("com.example.chessTimer.minuteBlack"));
        int topSec = Integer.parseInt(getIntent().getExtras().getString("com.example.chessTimer.secondBlack"));*/

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
                ++contaMosse;
            }
        });

        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer("bottom");
                startTimer("top");
                bottomBtn.setClickable(false);
                topBtn.setClickable(true);
                ++contaMosse;
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
                    topBtn.setText("LOSE");
                    bottomBtn.setText("WIN");
                }
            }.start();
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
                    topBtn.setText("WIN");
                    bottomBtn.setText("LOSE");
                }
            }.start();
        }
    }

    private void pauseTimer(String pos) {
        if(pos.equals("top") && contaMosse != 0) {
            top_countDownTimer.cancel();
        }
        else if(pos.equals("bottom") && contaMosse != 0) {
            bottom_countDownTimer.cancel();
        }
    }

    private void resetTimer() {
        pauseTimer("top");
        pauseTimer("bottom");

        contaMosse = 0;

        bottom_timeLeft = bottom_START_TIME;
        top_timeLeft = bottom_START_TIME;

        uptadeCountDownText("top");
        uptadeCountDownText("bottom");

        bottomBtn.setClickable(true);
        topBtn.setClickable(true);
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
