package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
    private ImageButton pauseBtn;
    private boolean isTopTimerRunning;
    private boolean isBottomTimerRunning;
    private boolean lastTimerRunning; // false:top
    private boolean needMoveCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        topBtn = findViewById(R.id.topBtn);
        bottomBtn = findViewById(R.id.bottomBtn);

        isBottomTimerRunning = false;
        isTopTimerRunning = false;

        Intent intent = getIntent();

        TextView topName = findViewById(R.id.topName);
        String tName = "    " + intent.getStringExtra(timerDatas.NAME_WHITE);
        topName.setText(tName);

        TextView bottomName = findViewById(R.id.bottomName);
        String btmName = "    " + intent.getStringExtra(timerDatas.NAME_BLACK);
        bottomName.setText(btmName);

        needMoveCounter = intent.getBooleanExtra ( timerDatas.MOVE_COUNTER, false);

        int minBottom = intent.getIntExtra(timerDatas.MINUTES_BLACK,0);
        int minTop = intent.getIntExtra(timerDatas.MINUTES_WHITE,0);
        int secBottom = intent.getIntExtra(timerDatas.SECONDS_BLACK,0);
        int secTop = intent.getIntExtra(timerDatas.SECONDS_WHITE, 0);
        int incrementoBottom_in_secondi = intent.getIntExtra(timerDatas.RECOVER_BLACK,0);
        int incrementoTop_in_secondi = intent.getIntExtra(timerDatas.RECOVER_WHITE,0);

        top_START_TIME = (minTop * 60000) + (secTop * 1000) + 5;
        incrementoTop = incrementoTop_in_secondi * 1000;
        bottom_START_TIME = (minBottom * 60000) + (secBottom * 1000) + 5;
        incrementoBottom = incrementoBottom_in_secondi * 1000;

        top_timeLeft = top_START_TIME;
        bottom_timeLeft = bottom_START_TIME;

        contaMosse = 0;
        ImageButton resetBtn = findViewById ( R.id.resetBtn );
        pauseBtn = findViewById ( R.id.pauseBtn );
        ImageButton startBtn = findViewById ( R.id.playBtn );

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

        resetBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                pauseBtn.setClickable ( true );
            }
        });

        pauseBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(contaMosse != 0) {
                    if (isTopTimerRunning) {
                        pauseTimer ( "top" );
                    } else {
                        pauseTimer ( "bottom" );
                    }
                }
            }
        } );

        startBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(!isTopTimerRunning && !isBottomTimerRunning && contaMosse !=0){

                    if (lastTimerRunning) {
                        startTimer ( "bottom" );
                        isBottomTimerRunning = true;
                    } else {
                        startTimer ( "top" );
                        isTopTimerRunning = true;
                    }
                }
            }
        } );
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
                    pauseBtn.setClickable ( false );
                }
            }.start();
            isTopTimerRunning = true;
            isBottomTimerRunning = false;
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
                    pauseBtn.setClickable ( false );
                }
            }.start();

            isTopTimerRunning = false;
            isBottomTimerRunning = true;
        }
    }

    private void pauseTimer(String pos) {
        if(isTopTimerRunning) {
            top_countDownTimer.cancel();
            top_timeLeft += incrementoTop;
            uptadeCountDownText("top");
            isTopTimerRunning = false;
            lastTimerRunning = false;
        }
        else if(pos.equals("bottom") && contaMosse != 0) {
            bottom_countDownTimer.cancel();
            bottom_timeLeft += incrementoBottom;
            uptadeCountDownText("bottom");
            isBottomTimerRunning = false;
            lastTimerRunning = true;
        }
    }

    private void resetTimer() {
        bottom_timeLeft = bottom_START_TIME;
        top_timeLeft = top_START_TIME;

        if(isTopTimerRunning) pauseTimer ("top");
        else pauseTimer ( "bottom" );

        contaMosse = 0;
        isBottomTimerRunning = false;
        isTopTimerRunning = false;

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

        if(needMoveCounter) {
            TextView topMoveCounter = findViewById ( R.id.topMoveCounter );
            TextView bottomMoveCounter = findViewById ( R.id.bottomMoveCounter );

            topMoveCounter.setText ( String.format ( Locale.getDefault (),"Move: %d",contaMosse ));
            bottomMoveCounter.setText ( String.format ( Locale.getDefault (),"Move: %d",contaMosse ));
        }
    }
}
