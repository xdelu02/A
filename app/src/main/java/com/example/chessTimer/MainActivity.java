package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasySplashScreen config = new EasySplashScreen(MainActivity.this)
                .withFullScreen()
                .withTargetActivity(timerDatas.class)
                .withSplashTimeOut(2000)
                .withBackgroundColor(Color.parseColor("#1a1b29"))
                .withHeaderText("")
                .withFooterText("Made by ChessTimer.inc")
                .withBeforeLogoText("")
                .withAfterLogoText("Chess Timer")
                .withLogo(R.mipmap.ic_launcher);

        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }

    //go to methods
    //timerDatas
    private void goToTimerDatas () {
        Intent intent = new Intent(this, timerDatas.class);
        startActivity(intent);
    }
    //Settings
    private void goToSettings (){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
