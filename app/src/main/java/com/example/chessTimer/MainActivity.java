package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TimerDatas
        Button timerDatasBtn = (Button) findViewById(R.id.timerDatasBtn);
        timerDatasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTimerDatas();
            }
        });

        //Settings
        Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettings();
            }
        });
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
