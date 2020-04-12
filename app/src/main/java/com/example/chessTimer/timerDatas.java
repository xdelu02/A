package com.example.chessTimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

public class timerDatas extends AppCompatActivity {

    //dichiaro le EditText
    private EditText nWEditText;
    private EditText mWEditText;
    private EditText sWEditText;
    private EditText rWEditText;
    private EditText nBEditText;
    private EditText mBEditText;
    private EditText sBEditText;
    private EditText rBEditText;

    private CheckBox moveCounterCheckBox;
    private Switch recoverSwitchWhite;
    private Switch recoverSwitchBlack;

    //SharedPreferences
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SP_NAME_WHITE = "nameWhite";
    public static final String SP_MINUTES_WHITE = "minutesWhite";
    public static final String SP_SECONDS_WHITE = "secondsWhite";
    public static final String SP_RECOVERSWICH_WHITE = "recoverSwitchWhite";
    public static final String SP_RECOVER_WHITE = "recoverWhite";
    public static final String SP_NAME_BLACK = "nameBlack";
    public static final String SP_MINUTES_BLACK = "minutesBlack";
    public static final String SP_SECONDS_BLACK = "secondsBlack";
    public static final String SP_RECOVERSWICH_BLACK = "recoverSwitchBlack";
    public static final String SP_RECOVER_BLACK = "recoverBlack";
    public static final String SP_MOVE_COUNTER = "moveCounterCheckbox";
    public static final String SP_DARK_MODE = "darkMode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_datas);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        if(sharedPreferences.getBoolean(SP_DARK_MODE, false)) {
            setDarkModeOn(true);
            setTheme(R.style.DarkTheme);
        }

        //DARK/LIGHT MODE VISIBILITY OF BTNS SECTION
        Button nightModeBtn = findViewById(R.id.nightModeBtn);
        Button lightModeBtn = findViewById(R.id.lightModeBtn);
        if(isDarkModeOn()) {
            lightModeBtn.setVisibility(View.VISIBLE);
            nightModeBtn.setVisibility(View.INVISIBLE);
        }
        else {
            nightModeBtn.setVisibility(View.VISIBLE);
            lightModeBtn.setVisibility(View.INVISIBLE);
        }

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
        recoverSwitchWhite = findViewById(R.id.recoverSwitchWhite);
        recoverSwitchWhite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) rWEditText.setVisibility ( View.VISIBLE );

                else rWEditText.setVisibility(View.INVISIBLE);
            }
        });
        //switch recupero nero
        recoverSwitchBlack = findViewById(R.id.recoverSwitchBlack);
        recoverSwitchBlack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    rBEditText.setVisibility(View.VISIBLE);
                else
                    rBEditText.setVisibility(View.INVISIBLE);
            }
        });

        //checkBox del conta mosse
        moveCounterCheckBox =  findViewById(R.id.moveCounterCheckBox);
        moveCounterCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                
            }
        });

        //Btn erase
        final Button eraseBtn = findViewById(R.id.eraseBtn);
        eraseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllFilds();

                recoverSwitchWhite.setChecked(false);
                recoverSwitchBlack.setChecked(false);
                moveCounterCheckBox.setChecked(false);
            }
        });

        //Btn for DarkMode
        nightModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDarkModeOn(true);
            }
        });
        lightModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDarkModeOn(false);
            }
        });

        loadData();
    }

    //funzione che scorre alla attivita' del FisherTimer
    public void goToTimer () {
        Intent intent = new Intent(this, FisherTimer.class);

        if(visualiseErrors(validateCharLength())) {
            showErrorToast();
            return;
        }

        if(visualiseErrors(validateTime())) {
            showErrorToast();
            return;
        }

        saveData();

        startActivity(intent);
    }


    //metodo provvisorio
    private void visualiseInRUN(String tag, String str) {
        Log.d(tag, str);
    }

    private boolean visualiseErrors(ArrayList<myException> exceptions) {
        if(exceptions.size() > 0) {
            for (myException e : exceptions)
                visualiseError(e);
            return true;
        }
        return false;
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

    //controllo che i campi siano inferiori a 3 caratteri
    private ArrayList<myException> validateCharLength() {
        ArrayList<myException> exceptions = new ArrayList<>();

        if(mWEditText.length() > 2)
            exceptions.add(new myException("Length grater than 2", 1));
        if(sWEditText.length() > 2)
            exceptions.add(new myException("Length grater than 2", 2));
        if(rWEditText.length() > 2)
            exceptions.add(new myException("Length grater than 2", 3));
        if(mBEditText.length() > 2)
            exceptions.add(new myException("Length grater than 2", 4));
        if(sBEditText.length() > 2)
            exceptions.add(new myException("Length grater than 2", 5));
        if(rBEditText.length() > 2)
            exceptions.add(new myException("Length grater than 2", 6));

        return exceptions;
    }

    //controllo che il tempo non superi il limite massimo di 59:59
    private ArrayList<myException> validateTime() {
        ArrayList<myException> exceptions = new ArrayList<>();

        if(Integer.parseInt(mWEditText.getText().toString()) > 59)
            exceptions.add(new myException("Out of bounds", 1));
        if(Integer.parseInt(sWEditText.getText().toString()) > 59)
            exceptions.add(new myException("Out of bounds", 2));
        if(Integer.parseInt(rWEditText.getText().toString()) > 59)
            exceptions.add(new myException("Out of bounds", 3));
        if(Integer.parseInt(mBEditText.getText().toString()) > 59)
            exceptions.add(new myException("Out of bounds", 4));
        if(Integer.parseInt(sBEditText.getText().toString()) > 59)
            exceptions.add(new myException("Out of bounds", 5));
        if(Integer.parseInt(rBEditText.getText().toString()) > 59)
            exceptions.add(new myException("Out of bounds", 6));

        return exceptions;
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

    //clear all filds
    private void clearAllFilds() {
        nWEditText.setText("");
        mWEditText.setText("");
        sWEditText.setText("");
        rWEditText.setText("");
        nBEditText.setText("");
        mBEditText.setText("");
        sBEditText.setText("");
        rBEditText.setText("");
    }

    //personalized toast
    private void showErrorToast() {
        String message = "You made error in timer parameters!";

        if(isDarkModeOn())
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        else
            StyleableToast.makeText(this, message, R.style.lightToast).show();
    }

    /**
     * DARK MODE SECTION
     */
    //true se la dark mode e' attiva
    public static boolean isDarkModeOn() {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    }
    //mette la darkmode on
    private void setDarkModeOn(boolean setOn) {
        if(setOn)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(SP_DARK_MODE, isDarkModeOn());

        editor.apply();
    }

    /**
     * DATA STORAGE SECTION
     */
    //save datas
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //White
        if(!nWEditText.getText().toString().equals(""))
            editor.putString(SP_NAME_WHITE, nWEditText.getText().toString());
        else
            editor.putString(SP_NAME_WHITE, "White");
        editor.putString(SP_MINUTES_WHITE, mWEditText.getText().toString());
        editor.putString(SP_SECONDS_WHITE, sWEditText.getText().toString());
        editor.putBoolean(SP_RECOVERSWICH_WHITE, recoverSwitchWhite.isChecked());
        editor.putString(SP_RECOVER_WHITE, rWEditText.getText().toString());
        //Black
        if(!nBEditText.getText().toString().equals(""))
            editor.putString(SP_NAME_BLACK, nBEditText.getText().toString());
        else
            editor.putString(SP_NAME_BLACK, "Black");
        editor.putString(SP_MINUTES_BLACK, mBEditText.getText().toString());
        editor.putString(SP_SECONDS_BLACK, sBEditText.getText().toString());
        editor.putBoolean(SP_RECOVERSWICH_BLACK, recoverSwitchBlack.isChecked());
        editor.putString(SP_RECOVER_BLACK, rBEditText.getText().toString());
        //general
        editor.putBoolean(SP_MOVE_COUNTER, moveCounterCheckBox.isChecked());

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }
    //load datas
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        //White
        if(!nWEditText.getText().toString().equals(""))
            nWEditText.setText(sharedPreferences.getString(SP_NAME_WHITE, ""));
        else
            nWEditText.setText("");
        mWEditText.setText(sharedPreferences.getString(SP_MINUTES_WHITE, "10"), TextView.BufferType.EDITABLE);
        sWEditText.setText(sharedPreferences.getString(SP_SECONDS_WHITE, "0"), TextView.BufferType.EDITABLE);
        recoverSwitchWhite.setChecked(sharedPreferences.getBoolean(SP_RECOVERSWICH_WHITE, false));
        rWEditText.setText(sharedPreferences.getString(SP_RECOVER_WHITE, "0"), TextView.BufferType.EDITABLE);
        //Black
        if(!nBEditText.getText().toString().equals(""))
            nBEditText.setText(sharedPreferences.getString(SP_NAME_BLACK, ""));
        else
            nBEditText.setText("");
        mBEditText.setText(sharedPreferences.getString(SP_MINUTES_BLACK, "10"), TextView.BufferType.EDITABLE);
        sBEditText.setText(sharedPreferences.getString(SP_SECONDS_BLACK, "0"), TextView.BufferType.EDITABLE);
        recoverSwitchBlack.setChecked(sharedPreferences.getBoolean(SP_RECOVERSWICH_BLACK, false));
        rBEditText.setText(sharedPreferences.getString(SP_RECOVER_BLACK, "0"), TextView.BufferType.EDITABLE);
        //general
        moveCounterCheckBox.setChecked(sharedPreferences.getBoolean(SP_MOVE_COUNTER, false));
    }
}
