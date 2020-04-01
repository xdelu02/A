package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.util.Log;
import android.widget.Switch;

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

    private boolean isNeedRecoverWhite = false;
    private boolean isNeedRecoverBlack = false;
    private boolean contaMosse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_datas);

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

        try {
            setExternalVariables(intent);
        } catch (myException e) {
            System.out.println(e);
            return -1;
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

        //prelevo le EditText
        EditText nWEditText = findViewById(R.id.nameWhite);
        EditText mWEditText = findViewById(R.id.minuteWhite);
        EditText sWEditText = findViewById(R.id.secondWhite);
        EditText rWEditText = findViewById(R.id.recoverWhite);
        EditText nBEditText = findViewById(R.id.nameBlack);
        EditText mBEditText = findViewById(R.id.minuteBlack);
        EditText sBEditText = findViewById(R.id.secondBlack);
        EditText rBEditText = findViewById(R.id.recoverBlack);

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
        if(secondsWhite > 59)
            throw new myException("****** White seconds intervall error ******");
        if(minutesWhite > 59)
            throw new myException("****** White minute intervall error ******");
        if(recuperoWhite > 59)
            throw new myException("****** White recover intervall error ******");
        if(secondsBlack > 59)
            throw new myException("****** Black seconds intervall error ******");
        if(minutesBlack > 59)
            throw new myException("****** Black minute intervall error ******");
        if(recuperoBlack > 59)
            throw new myException("****** Black recover intervall error ******");

        //visualizzo su "RUN" i dati passati
        visualiseLogcat("nameWhite ", "" + nameWhite);
        visualiseLogcat("minutesWhite ", "" + minutesWhite);
        visualiseLogcat("secondsWhite ", "" + secondsWhite);
        visualiseLogcat("recoverWhite ", "" + recuperoWhite);
        visualiseLogcat("nameBlack ", "" + nameBlack);
        visualiseLogcat("minutesBlack ", "" + minutesBlack);
        visualiseLogcat("secondsBlack ", "" + secondsBlack);
        visualiseLogcat("recoverBlack ", "" + recuperoBlack);
        if(contaMosse)
            visualiseLogcat("contaMosse ", "true");
        else
            visualiseLogcat("contaMosse ", "false");

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
    private void visualiseLogcat(String tag, String str) {
        Log.d(tag, str);
    }
}
