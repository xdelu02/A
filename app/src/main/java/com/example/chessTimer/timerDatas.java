package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;

public class timerDatas extends AppCompatActivity {
    public static final String NAME_WHITE = "com.example.chessTimer.nameWhite";
    public static final String MINUTES_WHITE = "com.example.chessTimer.minutesWhite";
    public static final String SECONDS_WHITE = "com.example.chessTimer.secondsWhite";
    public static final String NAME_BLACK = "com.example.chessTimer.nameBlack";
    public static final String MINUTES_BLACK = "com.example.chessTimer.minutesBlack";
    public static final String SECONDS_BLACK = "com.example.chessTimer.secondsBlack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_datas);
    }

    public int goToTimer (View view) {
        Intent intent = new Intent(this, timer.class);

        try {
            setExternalVariables(intent);
        } catch (myException e) {
            System.out.println(e);
            return -1;
        }

        startActivity(intent);

        return 0;
    }

    private void setExternalVariables(Intent intent) throws myException {
        //dichiarazione delle variabili
        String nameWhite = "White";
        int minutesWhite = 10;
        int secondsWhite = 0;
        String nameBlack = "Black";
        int minutesBlack = 10;
        int secondsBlack = 0;

        //prelevo le EditText
        EditText nW1EditText = findViewById(R.id.nameWhite);
        EditText mW1EditText = findViewById(R.id.minuteWhite);
        EditText sW1EditText = findViewById(R.id.secondWhite);
        EditText nB1EditText = findViewById(R.id.nameBlack);
        EditText mB1EditText = findViewById(R.id.minuteBlack);
        EditText sB1EditText = findViewById(R.id.secondBlack);

        //Se le edit text hanno un valore al loro interno copio il valore nella variabile corrispondente
        //Altrimenti lascio quelli preimpostati in testa
        if(!nW1EditText.getText().toString().equals(""))
            nameWhite = nW1EditText.getText().toString();
        if(!nW1EditText.getText().toString().equals(""))
            minutesWhite = Integer.parseInt(mW1EditText.getText().toString());
        if(!sW1EditText.getText().toString().equals(""))
            secondsWhite = Integer.parseInt(sW1EditText.getText().toString());
        if(!nB1EditText.getText().toString().equals(""))
            nameBlack = nB1EditText.getText().toString();
        if(!mB1EditText.getText().toString().equals(""))
            minutesBlack = Integer.parseInt(mB1EditText.getText().toString());
        if(!sB1EditText.getText().toString().equals(""))
            secondsBlack = Integer.parseInt(sB1EditText.getText().toString());

        //controllo che il tempo non superi il limite massimo di 59:59
        if(secondsWhite > 59)
            throw new myException("****** White seconds intervall error ******");
        if(minutesWhite > 59)
            throw new myException("****** White minute intervall error ******");
        if(secondsBlack > 59)
            throw new myException("****** Black seconds intervall error ******");
        if(minutesBlack > 59)
            throw new myException("****** Black minute intervall error ******");

        //visualizzo su "RUN" i dati passati
        visualiseLogcat("nameWhite ", "" + nameWhite);
        visualiseLogcat("minutesWhite ", "" + minutesWhite);
        visualiseLogcat("secondsWhite ", "" + secondsWhite);
        visualiseLogcat("nameBlack ", "" + nameBlack);
        visualiseLogcat("minutesBlack ", "" + minutesBlack);
        visualiseLogcat("secondsBlack ", "" + secondsBlack);

        //Aggiungo le variabili come extra
        intent.putExtra(NAME_WHITE, nameWhite);
        intent.putExtra(MINUTES_WHITE, minutesWhite);
        intent.putExtra(SECONDS_WHITE, secondsWhite);
        intent.putExtra(NAME_BLACK, nameBlack);
        intent.putExtra(MINUTES_BLACK, minutesBlack);
        intent.putExtra(SECONDS_BLACK, secondsBlack);
    }

    //metodo provvisorio
    private void visualiseLogcat(String tag,String str) {
        Log.d(tag, str);
    }
}
