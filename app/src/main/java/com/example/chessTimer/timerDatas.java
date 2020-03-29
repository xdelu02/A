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

    public int goToTimer (View view) {
        Intent intent = new Intent(this, timer.class);
        int result = -1;

        try {
            result = setExternalVariables(intent);
        } catch (myException e) {
            System.out.println(e);
            return -1;
        }

        startActivity(intent);

        return 0;
    }

    private int setExternalVariables(Intent intent) throws myException {
        String nameWhite = "Player1";
        int minuteWhite = 10;
        int secondWhite = 0;
        String nameBlack = "Player2";
        int minuteBlack = 10;
        int secondBlack = 0;

        EditText nW1EditText = findViewById(R.id.nameWhite);
        EditText mW1EditText = findViewById(R.id.minuteWhite);
        EditText sW1EditText = findViewById(R.id.secondWhite);
        EditText nB1EditText = findViewById(R.id.nameBlack);
        EditText mB1EditText = findViewById(R.id.minuteBlack);
        EditText sB1EditText = findViewById(R.id.secondBlack);

        if(!nW1EditText.getText().toString().equals(""))
            nameWhite = nW1EditText.getText().toString();
        if(!nW1EditText.getText().toString().equals(""))
            minuteWhite = Integer.parseInt(mW1EditText.getText().toString());
        if(!sW1EditText.getText().toString().equals(""))
            secondWhite = Integer.parseInt(sW1EditText.getText().toString());
        if(!nB1EditText.getText().toString().equals(""))
            nameBlack = nB1EditText.getText().toString();
        if(!mB1EditText.getText().toString().equals(""))
            minuteBlack = Integer.parseInt(mB1EditText.getText().toString());
        if(!sB1EditText.getText().toString().equals(""))
            secondBlack = Integer.parseInt(sB1EditText.getText().toString());

        if(secondWhite > 59)
            throw new myException("****** White seconds intervall error ******");
        if(minuteWhite > 59)
            throw new myException("****** White minute intervall error ******");
        if(secondBlack > 59)
            throw new myException("****** Black seconds intervall error ******");
        if(minuteBlack > 59)
            throw new myException("****** Black minute intervall error ******");


        intent.putExtra("com.example.chessTimer.namePlayer1", nameWhite);
        intent.putExtra("com.example.chessTimer.minutePlayer1", minuteWhite);
        intent.putExtra("com.example.chessTimer.secondPlayer1", secondWhite);
        intent.putExtra("com.example.chessTimer.namePlayer2", nameBlack);
        intent.putExtra("com.example.chessTimer.minutePlayer2", minuteBlack);
        intent.putExtra("com.example.chessTimer.secondPlayer2", secondBlack);

        return 0;
    }
}
