package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class FisherTimer extends AppCompatActivity {

    private long bottom_START_TIME;
    private long top_START_TIME;
    private Button topBtn;
    private Button bottomBtn;
    private CountDownTimer top_countDownTimer;
    private CountDownTimer bottom_countDownTimer;
    private long incrementoTop;
    private long incrementoBottom;
    private long top_timeLeft;
    private long bottom_timeLeft;
    private int contaMosse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Intent intent = getIntent();

        TextView topName = findViewById(R.id.topName);
        topName.setText(intent.getStringExtra(timerDatas.NAME_WHITE));

        TextView bottomName = findViewById(R.id.bottomName);
        bottomName.setText(intent.getStringExtra(timerDatas.NAME_BLACK));

        int minBottom = intent.getIntExtra(timerDatas.MINUTES_BLACK,0);
        int minTop = intent.getIntExtra(timerDatas.MINUTES_WHITE,0);
        int secBottom = intent.getIntExtra(timerDatas.SECONDS_BLACK,0);
        int secTop = intent.getIntExtra(timerDatas.SECONDS_WHITE, 0);
        int incrementoBottom_in_secondi = intent.getIntExtra(timerDatas.RECOVER_BLACK,0);
        int incrementoTop_in_secondi = intent.getIntExtra(timerDatas.RECOVER_WHITE,0);

        top_START_TIME = (minTop * 60000) + (secTop * 1000);
        incrementoTop = incrementoTop_in_secondi * 1000;
        bottom_START_TIME = (minBottom * 60000) + (secBottom * 1000);
        incrementoBottom = incrementoBottom_in_secondi * 1000;

        top_timeLeft = top_START_TIME;
        bottom_timeLeft = bottom_START_TIME;

        String needContaMosse = intent.getStringExtra(timerDatas.MOVE_COUNTER);

        contaMosse = 0;

        Button resetBtn = findViewById(R.id.resetBtn);
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
                    topBtn.setText(R.string.show_msg_LOSE);
                    bottomBtn.setText(R.string.show_msg_WIN);
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
                    topBtn.setText(R.string.show_msg_WIN);
                    bottomBtn.setText( R.string.show_msg_LOSE);
                }
            }.start();
        }
    }

    private void pauseTimer(String pos) {
        if(pos.equals("top") && contaMosse != 0) {
            top_countDownTimer.cancel();
            top_timeLeft += incrementoTop;
            uptadeCountDownText("top");
        }
        else if(pos.equals("bottom") && contaMosse != 0) {
            bottom_countDownTimer.cancel();
            bottom_timeLeft += incrementoBottom;
            uptadeCountDownText("bottom");
        }
    }

    private void resetTimer() {
        pauseTimer("top");
        pauseTimer("bottom");

        contaMosse = 0;

        bottom_timeLeft = bottom_START_TIME;
        top_timeLeft = top_START_TIME;

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
