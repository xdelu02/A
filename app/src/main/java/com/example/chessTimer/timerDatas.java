package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.util.Log;
import android.widget.Switch;

import java.util.ArrayList;

public class timerDatas extends AppCompatActivity {
    //percorsi
    public static final String NAME_WHITE = "com.example.chessTimer.nameWhite";
    public static final String MINUTES_WHITE = "com.example.chessTimer.minutesWhite";
    public static final String SECONDS_WHITE = "com.example.chessTimer.secondsWhite";
    public static final String RECOVER_WHITE = "com.example.chessTimer.recoverWhite";
    public static final String NAME_BLACK = "com.example.chessTimer.nameBlack";
    public static final String MINUTES_BLACK = "com.example.chessTimer.minutesBlack";
    public static final String SECONDS_BLACK = "com.example.chessTimer.secondsBlack";
    public static final String RECOVER_BLACK = "com.example.chessTimer.recoverBlack";
    public static final String MOVE_COUNTER = "com.example.chessTimer.moveCounter";

    //dichiaro le EditText
    private EditText nWEditText;
    private EditText mWEditText;
    private EditText sWEditText;
    private EditText rWEditText;
    private EditText nBEditText;
    private EditText mBEditText;
    private EditText sBEditText;
    private EditText rBEditText;

    private boolean isNeedRecoverWhite = false;
    private boolean isNeedRecoverBlack = false;
    private boolean contaMosse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_datas);

        //prelevo le EditText
        nWEditText = findViewById(R.id.nameWhite);
        mWEditText = findViewById(R.id.minuteWhite);
        sWEditText = findViewById(R.id.secondWhite);
        rWEditText = findViewById(R.id.recoverWhite);
        nBEditText = findViewById(R.id.nameBlack);
        mBEditText = findViewById(R.id.minuteBlack);
        sBEditText = findViewById(R.id.secondBlack);
        rBEditText = findViewById(R.id.recoverBlack);

        //funzione che scorre alla attivita' del FisherTimer
        final Button goToTimerBtn = findViewById(R.id.goToTimerBtn);
        goToTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTimer();
            }
        });

        //con lo scorrere dello switch abilita e disabilita la parte del recupero
        //switch recupero bianco
        final EditText recoverWhite = findViewById(R.id.recoverWhite);
        final Switch recoverSwitchWhite = findViewById(R.id.recoverSwitchWhite);
        recoverSwitchWhite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    recoverWhite.setVisibility(View.VISIBLE);
                else
                    recoverWhite.setVisibility(View.INVISIBLE);

                isNeedRecoverWhite = isChecked;
            }
        });
        //switch recupero nero
        final EditText recoverBlack = findViewById(R.id.recoverBlack);
        final Switch recoverSwitchBlack = findViewById(R.id.recoverSwitchBlack);
        recoverSwitchBlack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    recoverBlack.setVisibility(View.VISIBLE);
                else
                    recoverBlack.setVisibility(View.INVISIBLE);

                isNeedRecoverBlack = isChecked;
            }
        });

        //checkBox del conta mosse
        final CheckBox moveCounterCheckBox =  findViewById(R.id.moveCounterCheckBox);
        moveCounterCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                contaMosse = isChecked;
            }
        });
    }

    //funzione che scorre alla attivita' del FisherTimer
    public int goToTimer () {
        Intent intent = new Intent(this, FisherTimer.class);

        ArrayList<myException> exceptions = new ArrayList<>();

        try {
            controlCharLength();
        } catch (myException e) {
            System.out.println(e);
            visualiseError(e);
            return -1;
        }

        exceptions.clear();

        try {
            setExternalVariables(intent);
        } catch (myException e) {
            System.out.println(e);
            visualiseError(e);
            return -2;
        }

        startActivity(intent);

        return 0;
    }

    //funzione che setta tutte le variabili da consegnare al FisherTimer
    private void setExternalVariables(Intent intent) throws myException {
        //dichiarazione delle variabili
        String nameWhite = "White";
        int minutesWhite = 10;
        int secondsWhite = 0;
        int recuperoWhite = 0;
        String nameBlack = "Black";
        int minutesBlack = 10;
        int secondsBlack = 0;
        int recuperoBlack = 0;

        //Se le edit text hanno un valore al loro interno copio il valore nella variabile corrispondente
        //Altrimenti lascio quelli preimpostati in testa
        if(!nWEditText.getText().toString().equals("")) //nome White
            nameWhite = nWEditText.getText().toString();
        if(!mWEditText.getText().toString().equals("")) //minuti White
            minutesWhite = Integer.parseInt(mWEditText.getText().toString());
        if(!sWEditText.getText().toString().equals("")) //secondi White
            secondsWhite = Integer.parseInt(sWEditText.getText().toString());
        if(!rWEditText.getText().toString().equals("") && isNeedRecoverWhite) //recupero White
            recuperoWhite = Integer.parseInt(rWEditText.getText().toString());
        if(!nBEditText.getText().toString().equals("")) //nome Black
            nameBlack = nBEditText.getText().toString();
        if(!mBEditText.getText().toString().equals("")) //minuti Black
            minutesBlack = Integer.parseInt(mBEditText.getText().toString());
        if(!sBEditText.getText().toString().equals("")) //secondi Black
            secondsBlack = Integer.parseInt(sBEditText.getText().toString());
        if(!rBEditText.getText().toString().equals("") && isNeedRecoverBlack) //recupero Black
            recuperoBlack = Integer.parseInt(rBEditText.getText().toString());

        //controllo che il tempo non superi il limite massimo di 59:59
        if(minutesWhite > 59)
            throw new myException("Out of bounds", 1);
        if(secondsWhite > 59)
            throw new myException("Out of bounds", 2);
        if(recuperoWhite > 59)
            throw new myException("Out of bounds", 3);
        if(minutesBlack > 59)
            throw new myException("Out of bounds", 4);
        if(secondsBlack > 59)
            throw new myException("Out of bounds", 5);
        if(recuperoBlack > 59)
            throw new myException("Out of bounds", 6);

        //visualizzo su "RUN" i dati passati
        visualiseInRUN("nameWhite ", "" + nameWhite);
        visualiseInRUN("minutesWhite ", "" + minutesWhite);
        visualiseInRUN("secondsWhite ", "" + secondsWhite);
        visualiseInRUN("recoverWhite ", "" + recuperoWhite);
        visualiseInRUN("nameBlack ", "" + nameBlack);
        visualiseInRUN("minutesBlack ", "" + minutesBlack);
        visualiseInRUN("secondsBlack ", "" + secondsBlack);
        visualiseInRUN("recoverBlack ", "" + recuperoBlack);
        if(contaMosse)
            visualiseInRUN("contaMosse ", "true");
        else
            visualiseInRUN("contaMosse ", "false");

        //Aggiungo le variabili come extra
        intent.putExtra(NAME_WHITE, nameWhite);
        intent.putExtra(MINUTES_WHITE, minutesWhite);
        intent.putExtra(SECONDS_WHITE, secondsWhite);
        intent.putExtra(RECOVER_WHITE, recuperoWhite);
        intent.putExtra(NAME_BLACK, nameBlack);
        intent.putExtra(MINUTES_BLACK, minutesBlack);
        intent.putExtra(SECONDS_BLACK, secondsBlack);
        intent.putExtra(RECOVER_BLACK, recuperoBlack);
        intent.putExtra(MOVE_COUNTER, contaMosse);
    }

    //metodo provvisorio
    private void visualiseInRUN(String tag, String str) {
        Log.d(tag, str);
    }

    private void visualiseError(myException exception) {
        switch(exception.getCode()) {
            case 1:
                mWEditText.setError(exception.getMessange());
                break;
            case 2:
                sWEditText.setError(exception.getMessange());
                break;
            case 3:
                rWEditText.setError(exception.getMessange());
                break;
            case 4:
                mBEditText.setError(exception.getMessange());
                break;
            case 5:
                sBEditText.setError(exception.getMessange());
                break;
            case 6:
                rBEditText.setError(exception.getMessange());
                break;
        }
    }

    private void controlCharLength() throws myException {
        if(mWEditText.length() > 2)
            throw new myException("Length grater than 2", 1);
        if(sWEditText.length() > 2)
            throw new myException("Length grater than 2", 2);
        if(rWEditText.length() > 2)
            throw new myException("Length grater than 2", 3);
        if(mBEditText.length() > 2)
            throw new myException("Length grater than 2", 4);
        if(sBEditText.length() > 2)
            throw new myException("Length grater than 2", 5);
        if(rBEditText.length() > 2)
            throw new myException("Length grater than 2", 6);
    }
}
