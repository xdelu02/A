package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;

public class timerDatas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_datas);
    }

    public void goToTimer (View view){
        Intent intent = new Intent(this, timer.class);
        setExternalVariables(intent);
        startActivity(intent);
    }

    private void setExternalVariables(Intent intent) {
        String nameWhite = "Player1";
        String hourWhite = "0";
        String minuteWhite = "10";
        String secondWhite = "0";
        String nameBlack = "Player2";
        String hourBlack = "0";
        String minuteBlack = "10";
        String secondBlack = "0";

        EditText nW1EditText = findViewById(R.id.nameWhite);
        EditText hW1EditText = findViewById(R.id.hourWhite);
        EditText mW1EditText = findViewById(R.id.minuteWhite);
        EditText sW1EditText = findViewById(R.id.secondWhite);
        EditText nB1EditText = findViewById(R.id.nameBlack);
        EditText hB1EditText = findViewById(R.id.hourBlack);
        EditText mB1EditText = findViewById(R.id.minuteBlack);
        EditText sB1EditText = findViewById(R.id.secondBlack);

        if(!nW1EditText.getText().toString().equals(""))
            nameWhite = nW1EditText.getText().toString();
        if(!hW1EditText.getText().toString().equals(""))
            hourWhite = hW1EditText.getText().toString();
        if(!nW1EditText.getText().toString().equals(""))
            minuteWhite = mW1EditText.getText().toString();
        if(!sW1EditText.getText().toString().equals(""))
            secondWhite = sW1EditText.getText().toString();
        if(!nB1EditText.getText().toString().equals(""))
            nameBlack = nB1EditText.getText().toString();
        if(!hB1EditText.getText().toString().equals(""))
            hourBlack = hB1EditText.getText().toString();
        if(!mB1EditText.getText().toString().equals(""))
            minuteBlack = mB1EditText.getText().toString();
        if(!sB1EditText.getText().toString().equals(""))
            secondBlack = sB1EditText.getText().toString();

        intent.putExtra("com.example.chessTimer.namePlayer1", nameWhite);
        intent.putExtra("com.example.chessTimer.hourPlayer1", hourWhite);
        intent.putExtra("com.example.chessTimer.minutePlayer1", minuteWhite);
        intent.putExtra("com.example.chessTimer.secondPlayer1", secondWhite);
        intent.putExtra("com.example.chessTimer.namePlayer2", nameBlack);
        intent.putExtra("com.example.chessTimer.hourPlayer2", hourBlack);
        intent.putExtra("com.example.chessTimer.minutePlayer2", minuteBlack);
        intent.putExtra("com.example.chessTimer.secondPlayer2", secondBlack);
    }
}
