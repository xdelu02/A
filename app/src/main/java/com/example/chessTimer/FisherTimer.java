package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
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
    private int colorIsMyTurn;
    private int colorIsNotMyTurn;
    private int colorTextIsMyTurn;
    private int colorTextIsNotMyTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ImageButton resetBtn = findViewById ( R.id.resetBtn );
        pauseBtn = findViewById ( R.id.pauseBtn );
        ImageButton startBtn = findViewById ( R.id.playBtn );

        if(timerDatas.isDarkModeOn()) {
            setTheme(R.style.DarkTheme);
            startBtn.setImageResource ( R.drawable.ic_play_arrow_white_55dp );
            pauseBtn.setImageResource ( R.drawable.ic_pause_white_55dp );
            resetBtn.setImageResource ( R.drawable.ic_refresh_white_55dp );
            colorIsMyTurn = 0xff2735ff;    // 0xff4169e1
            colorIsNotMyTurn = 0xff5a595a;
            colorTextIsMyTurn = 0x99000000;
            colorTextIsNotMyTurn = 0xfffafafa;
        }
        else {
            setTheme ( R.style.AppTheme );
            startBtn.setImageResource ( R.drawable.ic_play_arrow_black_55dp );
            pauseBtn.setImageResource ( R.drawable.ic_pause_black_55dp );
            resetBtn.setImageResource ( R.drawable.ic_refresh_black_55dp );
            colorIsMyTurn = 0xff4169e1;
            colorIsNotMyTurn = 0xffd6d7d7;
            colorTextIsMyTurn = 0xfffafafa;
            colorTextIsNotMyTurn = 0xff000000;
        }
        topBtn = findViewById(R.id.topBtn);
        bottomBtn = findViewById(R.id.bottomBtn);

        isBottomTimerRunning = false;
        isTopTimerRunning = false;


        SharedPreferences sharedPreferences = getSharedPreferences(timerDatas.SHARED_PREFS, MODE_PRIVATE);

        final TextView topName = findViewById(R.id.topName);
        topName.setText(sharedPreferences.getString(timerDatas.SP_NAME_WHITE, "White"));

        TextView bottomName = findViewById(R.id.bottomName);
        bottomName.setText(sharedPreferences.getString(timerDatas.SP_NAME_BLACK, "Black"));

        needMoveCounter = sharedPreferences.getBoolean(timerDatas.SP_MOVE_COUNTER, false);

        int minBottom = Integer.parseInt(sharedPreferences.getString(timerDatas.SP_MINUTES_BLACK, "10"));
        int minTop = Integer.parseInt(sharedPreferences.getString(timerDatas.SP_MINUTES_WHITE, "10"));
        int secBottom = Integer.parseInt(sharedPreferences.getString(timerDatas.SP_SECONDS_BLACK, "0"));
        int secTop = Integer.parseInt(sharedPreferences.getString(timerDatas.SP_SECONDS_WHITE, "0"));
        int incrementoBottom_in_secondi = Integer.parseInt(sharedPreferences.getString(timerDatas.SP_RECOVER_BLACK, "0"));
        int incrementoTop_in_secondi = Integer.parseInt(sharedPreferences.getString(timerDatas.SP_RECOVER_WHITE, "0"));

        //se lo switch non e' attivo e' necessatio portare il tempo di recupero a 0
        if(!sharedPreferences.getBoolean(timerDatas.SP_RECOVERSWICH_WHITE, false)) {
            incrementoTop_in_secondi = 0;
        }
        if(!sharedPreferences.getBoolean(timerDatas.SP_RECOVERSWICH_BLACK, false)) {
            incrementoBottom_in_secondi = 0;
        }

        top_START_TIME = (minTop * 60000) + (secTop * 1000) + 5;
        incrementoTop = incrementoTop_in_secondi * 1000;
        bottom_START_TIME = (minBottom * 60000) + (secBottom * 1000) + 5;
        incrementoBottom = incrementoBottom_in_secondi * 1000;

        top_timeLeft = top_START_TIME;
        bottom_timeLeft = bottom_START_TIME;

        contaMosse = 0;

        uptadeCountDownText("top");
        uptadeCountDownText("bottom");

        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer("top");
                startTimer("bottom");
                topBtn.setClickable(false);
                bottomBtn.setClickable(true);
                changeFontAndColor();
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
                changeFontAndColor();
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
                    } else {
                        startTimer ( "top" );
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
                    top_timeLeft = millisUntilFinished+2;
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
                    bottom_timeLeft = millisUntilFinished+2;
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

        resetColorAndFonts();
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

    private void changeFontAndColor(){
        if(isTopTimerRunning) {
            bottomBtn.setBackgroundColor ( colorIsNotMyTurn );
            topBtn.setBackgroundColor ( colorIsMyTurn );
            topBtn.setTextSize( TypedValue.COMPLEX_UNIT_SP, 82);
            bottomBtn.setTextSize( TypedValue.COMPLEX_UNIT_SP, 63);
            topBtn.setTextColor ( colorTextIsMyTurn );
            bottomBtn.setTextColor ( colorTextIsNotMyTurn );
        }else {
            topBtn.setBackgroundColor ( colorIsNotMyTurn );
            bottomBtn.setBackgroundColor ( colorIsMyTurn );
            bottomBtn.setTextSize( TypedValue.COMPLEX_UNIT_SP, 82);
            topBtn.setTextSize( TypedValue.COMPLEX_UNIT_SP, 63);
            topBtn.setTextColor ( colorTextIsNotMyTurn );
            bottomBtn.setTextColor ( colorTextIsMyTurn );
        }
    }

    private void resetColorAndFonts(){
        topBtn.setTextColor(colorTextIsNotMyTurn);
        bottomBtn.setTextColor(colorTextIsNotMyTurn);
        topBtn.setTextSize (TypedValue.COMPLEX_UNIT_SP, 63 );
        bottomBtn.setTextSize (TypedValue.COMPLEX_UNIT_SP, 63 );
        topBtn.setBackgroundColor ( colorIsNotMyTurn );
        bottomBtn.setBackgroundColor ( colorIsNotMyTurn );
    }
}
