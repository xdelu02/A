package com.example.chessTimer;

public class myException extends Exception {
    private int code;

    //getter
    int getCode() {
        return code;
    }
    String getMessange() {
        return super.getMessage();
    }

    //costruttore
    myException(String message) {
        super(message);
    }
    myException(String message, int code) {
        super(message);
        this.code = code;
    }

    @androidx.annotation.NonNull
    /* Override */
    @Override
    public String toString() {
        return "****** " + super.getMessage() + " ******";
    }
}
